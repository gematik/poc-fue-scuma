package de.gematik.scuma.hoba

import de.gematik.kether.crypto.EcdsaPrivateKey
import de.gematik.kether.crypto.EllipticCurve
import de.gematik.kether.eth.types.Address
import de.gematik.kether.extensions.hexToByteArray
import de.gematik.kether.extensions.toByteArray
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.random.Random

/**
 * Created by rk on 02.08.2022.
 * gematik.de
 */
@ExperimentalSerializationApi
class HobaAuthorizationCredentialTests {

    val resourceOwnerId = Address("0xfe3b557e8fb62b89f4916b721be55ceb828dbd73")
    val resourceOwnerPrivateKey = "0x8f2a55949038a9610f50fb23b5883af3b4ecb3c3bb792cbcefbd1542c692be63".hexToByteArray()
    val origin = "http//:example.org:8080"
    val challenge = Random.nextBytes(32)
    val nonce = Random.nextBytes(32)


    @Test
    fun signatureTest() {
        val authorizationCredential = HobaAuthorizationCredential(challenge, nonce)
        val privateKey = EcdsaPrivateKey(resourceOwnerPrivateKey, EllipticCurve.secp256k1)
        authorizationCredential.sign(privateKey, origin)
        assert(authorizationCredential.kid.equals(resourceOwnerId))
        assert(authorizationCredential.verify(origin))
    }

    @Test
    fun fromToStringTest() {
        val authorizationCredential1 = HobaAuthorizationCredential(challenge, nonce)
        val privateKey = EcdsaPrivateKey(resourceOwnerPrivateKey, EllipticCurve.secp256k1)
        authorizationCredential1.sign(privateKey, origin)
        val authorizationCredential2 = HobaAuthorizationCredential.fromString(authorizationCredential1.toString())
        assert(
            authorizationCredential1.kid.equals(authorizationCredential2.kid) &&
                    authorizationCredential1.challenge.contentEquals(authorizationCredential2.challenge) &&
                    authorizationCredential1.nonce.contentEquals(authorizationCredential2.nonce)
        )
    }
}
