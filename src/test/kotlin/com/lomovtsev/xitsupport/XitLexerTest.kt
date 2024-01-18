package com.lomovtsev.xitsupport

import com.lomovtsev.xitsupport.psi.XitTypes
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.StringReader

class XitLexerTest {

    @Test
    fun sampleFileParsing() {
        val input = """
           stupid
           [ ] point
           
        """.trimIndent()
        val lexer = XitLexer(StringReader(input))
        lexer.reset(input, 0, input.length, 0)


        var element = lexer.advance()
        assertEquals(XitTypes.TEXT, element)
        element = lexer.advance()
        assertEquals(XitTypes.NEWLINE, element)
        element = lexer.advance()
        assertEquals(XitTypes.NEW_TASK, element)
        element = lexer.advance()
        assertEquals(XitTypes.NEWLINE, element)


    }


}