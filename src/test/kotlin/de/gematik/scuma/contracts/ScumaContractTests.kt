package de.gematik.scuma.contracts

import de.gematik.kether.abi.types.AbiUint32
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
        val scumaContractId = Address("0xf4074b3685f8f40f2dca83742dab19912a0eb2c3")
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
                assert(runCatching {registerResource(AbiUint32(1))}.isFailure)
            }
        }
    }

    @Test
    fun registerResource() {
        runBlocking {
            scumaResourceProvider.run {
                val receipt = registerResource(AbiUint32(1))
                assert(receipt.isSuccess)
            }
        }
    }

    @Test
    fun setPolicy() {
        runBlocking {
            scumaResourceOwner.run {
                val receipt = setRule(AbiUint32(1), protectionAuthorizationId, AbiUint8(1))
                assert(receipt.isSuccess)
            }
        }
    }

    @Test
    fun requestPermission() {
        runBlocking {
            scumaResourceProvider.run {
                val permissions = requestPermissions(protectionAuthorizationId, arrayOf(ScumaContract.PermissionRequest(protectedResourceId = AbiUint32(1), AbiUint8(1))))
                assert(permissions.size == 1)
            }
        }
    }


}
