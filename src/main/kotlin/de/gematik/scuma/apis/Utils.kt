package de.gematik.scuma.apis

import de.gematik.kether.eth.types.Quantity
import java.nio.ByteBuffer
import java.util.*

/**
 * Created by rk on 07.11.2022.
 * gematik.de
 */

fun UUID.toQuantity() : Quantity {
    val b = ByteBuffer.wrap(ByteArray(16))
    b.putLong(mostSignificantBits)
    b.putLong(leastSignificantBits)
    return Quantity(b.array())
}


fun Quantity.toUUID() : UUID {
    var byteArray = toBigInteger().toByteArray()
    byteArray = byteArray.copyInto(ByteArray(16), 16-byteArray.size)
    val b = ByteBuffer.wrap(byteArray)
    return UUID(b.long, b.long)
}