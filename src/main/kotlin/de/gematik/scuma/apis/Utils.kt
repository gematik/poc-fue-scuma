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

import de.gematik.kether.eth.types.Quantity
import java.nio.ByteBuffer
import java.util.*

/**
 * Created by rk on 07.11.2022.
 * gematik.de
 */

fun UUID.toQuantity(): Quantity {
    val b = ByteBuffer.wrap(ByteArray(16))
    b.putLong(mostSignificantBits)
    b.putLong(leastSignificantBits)
    return Quantity(b.array())
}


fun Quantity.toUUID(): UUID {
    var byteArray = toBigInteger().toByteArray()
    byteArray = byteArray.copyInto(ByteArray(16), 16 - byteArray.size)
    val b = ByteBuffer.wrap(byteArray)
    return UUID(b.long, b.long)
}

fun BitSet.toLong(): Long {
    var value = 0L
    var mask = 1L
    for (i in 0 until this.length()) {
        value += if (this.get(i)) mask else 0L
        mask = mask shl 1
    }
    return value
}

fun Long.toBitSet(): BitSet {
    var mask = 1L
    val bits = BitSet()
    for (i in 0 until 64) {
        if ((this and mask) != 0L) {
            bits.set(i)
        }
        mask = mask shl 1
    }
    return bits
}