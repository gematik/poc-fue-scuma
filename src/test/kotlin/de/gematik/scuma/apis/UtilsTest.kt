package de.gematik.scuma.apis

import de.gematik.kether.eth.types.Quantity
import de.gematik.kether.extensions.toHex
import org.junit.jupiter.api.Test
import java.util.Queue
import java.util.UUID

/**
 * Created by rk on 02.08.2022.
 * gematik.de
 */
class UtilsTests {

    @Test
    fun uuidFromToQuantity() {
        var uuid = UUID(0,1)
        var quantity = uuid.toQuantity()
        assert(uuid.equals(quantity.toUUID()))
        uuid = UUID.randomUUID()
        quantity = uuid.toQuantity()
        assert(uuid.equals(quantity.toUUID()))
    }
}
