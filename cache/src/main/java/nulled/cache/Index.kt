package nulled.cache

import com.google.common.base.Preconditions
import org.apollo.cache.FileSystemConstants

class Index(val size: Int, val block: Int) {
    companion object {
        fun decode(buffer: ByteArray): Index {
            Preconditions.checkArgument(buffer.size == FileSystemConstants.INDEX_SIZE, "Incorrect buffer length.")

            val size =
                (buffer[0].toInt() and 0xFF) shl 16 or ((buffer[1].toInt() and 0xFF) shl 8) or (buffer[2].toInt() and 0xFF)
            val block =
                (buffer[3].toInt() and 0xFF) shl 16 or ((buffer[4].toInt() and 0xFF) shl 8) or (buffer[5].toInt() and 0xFF)

            return Index(size, block)
        }
    }
}