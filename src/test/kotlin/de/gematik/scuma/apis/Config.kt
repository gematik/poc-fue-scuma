package de.gematik.scuma.apis

import de.gematik.kether.crypto.AccountStore
import de.gematik.kether.eth.types.Address
import de.gematik.kether.rpc.Rpc
import java.util.*

/**
 * Created by rk on 07.11.2022.
 * gematik.de
 */
val testResource = UUID(0, 1)
val contractId = Address("0x63491c5363329afb6f370e9d297025481e0277e6")
val ownerId = AccountStore.getAccount(AccountStore.TEST_ACCOUNT_1).address
val userId = AccountStore.getAccount(AccountStore.TEST_ACCOUNT_2).address
val protectionAuthorizationId = AccountStore.getAccount(AccountStore.TEST_ACCOUNT_4).address
val rpc = Rpc("http://ethereum1.lab.gematik.de:8545", "ws://ethereum1.lab.gematik.de:8546")
val controlApi = ControlApi(contractId, ownerId, rpc)
val protectionApi = ProtectionApi(contractId, protectionAuthorizationId, rpc)

