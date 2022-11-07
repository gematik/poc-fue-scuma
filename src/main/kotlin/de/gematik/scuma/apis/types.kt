package de.gematik.scuma.apis

import de.gematik.kether.eth.types.Address
import java.util.*

/**
 * Created by rk on 07.11.2022.
 * gematik.de
 */

enum class AccessMethod { READ, WRITE, UPDATE, DELETE }
data class PermissionRequest(val protectedResourceId: UUID, val requestedMethod: AccessMethod)
data class Rule(val who: Address, val how: AccessMethod)
data class Permission(val protectedResourceId: UUID, val grantedMethod: AccessMethod)
