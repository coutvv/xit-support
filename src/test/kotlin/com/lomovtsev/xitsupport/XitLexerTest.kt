package com.lomovtsev.xitsupport

import com.intellij.psi.tree.IElementType
import com.lomovtsev.xitsupport.psi.XitTypes.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.StringReader

@OptIn(ExperimentalStdlibApi::class) // it is not compiled without it
class XitLexerTest {

    @Test
    fun difficultXitParsing() {
        val input = """
            Это title
            [ ] open checkbox
                continue
            [x] close чекбокс
                continue
            [@] ongoing чекбокс
                continue
            [?] question чекбокс
                continue
            [~] obsolete чекбокс
                чекбокс
            
            other title
            [ ] checkbox
            
            
        """.trimIndent()

        val tokens = readLexTokens(input)
        showTokens(tokens)

        assertTokenEquals(listOf(
            TITLE_WORD, TITLE_WORD, TITLE_WORD, NEWLINE,

            OPEN_CHECKBOX, OCH_WORD, OCH_WORD, OCH_WORD, NEWLINE,
            DESC_INDENT, OCH_WORD, NEWLINE,
            DONE_CHECKBOX, CCH_WORD, CCH_WORD, CCH_WORD, NEWLINE, // 2nd cch_word is space
            DESC_INDENT, CCH_WORD, NEWLINE,
            ONGOING_CHECKBOX, GCH_WORD, GCH_WORD, GCH_WORD, NEWLINE,
            DESC_INDENT, GCH_WORD, NEWLINE,
            QUESTION_CHECKBOX, QUESTION_WORD, QUESTION_WORD, QUESTION_WORD, NEWLINE,
            DESC_INDENT, QUESTION_WORD, NEWLINE,
            OBSOLETE_CHECKBOX, OBS_WORD, OBS_WORD, OBS_WORD, NEWLINE,
            DESC_INDENT, OBS_WORD, NEWLINE,
            GROUP_END,

            TITLE_WORD, TITLE_WORD, TITLE_WORD, NEWLINE,
            OPEN_CHECKBOX, OCH_WORD, NEWLINE,
            GROUP_END

        ), tokens)


    }

    @Test
    fun sampleXitParsing() {
        val input = """
           title
           [ ] описание
               continue
           
           [ ] sample
           
           
        """.trimIndent()

        val tokens = readLexTokens(input)
        showTokens(tokens)

        assertTokenEquals(
            listOf(
                TITLE_WORD, NEWLINE,
                OPEN_CHECKBOX, OCH_WORD, NEWLINE,
                DESC_INDENT, OCH_WORD, NEWLINE,
                GROUP_END,
                OPEN_CHECKBOX, OCH_WORD, NEWLINE,
                GROUP_END
            ),
            tokens
        )
    }

    private fun readTokens(lexer: XitLexer): List<IElementType> {
        val result = mutableListOf<IElementType>()
        var element = lexer.advance()
        while (element != null) {
            result.add(element)
            element = lexer.advance()

        }
        return result
    }

    private fun readLexTokens(input: String): List<IElementType> {
        val lexer = XitLexer(StringReader(input))
        lexer.reset(input, 0, input.length, 0)
        return readTokens(lexer)
    }

    /**
     * used for debugging issues
     */
    private fun showTokens(tokens: List<IElementType>) {
        val bracket = "#".repeat(tokens.maxNameSize())
        println(bracket)
        tokens.forEach {
            println("  ${it.debugName}")
        }
        println(bracket)
    }

    private fun assertTokenEquals(expected: List<IElementType>, original : List<IElementType>) {

        assertEquals(
            expected,
            original
        ) {
            val expectedElSize = expected.maxNameSize()
            val originElSize = original.maxNameSize()
            val result = mutableListOf("")
            val maxSize = if (expected.size > original.size) {
                expected.size
            } else original.size
            for (i in 0..<maxSize) {
                val exElement = expected.getOrNull(i)?.fullDebugName(expectedElSize) ?: nullName(expectedElSize)
                val origElement = original.getOrNull(i)?.fullDebugName(originElSize) ?: nullName(originElSize)
                result.add("$exElement \t <-> \t $origElement")
            }

            result.joinToString("\n")
        }
    }

    fun IElementType.fullDebugName(maxSize: Int): String {
        val spaceX = maxSize - debugName.length
        val suffix = if (spaceX > 0) {
            " ".repeat(spaceX)
        } else ""
        return debugName + suffix
    }

    fun nullName(maxSize: Int): String {
        val nullName = "null"
        val spaceX = maxSize - nullName.length
        val suffix = if (spaceX > 0) {
            " ".repeat(spaceX)
        } else ""
        return nullName + suffix
    }

    fun List<IElementType>.maxNameSize(): Int {
        return maxOfOrNull { it.toString().length } ?: 15
    }

}