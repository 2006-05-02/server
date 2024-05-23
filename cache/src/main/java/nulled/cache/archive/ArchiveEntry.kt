package nulled.cache.archive

import java.nio.ByteBuffer

class ArchiveEntry(val id: Int, initialBuffer: ByteBuffer) {
    var buffer: ByteBuffer
        get() = field.duplicate()

    init {
        buffer = initialBuffer
    }
}