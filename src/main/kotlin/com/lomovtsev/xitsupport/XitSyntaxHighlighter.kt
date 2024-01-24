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
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.lomovtsev.xitsupport.psi.XitTypes
import com.vladsch.flexmark.util.html.ui.Color

class XitSyntaxHighlighter : SyntaxHighlighterBase() {
    companion object {
        val BAD_CHAR = createTextAttributesKey("XIT_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

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
    }


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

            XitTypes.OBS_WORD -> OBSOLETE_DESC_KEYS
            XitTypes.OBSOLETE_CHECKBOX -> OBSOLETE_CHB_KEYS

            XitTypes.QUESTION_CHECKBOX -> QUESTION_CHB_KEYS
            XitTypes.QUESTION_WORD -> QUESTION_DESC_KEYS

            else -> EMPTY_KEYS
        }
    }
}


/**
 * TODO: delete (custom attribute key)
 */
object ExperimentHighlightValue {

    private val TITLE_WORD = createTextAttributesKey("TITLE WORD", xitTitle())

    /**
     * used for checking my lex feature development
     */
    val SIGNAL_KEYS = arrayOf(ExperimentHighlightValue.TITLE_WORD)

    private fun xitTitle(): TextAttributes {
        val result = TextAttributes()
        result.effectType = EffectType.BOLD_LINE_UNDERSCORE
        result.foregroundColor = Color.WHITE
        result.backgroundColor = Color.CYAN

        return result
    }
}
