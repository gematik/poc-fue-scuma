package de.gematik.scuma.apis

import de.gematik.kether.abi.types.AbiUint8
import de.gematik.kether.eth.Eth
import de.gematik.kether.eth.types.Address
import de.gematik.kether.eth.types.Quantity
import de.gematik.kether.eth.types.Transaction
import de.gematik.kether.rpc.Rpc
import de.gematik.scuma.contracts.ScumaContract
import java.util.*

/**
 * Created by rk on 03.11.2022.
 * gematik.de
 */
class ControlApi(contractId: Address, resourceOwnerId: Address, rpc: Rpc) {
    val contract = ScumaContract(Eth(rpc), Transaction(to=contractId, from = resourceOwnerId))

    suspend fun registerProvider(protectionAuthorizationId: Address) : Boolean {
        return contract.registerProvider(protectionAuthorizationId).isSuccess
    }

    fun getProviderCount(): Int {
        return contract.getProviderCount().toBigInteger().toInt()
    }

    fun getProviders(): List<Address> {
        return contract.getProviders()
    }

    suspend fun unregisterAllProviders(): Boolean {
        return contract.unregisterAllProviders().isSuccess
    }

    suspend fun unregisterProvider(protectionAuthorizationId: Address): Boolean {
        return contract.unregisterProvider(protectionAuthorizationId).isSuccess
    }

    suspend fun setRule(protectedResourceId: UUID, userId: Address, method: AccessMethod) : Boolean {
        return contract.setRule(protectedResourceId.toQuantity(), userId, AbiUint8(method.ordinal.toLong())).isSuccess
    }

    suspend fun deleteRule(protectedResourceId: UUID, index: Int): Boolean {
        return contract.deleteRule(protectedResourceId.toQuantity(), Quantity(index.toLong())).isSuccess
    }

    fun getPolicy(protectedResourceId: UUID): List<Rule> {
        return contract.getPolicy(protectedResourceId.toQuantity()).map {Rule(it.who, AccessMethod.values()[it.how.toBigInteger().toInt()])}
    }
}

