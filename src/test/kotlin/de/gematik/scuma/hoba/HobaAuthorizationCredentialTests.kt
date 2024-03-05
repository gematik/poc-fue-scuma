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
