/*
 * Copyright 2022-2024, gematik GmbH
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by the
 * European Commission – subsequent versions of the EUPL (the "Licence").
 * You may not use this work except in compliance with the Licence.
 *
 * You find a copy of the Licence in the "Licence" file or at
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expressed or implied.
 * In case of changes by gematik find details in the "Readme" file.
 *
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

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