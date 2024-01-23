package com.lomovtsev.xitsupport

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.CodeInsightColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.editor.markup.EffectType
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.keymap.impl.ui.Hyperlink
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.lomovtsev.xitsupport.psi.XitTypes
import com.vladsch.flexmark.util.html.ui.Color
import org.apache.http.annotation.Obsolete

class XitSyntaxHighlighter : SyntaxHighlighterBase() {
    companion object {
        val SEPARATOR = createTextAttributesKey("XIT_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)

        val TEXT = createTextAttributesKey("XIT_TEXT", DefaultLanguageHighlighterColors.STRING)
        val BAD_CHAR = createTextAttributesKey("XIT_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
        val OPEN_CHECKBOX = createTextAttributesKey("OPEN CHECKBOX", DefaultLanguageHighlighterColors.KEYWORD)
        val TITLE = createTextAttributesKey("TITLE", DefaultLanguageHighlighterColors.HIGHLIGHTED_REFERENCE)

        val HIGHLIGHT = createTextAttributesKey("TITLE_LINK", CodeInsightColors.HYPERLINK_ATTRIBUTES)
        val COMMENT = createTextAttributesKey("COMMENTING", DefaultLanguageHighlighterColors.LINE_COMMENT)
        /**
         * TODO: delete (custom attribute key)
         */
        val TITLE_WORD = createTextAttributesKey("TITLE WORD", xitTitle())

        val ONGOING_CHECKBOX = createTextAttributesKey("Ongoing Checkbox", DefaultLanguageHighlighterColors.METADATA)
        private fun xitTitle(): TextAttributes {
            val result = TextAttributes()
            result.effectType = EffectType.LINE_UNDERSCORE
            result.foregroundColor = Color.WHITE
//            result.backgroundColor =  Color.CYAN

            return result
        }
    }


    val BAD_KEYS = arrayOf(BAD_CHAR)
    val OCH_KEYS = arrayOf(OPEN_CHECKBOX)
    val OCH_WORD_KEYS = arrayOf(TEXT)

    val TITLE_KEYS = arrayOf(HIGHLIGHT)
    val EMPTY_KEYS = emptyArray<TextAttributesKey>()

    val DONE_KEYS = arrayOf(COMMENT)

    val ONGOING_CHB_KEYS = arrayOf(ONGOING_CHECKBOX) // yeah!
    val ONGOING_WORD = arrayOf(TEXT)

    val OBSOLETE_KEYS = arrayOf(COMMENT)

    override fun getHighlightingLexer(): Lexer {
        return XitLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
        return when (tokenType) {
            TokenType.BAD_CHARACTER -> BAD_KEYS
            XitTypes.TITLE_WORD -> TITLE_KEYS

            XitTypes.OPEN_CHECKBOX -> OCH_KEYS
            XitTypes.OCH_WORD -> OCH_WORD_KEYS

            XitTypes.DONE_CHECKBOX -> DONE_KEYS // TODO:
            XitTypes.CCH_WORD -> DONE_KEYS

            XitTypes.ONGOING_CHECKBOX -> ONGOING_CHB_KEYS
            XitTypes.GCH_WORD -> ONGOING_WORD

            XitTypes.OBS_WORD -> OBSOLETE_KEYS
            XitTypes.OBSOLETE_CHECKBOX -> OBSOLETE_KEYS

            else -> EMPTY_KEYS
        }
    }
}