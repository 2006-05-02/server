package nulled.cache.util

class FileDescriptor(val type: Int, val file: Int) {
    override fun equals(other: Any?): Boolean {
        return other is FileDescriptor && other.type == type && other.file == file
    }

    override fun hashCode(): Int {
        return file * FileSystemConstants.ARCHIVE_COUNT + type
    }
}