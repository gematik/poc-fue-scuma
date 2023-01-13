package de.gematik.scuma.contracts

import de.gematik.kether.crypto.AccountStore
import de.gematik.kether.crypto.accountStore
import de.gematik.kether.eth.Eth
import de.gematik.kether.eth.types.Address
import de.gematik.kether.extensions.toHex
import de.gematik.kether.rpc.Rpc
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Test
import de.gematik.scuma.contracts.ScumaContract
import java.math.BigInteger

/**
 * Created by rk on 02.08.2022.
 * gematik.de
 */
@ExperimentalSerializationApi
class DeployScumaContract {

    val resourceOwnerId = accountStore.getAccount(AccountStore.TEST_ACCOUNT_1_R).address

    @Test
    fun scumaContractDeploy() {
        runBlocking {
            val ethereum1 = Eth(Rpc("http://besu.lab.gematik.de:8545", "ws://besu.lab.gematik.de:8546"))
            val receipt = ScumaContract.deploy(ethereum1, resourceOwnerId)
            val contractAddress = receipt.contractAddress!!
            println("scuma contractId: ${contractAddress.toByteArray().toHex()}")
            assert(receipt.isSuccess)
        }
    }
}