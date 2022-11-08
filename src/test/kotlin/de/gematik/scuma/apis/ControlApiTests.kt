package de.gematik.scuma.apis

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.*


/**
 * Created by rk on 02.08.2022.
 * gematik.de
 */
class ControlApiTests {

    @Test
    fun getProviderCount() {
        println(controlApi.getProviderCount())
    }

    @Test
    fun getProviders() {
        println(controlApi.getProviders())
    }

    @Test
    fun registerProvider() {
        runBlocking {
            assert(controlApi.registerProvider(protectionAuthorizationId))
        }
    }

    @Test
    fun setRule() {
        runBlocking {
            assert(
                controlApi.setRule(
                    testResource,
                    userId,
                    BitSet().apply { set(CRUD_READ); set(CRUD_UPDATE) }
                )
            )
        }
    }

    @Test
    fun getPolicy() {
        println(controlApi.getPolicy(testResource))
    }

    @Test
    fun deleteRule() {
        runBlocking {
            assert(controlApi.deleteRule(testResource, 0))
        }
    }

    @Test
    fun unregisterAllProviders() {
        runBlocking {
            assert(controlApi.unregisterAllProviders())
        }
    }

    @Test
    fun unregisterProvider() {
        runBlocking {
            assert(controlApi.unregisterProvider(protectionAuthorizationId))
        }
    }
}
