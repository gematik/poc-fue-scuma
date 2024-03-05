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
