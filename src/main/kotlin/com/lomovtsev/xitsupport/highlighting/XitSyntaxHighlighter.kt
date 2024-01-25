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
        val BAD_CHAR = createTextAttributesKey("Xit Bad Character", HighlighterColors.BAD_CHARACTER)

        val ACTIVE_TEXT = createTextAttributesKey("Xit Active Text", DefaultLanguageHighlighterColors.CLASS_NAME)
        val PASSIVE_TEXT = createTextAttributesKey("Xit Passive Text", DefaultLanguageHighlighterColors.LINE_COMMENT)

        val TITLE_TEXT = createTextAttributesKey("Xit Title Text", CodeInsightColors.HYPERLINK_ATTRIBUTES)

        val OPEN_CHECKBOX = createTextAttributesKey(
            "Xit Open Checkbox",
            DefaultLanguageHighlighterColors.NUMBER
        )
        val OPEN_DESCRIPTION = createTextAttributesKey(
            "Xit Open Description",
            ACTIVE_TEXT
        )

        val DONE_CHECKBOX = createTextAttributesKey("Xit Done Checkbox", DefaultLanguageHighlighterColors.STRING)
        val DONE_DESCRIPTION = createTextAttributesKey(
            "Xit Done Description",
            PASSIVE_TEXT
        )

        val ONGOING_CHECKBOX = createTextAttributesKey(
            "Xit Ongoing Checkbox",
            DefaultLanguageHighlighterColors.INSTANCE_FIELD
        )
        val ONGOING_DESCRIPTION = createTextAttributesKey(
            "Xit Ongoing Description",
            ACTIVE_TEXT
        )

        val QUESTION_CHECKBOX = createTextAttributesKey(
            "Xit Question Checkbox",
            DefaultLanguageHighlighterColors.METADATA
        )
        val QUESTION_DESCRIPTION = createTextAttributesKey(
            "Xit Question Description",
            ACTIVE_TEXT
        )

        val OBSOLETE_CHECKBOX = createTextAttributesKey("Xit Obsolete Checkbox", PASSIVE_TEXT)
        val OBSOLETE_DESCRIPTION = createTextAttributesKey("Xit Obsolete Description", PASSIVE_TEXT)
        val OBSOLETE_DESCRIPTION_STRIKE = createTextAttributesKey(
            "Xit Strike Obsolete Description", CodeInsightColors.DEPRECATED_ATTRIBUTES
        )

        val HASHTAG = createTextAttributesKey("Xit Hashtag", DefaultLanguageHighlighterColors.NUMBER)
        val PRIORITY = createTextAttributesKey("Xit Priority")
        val DUE_DATE = createTextAttributesKey(
            "Xit Due Date",
            DefaultLanguageHighlighterColors.METADATA
        )

        val DONE_HASHTAG = createTextAttributesKey("Xit Done Hashtag", HASHTAG)
        val DONE_PRIORITY = createTextAttributesKey("Xit Done Priority", PASSIVE_TEXT)
        val DONE_DUE_DATE = createTextAttributesKey("Xit Done Due Date", PASSIVE_TEXT)

        val OBSOLETE_HASHTAG = createTextAttributesKey("Xit Obsolete Hashtag", HASHTAG)
        val OBSOLETE_PRIORITY = createTextAttributesKey("Xit Obsolete Priority", PASSIVE_TEXT)
        val OBSOLETE_DUE_DATE = createTextAttributesKey("Xit Obsolete Due Date", PASSIVE_TEXT)

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

