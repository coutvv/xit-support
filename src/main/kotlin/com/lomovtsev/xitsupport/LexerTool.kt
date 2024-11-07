package com.lomovtsev.xitsupport

object LexerTool {
    var lastCheckboxPlace: Int? = null // last checkbox state

    fun isPriorityToken(currentPlace: Int, priority: CharSequence): Boolean {
        return lastCheckboxPlace!= null && currentPlace - 4 == lastCheckboxPlace
                && isPriorityValid(priority)
    }

    /**
     * The dots MUST appear either before or after the exclamation mark(s).
     * (So they can neither appear in between nor on both sides.)
     */
    fun isPriorityValid(priority: CharSequence): Boolean {
        if (priority.isEmpty()) {
            return false
        }
        if (priority.length <= 2) { // samples: ".", ".!"
            return true
        }
        val startSymbol = priority[0]
        var symbolChanged = false
        val nextSymbol = if (startSymbol == '!') '.' else '!'
        for (i: Int in 1 until priority.length) {
            val sym = priority[i]
            when {
                symbolChanged && sym == startSymbol -> return false
                !symbolChanged && sym == nextSymbol -> symbolChanged = true
                sym != startSymbol && sym != nextSymbol -> return false // other symbols
            }
        }
        return true
    }
}
