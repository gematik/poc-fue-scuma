package de.gematik.scuma.contracts

import de.gematik.kether.eth.Eth
import de.gematik.kether.eth.types.Address
import de.gematik.kether.extensions.toHex
import de.gematik.kether.rpc.Rpc
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.Test
import java.math.BigInteger

/**
 * Created by rk on 02.08.2022.
 * gematik.de
 */
@ExperimentalSerializationApi
class DeployScumaContract {

    val resourceOwnerId = Address("0xfe3b557e8fb62b89f4916b721be55ceb828dbd73")
    val resourceOwnerPrivateKey = BigInteger("8f2a55949038a9610f50fb23b5883af3b4ecb3c3bb792cbcefbd1542c692be63", 16)


    @Test
    fun scumaContractDeploy() {
        runBlocking {
            val ethereum1 = Eth(Rpc("http://ethereum1.lab.gematik.de:8545", "ws://ethereum1.lab.gematik.de:8546"))
            val receipt = ScumaContract.deploy(ethereum1, resourceOwnerId, resourceOwnerPrivateKey)
            val contractAddress = receipt.contractAddress!!
            println("scuma contractId: ${contractAddress.toByteArray().toHex()}")
            assert(receipt.isSuccess)
        }
    }
}