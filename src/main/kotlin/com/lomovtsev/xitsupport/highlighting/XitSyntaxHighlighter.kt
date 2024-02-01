package com.lomovtsev.xitsupport.highlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.CodeInsightColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.lomovtsev.xitsupport.XitLexerAdapter
import com.lomovtsev.xitsupport.psi.XitTypes

class XitSyntaxHighlighter : SyntaxHighlighterBase() {
    companion object {
        val BAD_CHAR = createTextAttributesKey("XIT_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

        val ACTIVE_TEXT = createTextAttributesKey("XIT_ACTIVE_TEXT", DefaultLanguageHighlighterColors.CLASS_NAME)
        val PASSIVE_TEXT = createTextAttributesKey("XIT_PASSIVE_TEXT", DefaultLanguageHighlighterColors.LINE_COMMENT)

        val TITLE_TEXT = createTextAttributesKey("XIT_TITLE_TEXT", CodeInsightColors.HYPERLINK_ATTRIBUTES)

        val OPEN_CHECKBOX = createTextAttributesKey(
            "XIT_OPEN_CHECKBOX",
            DefaultLanguageHighlighterColors.NUMBER
        )
        val OPEN_DESCRIPTION = createTextAttributesKey(
            "XIT_OPEN_DESCRIPTION",
            ACTIVE_TEXT
        )

        val DONE_CHECKBOX = createTextAttributesKey("Xit Done Checkbox", DefaultLanguageHighlighterColors.STRING)
        val DONE_DESCRIPTION = createTextAttributesKey(
            "XIT_DONE_DESCRIPTION",
            PASSIVE_TEXT
        )

        val ONGOING_CHECKBOX = createTextAttributesKey(
            "XIT_ONGOING_CHECKBOX",
            DefaultLanguageHighlighterColors.INSTANCE_FIELD
        )
        val ONGOING_DESCRIPTION = createTextAttributesKey(
            "XIT_ONGOING_DESCRIPTION",
            ACTIVE_TEXT
        )

        val QUESTION_CHECKBOX = createTextAttributesKey(
            "XIT_QUESTION_CHECKBOX",
            DefaultLanguageHighlighterColors.METADATA
        )
        val QUESTION_DESCRIPTION = createTextAttributesKey(
            "XIT_QUESTION_DESCRIPTION",
            ACTIVE_TEXT
        )

        val OBSOLETE_CHECKBOX = createTextAttributesKey("XIT_OBSOLETE_CHECKBOX", PASSIVE_TEXT)
        val OBSOLETE_DESCRIPTION = createTextAttributesKey("XIT_OBSOLETE_DESCRIPTION", PASSIVE_TEXT)
        val OBSOLETE_DESCRIPTION_STRIKE = createTextAttributesKey(
            "XIT_STRIKE_OBSOLETE_DESCRIPTION", CodeInsightColors.DEPRECATED_ATTRIBUTES
        )

        val HASHTAG = createTextAttributesKey("XIT_HASHTAG", DefaultLanguageHighlighterColors.NUMBER)
        val PRIORITY = createTextAttributesKey("XIT_PRIORITY")
        val DUE_DATE = createTextAttributesKey(
            "XIT_DUE_DATE",
            DefaultLanguageHighlighterColors.METADATA
        )

        val DONE_HASHTAG = createTextAttributesKey("XIT_DONE_HASHTAG", HASHTAG)
        val DONE_PRIORITY = createTextAttributesKey("XIT_DONE_PRIORITY", PASSIVE_TEXT)
        val DONE_DUE_DATE = createTextAttributesKey("XIT_DONE_DUE_DATE", PASSIVE_TEXT)

        val OBSOLETE_HASHTAG = createTextAttributesKey("XIT_OBSOLETE_HASHTAG", HASHTAG)
        val OBSOLETE_PRIORITY = createTextAttributesKey("XIT_OBSOLETE_PRIORITY", PASSIVE_TEXT)
        val OBSOLETE_DUE_DATE = createTextAttributesKey("XIT_OBSOLETE_DUE_DATE", PASSIVE_TEXT)


        private val BAD_KEYS = arrayOf(BAD_CHAR)
        private val TITLE_KEYS = arrayOf(TITLE_TEXT)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()

        private val OPEN_CHB_KEYS = arrayOf(OPEN_CHECKBOX)
        private val OPEN_DESC_KEYS = arrayOf(OPEN_DESCRIPTION)

        private val DONE_CHB_KEYS = arrayOf(DONE_CHECKBOX)
        private val DONE_DESC_KEYS = arrayOf(DONE_DESCRIPTION)

        private val ONGOING_CHB_KEYS = arrayOf(ONGOING_CHECKBOX) // yeah!
        private val ONGOING_DESC_KEYS = arrayOf(ONGOING_DESCRIPTION)

        private val OBSOLETE_CHB_KEYS = arrayOf(OBSOLETE_CHECKBOX)
        private val OBSOLETE_DESC_KEYS = arrayOf(OBSOLETE_DESCRIPTION, OBSOLETE_DESCRIPTION_STRIKE)

        private val QUESTION_CHB_KEYS = arrayOf(QUESTION_CHECKBOX)
        private val QUESTION_DESC_KEYS = arrayOf(QUESTION_DESCRIPTION)

        private val HASHTAG_KEYS = arrayOf(HASHTAG)
        private val PRIORITY_KEYS = arrayOf(PRIORITY)
        private val DUE_DATE_KEYS = arrayOf(DUE_DATE)

        private val PASSIVE_HASHTAG_KEYS = arrayOf(DONE_HASHTAG)
        private val PASSIVE_PRIORITY_KEYS = arrayOf(DONE_PRIORITY)
        private val PASSIVE_DUE_DATE_KEYS = arrayOf(DONE_DUE_DATE)

        private val OBSOLETE_HASHTAG_KEYS = arrayOf(OBSOLETE_HASHTAG, OBSOLETE_DESCRIPTION_STRIKE)
        private val OBSOLETE_PRIORITY_KEYS = arrayOf(OBSOLETE_PRIORITY, OBSOLETE_DESCRIPTION_STRIKE)
        private val OBSOLETE_DUE_DATE_KEYS = arrayOf(OBSOLETE_DUE_DATE, OBSOLETE_DESCRIPTION_STRIKE)
    }

    override fun getHighlightingLexer(): Lexer {
        return XitLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
        return when (tokenType) {
            TokenType.BAD_CHARACTER -> BAD_KEYS

            XitTypes.TITLE_WORD -> TITLE_KEYS

            XitTypes.OPEN_CHECKBOX -> OPEN_CHB_KEYS
            XitTypes.OCH_WORD -> OPEN_DESC_KEYS

            XitTypes.DONE_CHECKBOX -> DONE_CHB_KEYS
            XitTypes.CCH_WORD -> DONE_DESC_KEYS

            XitTypes.ONGOING_CHECKBOX -> ONGOING_CHB_KEYS
            XitTypes.GCH_WORD -> ONGOING_DESC_KEYS

            XitTypes.OBSOLETE_CHECKBOX -> OBSOLETE_CHB_KEYS
            XitTypes.OBS_WORD -> OBSOLETE_DESC_KEYS

            XitTypes.QUESTION_CHECKBOX -> QUESTION_CHB_KEYS
            XitTypes.QUESTION_WORD -> QUESTION_DESC_KEYS

            XitTypes.HASHTAG -> HASHTAG_KEYS
            XitTypes.PRIORITY -> PRIORITY_KEYS
            XitTypes.DUE_DATE -> DUE_DATE_KEYS

            XitTypes.PASSIVE_HASHTAG -> PASSIVE_HASHTAG_KEYS
            XitTypes.PASSIVE_PRIORITY -> PASSIVE_PRIORITY_KEYS
            XitTypes.PASSIVE_DUE_DATE -> PASSIVE_DUE_DATE_KEYS

            XitTypes.OBSOLETE_HASHTAG -> OBSOLETE_HASHTAG_KEYS
            XitTypes.OBSOLETE_PRIORITY -> OBSOLETE_PRIORITY_KEYS
            XitTypes.OBSOLETE_DUE_DATE -> OBSOLETE_DUE_DATE_KEYS

            else -> EMPTY_KEYS
        }
    }
}

