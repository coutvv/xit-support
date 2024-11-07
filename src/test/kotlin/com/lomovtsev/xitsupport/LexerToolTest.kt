package com.lomovtsev.xitsupport

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LexerToolTest {

    @Test
    fun testValidPriority() {
        assertTrue(LexerTool.isPriorityValid("."))
        assertTrue(LexerTool.isPriorityValid(".!"))
        assertTrue(LexerTool.isPriorityValid("!!!"))
        assertTrue(LexerTool.isPriorityValid("..!"))
        assertTrue(LexerTool.isPriorityValid("!.."))
        assertTrue(LexerTool.isPriorityValid("!"))
        assertTrue(LexerTool.isPriorityValid(".."))
        assertTrue(LexerTool.isPriorityValid("!!!!"))
        assertTrue(LexerTool.isPriorityValid("...!"))

        assertFalse(LexerTool.isPriorityValid(".!."))
        assertFalse(LexerTool.isPriorityValid(".!!!.!!."))
        assertFalse(LexerTool.isPriorityValid("!!!.!!"))
        assertFalse(LexerTool.isPriorityValid(".!!!.!!"))
        assertFalse(LexerTool.isPriorityValid(""))
        assertFalse(LexerTool.isPriorityValid("!.!"))
        assertFalse(LexerTool.isPriorityValid(".!..!"))
        assertFalse(LexerTool.isPriorityValid("abc"))
        assertFalse(LexerTool.isPriorityValid(" .!"))
        assertFalse(LexerTool.isPriorityValid(".! "))
    }

}