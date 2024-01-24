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
            when {
                symbolChanged && priority[i] == startSymbol -> return false
                !symbolChanged && priority[i] == nextSymbol -> symbolChanged = true
            }
        }
        return true
    }
}