package com.lomovtsev.xitsupport

object LexerTool {
    var lastCheckboxPlace: Int? = null // last checkbox state
    fun isPriorityToken(currentPlace: Int): Boolean {
        return lastCheckboxPlace!= null && currentPlace - 4 == lastCheckboxPlace
    }
}