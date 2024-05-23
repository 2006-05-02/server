package nulled.cache.decoder

import nulled.cache.def.LocDefinition
import nulled.cache.fs.IndexedFileSystem
import org.apollo.util.BufferUtil
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.ByteBuffer

class LocDefinitionDecoder(private val fs: IndexedFileSystem) : Runnable {
    override fun run() {
        try {
            val config = fs.getArchive(0, 2)
            val data = config.getEntry("loc.dat").buffer
            val idx = config.getEntry("loc.idx").buffer

            val count = idx.getShort().toInt()
            var index = 2
            val indices = IntArray(count)
            for (i in 0 until count) {
                indices[i] = index
                index += idx.getShort().toInt()
            }

            val definitions = arrayOfNulls<LocDefinition>(count)
            for (i in 0 until count) {
                data.position(indices[i])
                definitions[i] = decode(i, data)
            }
            println("loaded $count Loc definitions")
            LocDefinition.init(definitions)
        } catch (e: IOException) {
            throw UncheckedIOException("Error decoding ObjectDefinitions.", e)
        }
    }

    private fun decode(id: Int, data: ByteBuffer): LocDefinition {
        val definition = LocDefinition(id)
        while (true) {
            val opcode = data.get().toInt() and 0xFF

            if (opcode == 0) {
                return definition
            } else if (opcode == 1) {
                definition.code1 = data.get().toInt()
            } else if (opcode == 2) {
                definition.name = BufferUtil.readString(data)
            } else if (opcode == 3) {
                definition.desc = BufferUtil.readString(data)
            } else if (opcode == 5) {
                val amount = data.get().toInt() and 0xFF
                definition.code5 = IntArray(amount)
                for (i in 0 until amount) {
                    definition.code5!![i] = data.getShort().toInt()
                }
            } else if (opcode == 14) {
                definition.width = data.get().toInt() and 0xFF
            } else if (opcode == 15) {
                definition.length = data.get().toInt() and 0xFF
            } else if (opcode == 17) {
                definition.blockwalk = false
            } else if (opcode == 18) {
                definition.blockrange = false
            } else if (opcode == 19) {
                definition.active = (data.get().toInt() and 0xFF) == 1
            } else if (opcode == 21) {
                definition.hillskew = true
            } else if (opcode == 22) {
                definition.sharelight = true
            } else if (opcode == 23) {
                definition.occlude = true
            } else if (opcode == 24) {
                definition.anim = data.getShort().toInt()
                if (definition.anim == 65535) {
                    definition.anim = -1
                }
            } else if (opcode == 28) {
                definition.wallwidth = data.get().toInt()
            } else if (opcode == 29) {
                definition.ambient = data.get().toInt()
            } else if (opcode in 30..38) {
                var actions = definition.menuActions
                if (actions == null) {
                    actions = arrayOfNulls(10)
                }
                val action = BufferUtil.readString(data)
                actions[opcode - 30] = action
                definition.menuActions = actions
            } else if (opcode == 39) {
                definition.contrast = data.get().toInt()
            } else if (opcode == 40) {
                val amount = data.get().toInt() and 0xFF
                definition.recol_s = IntArray(amount)
                definition.recol_d = IntArray(amount)
                for (i in 0 until amount) {
                    definition.recol_s!![i] = data.getShort().toInt()
                    definition.recol_d!![i] = data.getShort().toInt()
                }
            } else if (opcode == 60) {
                definition.mapfunction = data.getShort().toInt()
            } else if (opcode == 62) {
                definition.mirror = true
            } else if (opcode == 64) {
                definition.shadow = false
            } else if (opcode == 65) {
                definition.resizex = data.getShort().toInt()
            } else if (opcode == 66) {
                definition.resizey = data.getShort().toInt()
            } else if (opcode == 67) {
                definition.resizez = data.getShort().toInt()
            } else if (opcode == 68) {
                definition.mapscene = data.getShort().toInt()
            } else if (opcode == 69) {
                definition.forceapproach = data.get().toInt()
            } else if (opcode == 70) {
                definition.offsetx = data.getShort().toInt()
            } else if (opcode == 71) {
                definition.offsety = data.getShort().toInt()
            } else if (opcode == 72) {
                definition.offsetz = data.getShort().toInt()
            } else if (opcode == 73) {
                definition.forcedecor = true
            } else if (opcode == 74) {
                definition.code74 = true
            } else if (opcode == 75) {
                definition.code75 = data.get().toInt()
            } else if (opcode == 77) {
                data.getShort()
                data.getShort()
                val count = data.get().toInt()
                for (i in 0..count) {
                    data.getShort()
                }
            } else {
                continue
            }
        }
    }
}