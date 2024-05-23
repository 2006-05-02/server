package nulled.cache.util

object FileSystemConstants {
    const val ARCHIVE_COUNT: Int = 9
    const val CHUNK_SIZE: Int = 512
    const val HEADER_SIZE: Int = 8
    const val BLOCK_SIZE: Int = HEADER_SIZE + CHUNK_SIZE
    const val INDEX_SIZE: Int = 6
}