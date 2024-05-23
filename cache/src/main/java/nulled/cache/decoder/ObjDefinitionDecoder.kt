package nulled.cache.decoder

import nulled.cache.def.ObjDefinition
import nulled.cache.fs.IndexedFileSystem
import org.apollo.util.BufferUtil
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.ByteBuffer

class ObjDefinitionDecoder(private val fs: IndexedFileSystem) : Runnable {
    override fun run() {
        try {
            val config = fs.getArchive(0, 2)
            val data = config.getEntry("obj.dat").buffer
            val idx = config.getEntry("obj.idx").buffer

            val count = idx.getShort().toInt()
            var index = 2
            val indices = IntArray(count)
            for (i in 0 until count) {
                indices[i] = index
                index += idx.getShort().toInt()
            }

            val definitions = arrayOfNulls<ObjDefinition>(count)
            for (i in 0 until count) {
                data.position(indices[i])
                definitions[i] = decode(i, data)
            }
            println("loaded $count Obj definitions")
            ObjDefinition.init(definitions)
        } catch (e: IOException) {
            throw UncheckedIOException("Error decoding ItemDefinitions.", e)
        }
    }

    private fun decode(id: Int, buffer: ByteBuffer): ObjDefinition {
        val definition = ObjDefinition(id)
        while (true) {
            val opcode = buffer.get().toInt() and 0xFF

            if (opcode == 0) {
                return definition
            } else if (opcode == 1) {
                definition.model = buffer.getShort().toInt()
            } else if (opcode == 2) {
                definition.name = BufferUtil.readString(buffer)
            } else if (opcode == 3) {
                definition.desc = BufferUtil.readString(buffer)
            } else if (opcode == 4) {
                definition.zoom2d = buffer.getShort().toInt()
            } else if (opcode == 5) {
                definition.xan2d = buffer.getShort().toInt()
            } else if (opcode == 6) {
                definition.yan2d = buffer.getShort().toInt()
            } else if (opcode == 7) {
                definition.xof2d = buffer.getShort().toInt()
                if (definition.xof2d > 32767) {
                    definition.xof2d -= 65536
                }
            } else if (opcode == 8) {
                definition.yof2d = buffer.getShort().toInt()
                if (definition.yof2d > 32767) {
                    definition.yof2d -= 65536
                }
            } else if (opcode == 10) {
                buffer.getShort()
            } else if (opcode == 11) {
                definition.stackable = true
            } else if (opcode == 12) {
                definition.cost = buffer.getInt()
            } else if (opcode == 16) {
                definition.members = true
            } else if (opcode == 23) {
                definition.manwear = buffer.getShort().toInt()
                definition.manwearOffsetY = buffer.get().toInt()
            } else if (opcode == 24) {
                definition.manwear2 = buffer.getShort().toInt()
            } else if (opcode == 25) {
                definition.womanwear = buffer.getShort().toInt()
                definition.womanwearOffsetY = buffer.get().toInt()
            } else if (opcode == 26) {
                definition.womanwear2 = buffer.getShort().toInt()
            } else if (opcode in 30..34) {
                var str = BufferUtil.readString(buffer)
                if (str.equals("hidden", ignoreCase = true)) {
                    str = null
                }
                definition.setGroundAction(opcode - 30, str)
            } else if (opcode in 35..39) {
                definition.setInventoryAction(opcode - 35, BufferUtil.readString(buffer))
            } else if (opcode == 40) {
                val colourCount = buffer.get().toInt() and 0xFF
                definition.recol_s = IntArray(colourCount)
                definition.recol_d = IntArray(colourCount)
                for (i in 0 until colourCount) {
                    definition.recol_s!![i] = buffer.getShort().toInt()
                    definition.recol_d!![i] = buffer.getShort().toInt()
                }
            } else if (opcode == 78) {
                definition.manwear3 = buffer.getShort().toInt()
            } else if (opcode == 79) {
                definition.womanwear3 = buffer.getShort().toInt()
            } else if (opcode == 90) {
                definition.manhead = buffer.getShort().toInt()
            } else if (opcode == 91) {
                definition.womanhead = buffer.getShort().toInt()
            } else if (opcode == 92) {
                definition.manhead2 = buffer.getShort().toInt()
            } else if (opcode == 93) {
                definition.womanhead2 = buffer.getShort().toInt()
            } else if (opcode == 95) {
                definition.zan2d = buffer.getShort().toInt()
            } else if (opcode == 97) {
                definition.certlink = buffer.getShort().toInt() and 0xFFFF
            } else if (opcode == 98) {
                definition.certtemplate = buffer.getShort().toInt() and 0xFFFF
            } else if (opcode in 100..109) {
                if (definition.countobj == null) {
                    definition.countobj = IntArray(10)
                    definition.countco = IntArray(10)
                }
                definition.countobj!![opcode - 100] = buffer.getShort().toInt()
                definition.countco!![opcode - 100] = buffer.getShort().toInt()
            } else if (opcode in 110..112) {
                buffer.getShort()
            } else if (opcode == 113 || opcode == 114) {
                buffer.get()
            } else if (opcode == 115) {
                definition.team = buffer.get().toInt() and 0xFF
            }
        }
    }
}