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