package de.gematik.scuma.contracts

import de.gematik.kether.abi.types.AbiUint256
import de.gematik.kether.crypto.AccountStore
import de.gematik.kether.crypto.accountStore
import de.gematik.kether.eth.Eth
import de.gematik.kether.eth.types.Address
import de.gematik.kether.eth.types.Quantity
import de.gematik.kether.eth.types.Transaction
import de.gematik.kether.rpc.Rpc
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

/**
 * Created by rk on 02.08.2022.
 * gematik.de
 */
@ExperimentalSerializationApi
class ScumaContractTests {

    companion object {
        val resourceOwnerId = accountStore.getAccount(AccountStore.TEST_ACCOUNT_1_R).address
        val protectionAuthorizationId = accountStore.getAccount(AccountStore.TEST_ACCOUNT_3_R).address
        val scumaContractId = Address("0x42699a7612a82f1d9c36148af9c77354759b210b")
        lateinit var scumaResourceOwner: ScumaContract
        lateinit var scumaResourceProvider: ScumaContract

        @BeforeAll
        @JvmStatic
        fun scumaInit() {
            runBlocking {
                val ethereum1 = Eth(Rpc("http://besu.lab.gematik.de:8545", "ws://besu.lab.gematik.de:8546"))
                scumaResourceOwner = ScumaContract(
                    ethereum1,
                    Transaction(to = scumaContractId, from = resourceOwnerId)
                )
                scumaResourceProvider = ScumaContract(
                    ethereum1,
                    Transaction(to = scumaContractId, from = protectionAuthorizationId)
                )
            }
        }

        @AfterAll
        @JvmStatic
        fun cancelScuma() {
            scumaResourceOwner.cancel()
        }
    }

    @Test
    fun registerProvider() {
        runBlocking {
            scumaResourceOwner.run {
                // unregister all providers
                var receipt = unregisterAllProviders()
                assert(receipt.isSuccess)
                assert(getProviderCount() == Quantity(0))
                // register new provider
                receipt = registerProvider(protectionAuthorizationId)
                assert(receipt.isSuccess)
                assert(getProviderCount() == Quantity(1))
                assert(getProviders()[0] == protectionAuthorizationId)
                // clean up
                unregisterAllProviders()
                assert(receipt.isSuccess)
                assert(getProviderCount() == Quantity(0))
            }
        }
    }

    @Test
    fun registerResourceWrongProvider() {
        runBlocking {
            scumaResourceOwner.unregisterAllProviders()
            assert(runCatching { scumaResourceProvider.registerResource(AbiUint256(2)) }.isFailure)
        }
    }

    @Test
    fun registerResource() {
        runBlocking {
            scumaResourceOwner.registerProvider(protectionAuthorizationId)
            scumaResourceProvider.run {
                // unregister all resources
                val receipt1 = unregisterAllResources()
                assert(receipt1.isSuccess)
                assert(getResourceCount() == Quantity(0))
                //register new resource
                val receipt2 = registerResource(AbiUint256(2))
                assert(receipt2.isSuccess)
                assert(getResourceCount() == Quantity(1))
                assert(getResourceIds()[0] == AbiUint256(2))
                // clean up
                unregisterAllResources()
            }
            scumaResourceOwner.unregisterAllProviders()
        }
    }

    @Test
    fun setPolicy() {
        runBlocking {
            scumaResourceOwner.registerProvider(protectionAuthorizationId)
            scumaResourceProvider.registerResource(AbiUint256(2))
            scumaResourceOwner.run {
                val receipt = setRule(AbiUint256(2), protectionAuthorizationId, AbiUint256(1))
                assert(receipt.isSuccess)
                val policy = getPolicy(AbiUint256(2))
                assert(policy.size == 1)
                assert(policy[0].let{it.who == protectionAuthorizationId && it.how == AbiUint256(1)})
            }
            scumaResourceProvider.unregisterAllResources()
            scumaResourceOwner.unregisterAllProviders()
        }
    }

    @Test
    fun requestPermission() {
        runBlocking {
            scumaResourceOwner.registerProvider(protectionAuthorizationId)
            scumaResourceProvider.registerResource(AbiUint256(2))
            scumaResourceOwner.setRule(AbiUint256(2), protectionAuthorizationId, AbiUint256(1))
            val permissions = scumaResourceProvider.requestPermissions(
                protectionAuthorizationId,
                listOf(ScumaContract.PermissionRequest(protectedResourceId = AbiUint256(2), AbiUint256(1)))
            )
            assert(permissions.size == 1)
            assert(permissions[0].let{it.protectedResourceId == AbiUint256(2) && it.grantedMethods == AbiUint256(1)})
            scumaResourceProvider.unregisterAllResources()
            scumaResourceOwner.unregisterAllProviders()
        }
    }
}
