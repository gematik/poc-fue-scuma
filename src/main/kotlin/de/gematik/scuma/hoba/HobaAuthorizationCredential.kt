package de.gematik.scuma.hoba

import de.gematik.kether.abi.types.AbiAddress
import de.gematik.kether.extensions.keccak
import org.apache.tuweni.bytes.Bytes
import org.apache.tuweni.bytes.Bytes32
import org.bouncycastle.asn1.sec.SECNamedCurves
import org.hyperledger.besu.crypto.SECP256K1
import org.hyperledger.besu.crypto.SECPPrivateKey
import org.hyperledger.besu.crypto.SECPSignature
import java.util.*

/**
 * Created by rk on 03.11.2022.
 * gematik.de
 */
class HobaAuthorizationCredential(val challenge: ByteArray, val nonce: ByteArray) {

    constructor(challenge: String, nonce: ByteArray) : this(Base64.getUrlDecoder().decode(challenge), nonce)

    private val alg = 3 // SECP256K1 with keccak256
    lateinit var kid: AbiAddress
        private set
    lateinit var signature: SECPSignature
        private set

    companion object {
        fun fromString(credential: String): HobaAuthorizationCredential {
            val parameterMap = credential.toParameterMap()
            require(parameterMap.containsKey("result")) { "malformed credential - parameter result missing" }
            val resultMap = parameterMap.get("result")!!.trim('"').split('.')
            require(resultMap.size == 4) { "malformed credential - invalid number of result parts: expected 4 is ${resultMap.size}" }
            return HobaAuthorizationCredential(resultMap[1], Base64.getUrlDecoder().decode(resultMap[2])).apply {
                kid = AbiAddress(resultMap[0])
                val order = SECNamedCurves.getByName(SECP256K1.CURVE_NAME).n
                val signatureBytes = Bytes.wrap(Base64.getUrlDecoder().decode(resultMap[3]))
                signature = SECPSignature.decode(signatureBytes, order)
            }
        }
    }

    fun sign(privateKey: SECPPrivateKey, origin: String, realm: String? = null) {
        val signer = SECP256K1()
        val secKeyPair = signer.createKeyPair(privateKey)
        kid = secKeyPair.publicKey.toAccount()
        signature = signer.sign(Bytes32.wrap(getHobaTbs(origin, realm).keccak()), secKeyPair)
    }

    fun verify(origin: String, realm: String? = null): Boolean {
        require(::signature.isInitialized) { "credential isn't signed" }
        val signer = SECP256K1()
        val hash = Bytes32.wrap(getHobaTbs(origin, realm).keccak())
        val publicKey = signer.recoverPublicKeyFromSignature(hash, signature).get()
        return publicKey.toAccount() == kid && signer.verify(hash, signature, publicKey)
    }

    override fun toString(): String {
        return """HOBA result="$kid.${Base64.getUrlEncoder().encodeToString(challenge)}.${Base64.getUrlEncoder().encodeToString(nonce)}.${
            Base64.getUrlEncoder().encodeToString(signature.encodedBytes().toArray())
        }""""
    }

    private fun getHobaTbs(origin: String, realm: String? = null): String {
        val re = if (realm != null) """realm="$realm""" else ""
        val ch = Base64.getUrlEncoder().encodeToString(challenge)
        return "${nonce.size}:${nonce}${alg.toString().length}:${alg}${origin.length}:$origin${re.length}:$re${kid.toString().length}:${kid}${ch.length}:$ch"
    }

}