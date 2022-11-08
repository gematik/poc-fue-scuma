package de.gematik.scuma.apis

import de.gematik.kether.eth.types.Address
import java.util.*

/**
 * Created by rk on 07.11.2022.
 * gematik.de
 */

val CRUD_CREATE = 0
val CRUD_READ = 1
val CRUD_UPDATE = 2
val CRUD_DELETE = 3
data class PermissionRequest(val protectedResourceId: UUID, val requestedMethods: BitSet)
data class Rule(val who: Address, val how: BitSet)
data class Permission(val protectedResourceId: UUID, val grantedMethods: BitSet)
