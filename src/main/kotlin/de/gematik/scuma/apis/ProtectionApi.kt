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