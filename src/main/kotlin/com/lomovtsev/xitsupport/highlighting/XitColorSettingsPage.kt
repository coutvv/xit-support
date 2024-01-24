package com.lomovtsev.xitsupport.highlighting

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.lomovtsev.xitsupport.XitIcons
import javax.swing.Icon

class XitColorSettingsPage : ColorSettingsPage {
    private val attributesDescriptors = arrayOf(
        AttributesDescriptor("Bad value", XitSyntaxHighlighter.BAD_CHAR),

        AttributesDescriptor("Active text", XitSyntaxHighlighter.ACTIVE_TEXT),
        AttributesDescriptor("Passive text", XitSyntaxHighlighter.PASSIVE_TEXT),
        AttributesDescriptor("Title text", XitSyntaxHighlighter.TITLE_TEXT),

        AttributesDescriptor("Open checkbox", XitSyntaxHighlighter.OPEN_CHECKBOX),
        AttributesDescriptor("Open description", XitSyntaxHighlighter.OPEN_DESCRIPTION),

        AttributesDescriptor("Done checkbox", XitSyntaxHighlighter.DONE_CHECKBOX),
        AttributesDescriptor("Done description", XitSyntaxHighlighter.DONE_DESCRIPTION),

        AttributesDescriptor("Ongoing checkbox", XitSyntaxHighlighter.ONGOING_CHECKBOX),
        AttributesDescriptor("Ongoing description", XitSyntaxHighlighter.ONGOING_DESCRIPTION),

        AttributesDescriptor("Question checkbox", XitSyntaxHighlighter.QUESTION_CHECKBOX),
        AttributesDescriptor("Question description", XitSyntaxHighlighter.QUESTION_DESCRIPTION),

        AttributesDescriptor("Obsolete checkbox", XitSyntaxHighlighter.OBSOLETE_CHECKBOX),
        AttributesDescriptor("Obsolete description", XitSyntaxHighlighter.OBSOLETE_DESCRIPTION),
        AttributesDescriptor("Obsolete description strike", XitSyntaxHighlighter.OBSOLETE_DESCRIPTION_STRIKE),

        AttributesDescriptor("Hashtag", XitSyntaxHighlighter.HASHTAG),

    )
    override fun getAttributeDescriptors(): Array<AttributesDescriptor> {
        return attributesDescriptors
    }

    override fun getColorDescriptors(): Array<ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): String {
        return "Xit"
    }

    override fun getIcon(): Icon {
        return XitIcons.FILE
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return XitSyntaxHighlighter()
    }

    override fun getDemoText(): String {
        return """
            [ ] This is an open item
            [x] This is a checked item
            [@] This is an ongoing item
            [~] This is an obsolete item
            [?] This is an item in question
            
            Group of tasks with title
            [ ] task 1
            [x] done task
            [@] in progress задача 
            [ ] task with simple #hashtag


        """.trimIndent()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey>? {
        return null
    }
}