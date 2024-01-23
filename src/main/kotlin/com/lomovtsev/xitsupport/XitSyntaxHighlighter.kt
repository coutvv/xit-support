package com.lomovtsev.xitsupport

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.lomovtsev.xitsupport.psi.XitTypes

class XitSyntaxHighlighter : SyntaxHighlighterBase() {
    companion object {
        val SEPARATOR = createTextAttributesKey("XIT_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val TASK = createTextAttributesKey("XIT_TASK", DefaultLanguageHighlighterColors.KEYWORD)
        val TEXT = createTextAttributesKey("XIT_TEXT", DefaultLanguageHighlighterColors.STRING)
        val BAD_CHAR = createTextAttributesKey("XIT_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
        val OPEN_CHECKBOX = createTextAttributesKey("OPEN CHECKBOX", DefaultLanguageHighlighterColors.KEYWORD)
    }


    val SEPARATOR_KEYS = arrayOf(SEPARATOR)
    val TASK_KEYS = arrayOf(TASK)
    val TEXT_KEYS = arrayOf(TEXT)
    val BAD_KEYS = arrayOf(BAD_CHAR)
    val OCH_KEYS = arrayOf(OPEN_CHECKBOX)

    val EMPTY_KEYS = emptyArray<TextAttributesKey>()
    override fun getHighlightingLexer(): Lexer {
        return XitLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
        return when (tokenType) {
            XitTypes.POINT -> TASK_KEYS
//            XitTypes.TEXT -> TEXT_KEYS
            TokenType.BAD_CHARACTER -> BAD_KEYS
            XitTypes.OPEN_CHECKBOX -> OCH_KEYS
            else -> EMPTY_KEYS
        }
    }
}