package de.gematik.scuma.hoba

import de.gematik.kether.abi.types.AbiAddress
import de.gematik.kether.extensions.keccak
import org.hyperledger.besu.crypto.SECPPublicKey

/**
 * Created by rk on 02.11.2022.
 * gematik.de
 */


internal fun String.toParameterMap(): Map<String, String> {
    val result = mutableMapOf<String, String>()
    trim().replace("\\s+".toRegex(), " ").substringAfter(' ').split(' ').forEach {
        result.put(it.substringBefore('='), it.substringAfter('='))
    }
    return result
}

