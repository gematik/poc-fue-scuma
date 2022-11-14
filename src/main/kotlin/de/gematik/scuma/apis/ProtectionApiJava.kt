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
class ProtectionApiJava(contractId: Address, resourceOwnerId: Address, rpc: Rpc) :
    ScumaApi(contractId, resourceOwnerId, rpc) {

    public override fun registerResourceAsync(protectedResourceId: UUID): CompletableFuture<Boolean> {
        return super.registerResourceAsync(protectedResourceId)
    }

    public override fun unregisterAllResourcesAsync(): CompletableFuture<Boolean> {
        return super.unregisterAllResourcesAsync()
    }

    public override fun unregisterResourceAsync(protectedResourceId: UUID): CompletableFuture<Boolean> {
        return super.unregisterResourceAsync(protectedResourceId)
    }

    public override fun getResourceCount(): Int {
        return super.getResourceCount()
    }

    public override fun getResourceIds(): List<UUID> {
        return super.getResourceIds()
    }

    public override fun requestPermissions(userId: Address, permissionRequests: List<PermissionRequest>): List<Permission> {
        return super.requestPermissions(userId, permissionRequests)
    }

    public override fun cancel() {
        super.cancel()
    }

}