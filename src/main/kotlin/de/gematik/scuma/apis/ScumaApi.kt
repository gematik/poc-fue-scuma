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

import de.gematik.kether.abi.types.AbiUint256
import de.gematik.kether.eth.Eth
import de.gematik.kether.eth.types.Address
import de.gematik.kether.eth.types.Quantity
import de.gematik.kether.eth.types.Transaction
import de.gematik.kether.rpc.Rpc
import de.gematik.scuma.contracts.ScumaContract
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
open class ScumaApi(contractId: Address, resourceOwnerId: Address, rpc: Rpc) {
    private val contract = ScumaContract(Eth(rpc), Transaction(to=contractId, from = resourceOwnerId))

    private val timeOut = 120000L  // time out in milliseconds (2 minutes)
    private val parent = Job()

    // Control API

    protected open suspend fun registerProvider(protectionAuthorizationId: Address) : Boolean {
        return contract.registerProvider(protectionAuthorizationId).isSuccess
    }

    protected open fun getProviderCount(): Int {
        return contract.getProviderCount().toBigInteger().toInt()
    }

    protected open fun getProviders(): List<Address> {
        return contract.getProviders()
    }

    protected open suspend fun unregisterAllProviders(): Boolean {
        return contract.unregisterAllProviders().isSuccess
    }

    protected open suspend fun unregisterProvider(protectionAuthorizationId: Address): Boolean {
        return contract.unregisterProvider(protectionAuthorizationId).isSuccess
    }

    protected open suspend fun setRule(protectedResourceId: UUID, userId: Address, methods: BitSet) : Boolean {
        return contract.setRule(protectedResourceId.toQuantity(), userId, AbiUint256(methods.toLong())).isSuccess
    }

    protected open suspend fun deleteRule(protectedResourceId: UUID, index: Int): Boolean {
        return contract.deleteRule(protectedResourceId.toQuantity(), Quantity(index.toLong())).isSuccess
    }

    protected open fun getPolicy(protectedResourceId: UUID): List<Rule> {
        return contract.getPolicy(protectedResourceId.toQuantity()).map {Rule(it.who, it.how.toBigInteger().toLong().toBitSet())}
    }

    // Java wrappers for suspend functions

    protected open fun registerProviderAsync(protectionAuthorizationId: Address): CompletableFuture<Boolean> =
        CoroutineScope(Job(parent)).future {
            withTimeout(timeOut) { registerProvider(protectionAuthorizationId) }
        }

    protected open fun unregisterAllProvidersAsync(): CompletableFuture<Boolean> =
        CoroutineScope(Job(parent)).future {
            withTimeout(timeOut) { unregisterAllProviders() }
        }

    protected open fun unregisterProviderAsync(protectionAuthorizationId: Address): CompletableFuture<Boolean> =
        CoroutineScope(Job(parent)).future {
            withTimeout(timeOut) { unregisterProvider(protectionAuthorizationId) }
        }

    protected open fun setRuleAsync(protectedResourceId: UUID, userId: Address, methods: BitSet): CompletableFuture<Boolean> =
        CoroutineScope(Job(parent)).future {
            withTimeout(timeOut) { setRule(protectedResourceId, userId, methods) }
        }

    protected open fun deleteRuleAsync(protectedResourceId: UUID, index: Int): CompletableFuture<Boolean> =
        CoroutineScope(Job(parent)).future {
            withTimeout(timeOut) { deleteRule(protectedResourceId, index) }
        }

    // Protection API

    protected open fun getResourceCount(): Int {
        return contract.getResourceCount().toBigInteger().toInt()
    }

    protected open fun getResourceIds(): List<UUID> {
        return contract.getResourceIds().map { it.toUUID() }
    }

    protected open suspend fun registerResource(protectedResourceId: UUID): Boolean {
        return contract.registerResource(protectedResourceId.toQuantity()).isSuccess
    }

    protected open fun requestPermissions(userId: Address, permissionRequests: List<PermissionRequest>): List<Permission> {
        return contract.requestPermissions(userId, permissionRequests.map { ScumaContract.PermissionRequest(it.protectedResourceId.toQuantity(), Quantity(it.requestedMethods.toLong()))}).map{ Permission(it.protectedResourceId.toUUID(), it.grantedMethods.toBigInteger().toLong().toBitSet()) }
    }

    protected open suspend fun unregisterAllResources(): Boolean {
        return contract.unregisterAllResources().isSuccess
    }

    protected open suspend fun unregisterResource(protectedResourceId: UUID): Boolean {
        return contract.unregisterResource(protectedResourceId.toQuantity()).isSuccess
    }

    // Java wrappers for suspend functions

    protected open fun registerResourceAsync(protectedResourceId: UUID): CompletableFuture<Boolean> =
        CoroutineScope(Job(parent)).future {
            withTimeout(timeOut) { registerResource(protectedResourceId) }
        }

    protected open fun unregisterAllResourcesAsync(): CompletableFuture<Boolean> =
        CoroutineScope(Job(parent)).future {
            withTimeout(timeOut) { unregisterAllResources() }
        }

    protected open fun unregisterResourceAsync(protectedResourceId: UUID): CompletableFuture<Boolean> =
        CoroutineScope(Job(parent)).future {
            withTimeout(timeOut) { unregisterResource(protectedResourceId) }
        }

    protected open fun cancel() {
        parent.cancel()
    }

}