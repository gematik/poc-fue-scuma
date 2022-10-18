package de.gematik.scuma.contracts

import de.gematik.kether.eth.Eth
import de.gematik.kether.eth.types.Address
import de.gematik.kether.extensions.toHex
import de.gematik.kether.rpc.Rpc
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.Test

/**
 * Created by rk on 02.08.2022.
 * gematik.de
 */
@ExperimentalSerializationApi
class RegisterScumaContract {

    val resourceOwnerId = Address("0xfe3b557e8fb62b89f4916b721be55ceb828dbd73")

    @Test
    fun scumaContractDeploy() {
        runBlocking {
            val ethereum1 = Eth(Rpc("http://ethereum1.lab.gematik.de:8547", "ws://ethereum1.lab.gematik.de:8546"))
            val receipt = ScumaContract.deploy(ethereum1, resourceOwnerId)
            val contractAddress = receipt.contractAddress!!
            println("scuma contractId: ${contractAddress.toByteArray().toHex()}")
            assert(receipt.isSuccess)
        }
    }
}