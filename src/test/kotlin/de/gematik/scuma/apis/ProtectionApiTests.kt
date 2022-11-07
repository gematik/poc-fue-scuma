package de.gematik.scuma.apis

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.*

/**
 * Created by rk on 02.08.2022.
 * gematik.de
 */
class ProtectionApiTests {

    @Test
    fun getResourceCount() {
        println(protectionApi.getResourceCount())
    }

    @Test
    fun getResourceIds() {
        println(protectionApi.getResourceIds())
    }

    @Test
    fun registerResource() {
        runBlocking {
            assert(protectionApi.registerResource(testResource))
        }
    }

    @Test
    fun registerTenResources() {
        runBlocking {
            for (i in 1 until 11) {
                assert(protectionApi.registerResource(UUID(0, i.toLong())))
            }
        }
    }

    @Test
    fun unRegisterResource() {
        runBlocking {
            assert(protectionApi.unregisterResource(testResource))
        }
    }

    @Test
    fun unRegisterAllResources() {
        runBlocking {
            assert(protectionApi.unregisterAllResources())
        }
    }

    @Test
    fun requestPermissions() {
        println(
            protectionApi.requestPermissions(
                userId,
                listOf(PermissionRequest(testResource, AccessMethod.READ))
            )
        )
    }


}
