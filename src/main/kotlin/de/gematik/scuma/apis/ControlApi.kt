package de.gematik.scuma.apis

import de.gematik.kether.eth.types.Address
import de.gematik.kether.rpc.Rpc
import java.util.*

/**
 * Created by rk on 03.11.2022.
 * gematik.de
 */
open class ControlApi(contractId: Address, resourceOwnerId: Address, rpc: Rpc) : ScumaApi(contractId, resourceOwnerId, rpc) {
    public override suspend fun registerProvider(protectionAuthorizationId: Address): Boolean {
        return super.registerProvider(protectionAuthorizationId)
    }

    public override fun getProviderCount(): Int {
        return super.getProviderCount()
    }

    public override fun getProviders(): List<Address> {
        return super.getProviders()
    }

    public override suspend fun unregisterAllProviders(): Boolean {
        return super.unregisterAllProviders()
    }

    public override suspend fun unregisterProvider(protectionAuthorizationId: Address): Boolean {
        return super.unregisterProvider(protectionAuthorizationId)
    }

    public override suspend fun setRule(protectedResourceId: UUID, userId: Address, methods: BitSet): Boolean {
        return super.setRule(protectedResourceId, userId, methods)
    }

    public override suspend fun deleteRule(protectedResourceId: UUID, index: Int): Boolean {
        return super.deleteRule(protectedResourceId, index)
    }

    public override fun getPolicy(protectedResourceId: UUID): List<Rule> {
        return super.getPolicy(protectedResourceId)
    }
}

