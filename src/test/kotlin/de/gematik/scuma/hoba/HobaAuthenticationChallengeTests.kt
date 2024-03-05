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

package de.gematik.scuma.hoba

import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.random.Random

/**
 * Created by rk on 02.08.2022.
 * gematik.de
 */
@ExperimentalSerializationApi
class HobaAuthenticationChallengeTests {

    @Test
    fun toStringTest() {
        val challenge = Random.nextBytes(32)
        val authenticationChallenge = HobaAuthenticationChallenge(challenge = challenge)
        assert(authenticationChallenge.toString() == """HOBA challenge="${Base64.getUrlEncoder().encodeToString(challenge)}" max-age=10""")
    }

    @Test
    fun fromStringTest() {
        val challenge = Random.nextBytes(32)
        val hobaChallengeString = """HOBA challenge="${Base64.getUrlEncoder().encodeToString(challenge)}" max-age=10"""
        val authenticationChallenge = HobaAuthenticationChallenge.fromString(hobaChallengeString)
        assert(authenticationChallenge.challenge.contentEquals(challenge) && authenticationChallenge.maxAge == 10)
    }
}
