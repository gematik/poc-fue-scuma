package de.gematik.scuma.contracts

import de.gematik.kether.abi.types.AbiUint256
import de.gematik.kether.abi.types.AbiUint8
import de.gematik.kether.eth.Eth
import de.gematik.kether.eth.types.Address
import de.gematik.kether.eth.types.Transaction
import de.gematik.kether.rpc.Rpc
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

/**
 * Created by rk on 02.08.2022.
 * gematik.de
 */
@ExperimentalSerializationApi
class ScumaContractTests {

    companion object {
        val resourceOwnerId = Address("0xfe3b557e8fb62b89f4916b721be55ceb828dbd73")
        val protectionAuthorizationId = Address("0xB389e2Ac92361c81481aFeF1cBF29881005996a3")
        val scumaContractId = Address("0x1b24d90f2210d6df2fa19acaef1e717bdb7a4668")
        lateinit var scumaResourceOwner: ScumaContract
        lateinit var scumaResourceProvider: ScumaContract

        @BeforeClass
        @JvmStatic
        fun scumaInit() {
            runBlocking {
                val ethereum1 = Eth(Rpc("http://ethereum1.lab.gematik.de:8547", "ws://ethereum1.lab.gematik.de:8546"))
                scumaResourceOwner = ScumaContract(ethereum1, Transaction(to = scumaContractId, from = resourceOwnerId))
                scumaResourceProvider =
                    ScumaContract(ethereum1, Transaction(to = scumaContractId, from = protectionAuthorizationId))
            }
        }

        @AfterClass
        @JvmStatic
        fun cancelScuma() {
            scumaResourceOwner.cancel()
        }
    }

    @Test
    fun registerProvider() {
        runBlocking {
            val receipt = scumaResourceOwner.RegisterProvider(protectionAuthorizationId)
            assert(receipt.isSuccess)
        }
    }

    @Test
    fun registerResourceWrongProvider() {
        runBlocking {
            scumaResourceOwner.run {
                assert(runCatching { registerResource(AbiUint256(2)) }.isFailure)
            }
        }
    }

    @Test
    fun registerResource() {
        runBlocking {
            scumaResourceProvider.run {
                val receipt = registerResource(AbiUint256(2))
                assert(receipt.isSuccess)
            }
        }
    }

    @Test
    fun setPolicy() {
        runBlocking {
            scumaResourceOwner.run {
                val receipt = setRule(AbiUint256(2), protectionAuthorizationId, AbiUint8(1))
                assert(receipt.isSuccess)
            }
        }
    }

    @Test
    fun requestPermission() {
        runBlocking {
            scumaResourceProvider.run {
                val permissions = requestPermissions(
                    protectionAuthorizationId,
                    listOf(ScumaContract.PermissionRequest(protectedResourceId = AbiUint256(2), AbiUint8(1)))
                )
                println(permissions)
                assert(permissions.size == 1)
            }
        }
    }


}
