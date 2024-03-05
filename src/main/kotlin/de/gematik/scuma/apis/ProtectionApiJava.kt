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