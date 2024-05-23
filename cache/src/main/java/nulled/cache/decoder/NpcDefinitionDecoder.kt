package nulled.cache.decoder

import nulled.cache.fs.IndexedFileSystem
import nulled.cache.def.NpcDefinition
import org.apollo.util.BufferUtil
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.ByteBuffer
import java.util.*

class NpcDefinitionDecoder(private val fs: IndexedFileSystem) : Runnable {
    override fun run() {
        try {
            val config = fs.getArchive(0, 2)
            val data = config.getEntry("npc.dat").buffer
            val idx = config.getEntry("npc.idx").buffer

            val count = idx.getShort().toInt()
            var index = 2
            val indices = IntArray(count)

            for (i in 0 until count) {
                indices[i] = index
                index += idx.getShort().toInt()
            }

            val definitions = arrayOfNulls<NpcDefinition>(count)
            for (i in 0 until count) {
                data.position(indices[i])
                definitions[i] = decode(i, data)
            }
            println("loaded $count Npc definitions")
            NpcDefinition.init(definitions)
        } catch (e: IOException) {
            throw UncheckedIOException("Error decoding NpcDefinitions.", e)
        }
    }

    private fun decode(id: Int, buffer: ByteBuffer): NpcDefinition {
        val definition = NpcDefinition(id)

        while (true) {
            val opcode = buffer.get().toInt() and 0xFF

            if (opcode == 0) {
                return definition
            } else if (opcode == 1) {
                val length = buffer.get().toInt() and 0xFF
                val models = IntArray(length)
                for (index in 0 until length) {
                    models[index] = buffer.getShort().toInt()
                }
            } else if (opcode == 2) {
                definition.name = BufferUtil.readString(buffer)
            } else if (opcode == 3) {
                definition.description = BufferUtil.readString(buffer)
            } else if (opcode == 12) {
                definition.size = buffer.get().toInt()
            } else if (opcode == 13) {
                definition.standAnimation = buffer.getShort().toInt()
            } else if (opcode == 14) {
                definition.walkAnimation = buffer.getShort().toInt()
            } else if (opcode == 17) {
                definition
                    .setWalkAnimations(
                        buffer.getShort().toInt(),
                        buffer.getShort().toInt(),
                        buffer.getShort().toInt(),
                        buffer.getShort().toInt()
                    )
            } else if (opcode >= 30 && opcode < 40) {
                var action = BufferUtil.readString(buffer)
                if (action == "hidden") {
                    action = null
                }

                definition.setInteraction(opcode - 30, action)
            } else if (opcode == 40) {
                val length = buffer.get().toInt() and 0xFF
                val originalColours = IntArray(length)
                val replacementColours = IntArray(length)

                for (index in 0 until length) {
                    originalColours[index] = buffer.getShort().toInt()
                    replacementColours[index] = buffer.getShort().toInt()
                }
            } else if (opcode == 60) {
                val length = buffer.get().toInt() and 0xFF
                val additionalModels = IntArray(length)

                for (index in 0 until length) {
                    additionalModels[index] = buffer.getShort().toInt()
                }
            } else if (opcode >= 90 && opcode <= 92) {
                buffer.getShort() // Dummy
            } else if (opcode == 95) {
                definition.combatLevel = buffer.getShort().toInt()
            } else if (opcode == 97 || opcode == 98) {
                buffer.getShort()
            } else if (opcode == 100 || opcode == 101) {
                buffer.get()
            } else if (opcode == 102 || opcode == 103) {
                buffer.getShort()
            } else if (opcode == 106) {
                wrap(buffer.getShort().toInt())
                wrap(buffer.getShort().toInt())

                val count = buffer.get().toInt() and 0xFF
                val morphisms = IntArray(count + 1)
                Arrays.setAll(morphisms) { index: Int -> wrap(buffer.getShort().toInt()) }
            }
        }
    }

    private fun wrap(value: Int): Int {
        return if (value == 65535) -1 else value
    }
}