package com.lomovtsev.xitsupport

import com.intellij.psi.tree.IElementType
import com.lomovtsev.xitsupport.psi.XitTypes.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.StringReader

class XitLexerTest {

    @Test
    fun testXitParsingWithDueDate() {
        val input = """
            title
            [ ] todo -> 2023/01/29
            [x] todo2 -> 2023-01-29
            [@] todo3 -> 2023-01
            [?] todo4 -> 2023-Q3
            [~] todo5 -> 2023-W05
            [ ] todo6 -> 23.01.2023
        """.trimIndent()

        val tokens = readLexTokens(input)

        assertTokenEquals(listOf(
            TITLE_WORD, NEWLINE,
            OPEN_CHECKBOX, OCH_WORD, OCH_WORD, DUE_DATE, NEWLINE,
            DONE_CHECKBOX, CCH_WORD, CCH_WORD, PASSIVE_DUE_DATE, NEWLINE,
            ONGOING_CHECKBOX, GCH_WORD, GCH_WORD, DUE_DATE, NEWLINE,
            QUESTION_CHECKBOX, QUESTION_WORD, QUESTION_WORD, DUE_DATE, NEWLINE,
            OBSOLETE_CHECKBOX, OBS_WORD, OBS_WORD, OBSOLETE_DUE_DATE, NEWLINE,
            OPEN_CHECKBOX, OCH_WORD, OCH_WORD, DUE_DATE,
        ), tokens)
    }
    @Test
    fun checkXitParsingWithSimpleHashTag() {
        val input = """
            title
            [ ] todo #hashtag last
            [ ] todo_#hashtag last
            [x] done #hashtag last
            [@] ongoing #hashtag last
            [~] obsolete #hashtag last
            [?] question #hashtag last
        """.trimIndent()

        val tokens = readLexTokens(input)

        assertTokenEquals(listOf(
            TITLE_WORD, NEWLINE,
            OPEN_CHECKBOX, OCH_WORD, OCH_WORD, HASHTAG, OCH_WORD, OCH_WORD, NEWLINE,
            OPEN_CHECKBOX, OCH_WORD, HASHTAG, OCH_WORD, OCH_WORD, NEWLINE,
            DONE_CHECKBOX, CCH_WORD, CCH_WORD, PASSIVE_HASHTAG, CCH_WORD, CCH_WORD, NEWLINE,
            ONGOING_CHECKBOX, GCH_WORD, GCH_WORD, HASHTAG, GCH_WORD, GCH_WORD, NEWLINE,
            OBSOLETE_CHECKBOX, OBS_WORD, OBS_WORD, OBSOLETE_HASHTAG, OBS_WORD, OBS_WORD, NEWLINE,
            QUESTION_CHECKBOX, QUESTION_WORD, QUESTION_WORD, HASHTAG, QUESTION_WORD, QUESTION_WORD,
        ), tokens)

    }

    @Test
    fun testXitParsingWithPriority() {
        val input = """
            title
            [ ] ! todo3
            [ ] !!! todo
            [ ] ... todo2
            [ ] ..! other
            [ ] with_fake_priority !!!
            [x] !!! other
            [@] ..! other
            [?] ..! other
            [~] ..! other
            [~] .!. wrong_priority
        """.trimIndent()

        val tokens = readLexTokens(input)

        assertTokenEquals(listOf(
            TITLE_WORD, NEWLINE,
            OPEN_CHECKBOX, PRIORITY, OCH_WORD, OCH_WORD, NEWLINE,
            OPEN_CHECKBOX, PRIORITY, OCH_WORD, OCH_WORD, NEWLINE,
            OPEN_CHECKBOX, PRIORITY, OCH_WORD, OCH_WORD, NEWLINE,
            OPEN_CHECKBOX, PRIORITY, OCH_WORD, OCH_WORD, NEWLINE,
            OPEN_CHECKBOX, OCH_WORD, OCH_WORD, OCH_WORD, NEWLINE,
            DONE_CHECKBOX, PASSIVE_PRIORITY, CCH_WORD, CCH_WORD, NEWLINE,
            ONGOING_CHECKBOX, PRIORITY, GCH_WORD, GCH_WORD, NEWLINE,
            QUESTION_CHECKBOX, PRIORITY, QUESTION_WORD, QUESTION_WORD, NEWLINE,
            OBSOLETE_CHECKBOX, OBSOLETE_PRIORITY, OBS_WORD, OBS_WORD, NEWLINE,
            OBSOLETE_CHECKBOX, OBS_WORD, OBS_WORD, OBS_WORD,  // wrong priority is word!
        ), tokens)
    }

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
            OPEN_CHECKBOX, OCH_WORD,

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
                OPEN_CHECKBOX, OCH_WORD,
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
            var lastNotEqualIndex = -1
            for (i in 0 until maxSize) {
                val exElement = expected.getOrNull(i)?.fullDebugName(expectedElSize) ?: nullName(expectedElSize)
                val origElement = original.getOrNull(i)?.fullDebugName(originElSize) ?: nullName(originElSize)
                result.add("$i \t $exElement \t <-> \t $origElement")
                if (exElement != origElement) {
                    lastNotEqualIndex = i
                }
            }
            result.add("\n\n[Last change index]: $lastNotEqualIndex")

            result.joinToString(separator = "\n", postfix = "\n\n")
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