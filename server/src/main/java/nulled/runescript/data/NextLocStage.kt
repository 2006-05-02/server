package nulled.runescript.data

import nulled.runescript.RuneScriptException

object NextLocStage {
    fun get(currentLoc: String) : Int {
        return when (currentLoc) {
            "loc_3033" -> 1342
            "loc_3034" -> 1342
            "loc_3036" -> 1348
            else -> throw RuneScriptException("invallid NextLocStage")
        }
    }
}