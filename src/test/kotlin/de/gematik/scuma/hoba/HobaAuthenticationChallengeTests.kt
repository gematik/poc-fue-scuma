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
