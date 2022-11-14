package de.gematik.scuma.apis

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
open class ProtectionApi(contractId: Address, resourceOwnerId: Address, rpc: Rpc) : ScumaApi(contractId, resourceOwnerId, rpc) {

    public override fun getResourceCount(): Int {
        return super.getResourceCount()
    }

    public override fun getResourceIds(): List<UUID> {
        return super.getResourceIds()
    }

    public override suspend fun registerResource(protectedResourceId: UUID): Boolean {
        return super.registerResource(protectedResourceId)
    }

    public override fun requestPermissions(userId: Address, permissionRequests: List<PermissionRequest>): List<Permission> {
        return super.requestPermissions(userId, permissionRequests)
    }

    public override suspend fun unregisterAllResources(): Boolean {
        return super.unregisterAllResources()
    }

    public override suspend fun unregisterResource(protectedResourceId: UUID): Boolean {
        return super.unregisterResource(protectedResourceId)
    }
}