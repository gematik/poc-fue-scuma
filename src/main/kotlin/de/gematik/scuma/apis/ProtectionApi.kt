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
class ProtectionApi(contractId: Address, resourceOwnerId: Address, rpc: Rpc) {
    val contract = ScumaContract(Eth(rpc), Transaction(to=contractId, from = resourceOwnerId))

    fun getResourceCount(): Int {
        return contract.getResourceCount().toBigInteger().toInt()
    }

    fun getResourceIds(): List<UUID> {
        return contract.getResourceIds().map { it.toUUID() }
    }

    suspend fun registerResource(protectedResourceId: UUID): Boolean {
        return contract.registerResource(protectedResourceId.toQuantity()).isSuccess
    }

    fun requestPermissions(userId: Address, permissionRequests: List<PermissionRequest>): List<Permission> {
        return contract.requestPermissions(userId, permissionRequests.map { ScumaContract.PermissionRequest(it.protectedResourceId.toQuantity(), Quantity(it.requestedMethod.ordinal.toLong()))}).map{ Permission(it.protectedResourceId.toUUID(), AccessMethod.values()[it.grantedMethod.toBigInteger().toInt()]) }
    }

    suspend fun unregisterAllResources(): Boolean {
        return contract.unregisterAllResources().isSuccess
    }

    suspend fun unregisterResource(protectedResourceId: UUID): Boolean {
        return contract.unregisterResource(protectedResourceId.toQuantity()).isSuccess
    }

}