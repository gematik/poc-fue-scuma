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

package de.gematik.scuma.apis

import de.gematik.kether.crypto.AccountStore
import de.gematik.kether.crypto.accountStore
import de.gematik.kether.eth.types.Address
import de.gematik.kether.rpc.Rpc
import java.util.*

/**
 * Created by rk on 07.11.2022.
 * gematik.de
 */
val testResource = UUID(0, 1)
val contractId = Address("0xba1a4f08001416a630e19e34abd260f039874e92")
val ownerId = accountStore.getAccount(AccountStore.TEST_ACCOUNT_1_R).address
val userId = accountStore.getAccount(AccountStore.TEST_ACCOUNT_2_R).address
val protectionAuthorizationId = accountStore.getAccount(AccountStore.TEST_ACCOUNT_3_R).address
val rpc = Rpc("http://besu1.lab.gematik.de:8545", "ws://besu1.lab.gematik.de:8546")
val controlApi = ControlApi(contractId, ownerId, rpc)
val protectionApi = ProtectionApi(contractId, protectionAuthorizationId, rpc)

