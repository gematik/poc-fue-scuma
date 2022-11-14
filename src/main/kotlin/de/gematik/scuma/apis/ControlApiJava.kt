package de.gematik.scuma.apis

import de.gematik.kether.eth.types.Address
import de.gematik.kether.rpc.Rpc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.future.future
import kotlinx.coroutines.withTimeout
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Created by rk on 14.11.2022.
 * gematik.de
 */
class ControlApiJava(contractId: Address, resourceOwnerId: Address, rpc: Rpc) :
    ScumaApi(contractId, resourceOwnerId, rpc) {

    public override fun registerProviderAsync(protectionAuthorizationId: Address): CompletableFuture<Boolean> {
        return super.registerProviderAsync(protectionAuthorizationId)
    }

    public override fun unregisterAllProvidersAsync(): CompletableFuture<Boolean> {
        return super.unregisterAllProvidersAsync()
    }

    public override fun unregisterProviderAsync(protectionAuthorizationId: Address): CompletableFuture<Boolean> {
        return super.unregisterProviderAsync(protectionAuthorizationId)
    }

    public override fun setRuleAsync(protectedResourceId: UUID, userId: Address, methods: BitSet): CompletableFuture<Boolean> {
        return super.setRuleAsync(protectedResourceId, userId, methods)
    }

    public override fun deleteRuleAsync(protectedResourceId: UUID, index: Int): CompletableFuture<Boolean> {
        return super.deleteRuleAsync(protectedResourceId, index)
    }

    public override fun getProviderCount(): Int {
        return super.getProviderCount()
    }

    public override fun getProviders(): List<Address> {
        return super.getProviders()
    }

    public override fun getPolicy(protectedResourceId: UUID): List<Rule> {
        return super.getPolicy(protectedResourceId)
    }

    public override fun cancel() {
        super.cancel()
    }
}