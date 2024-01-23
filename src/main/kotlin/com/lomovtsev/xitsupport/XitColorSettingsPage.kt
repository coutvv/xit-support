package com.lomovtsev.xitsupport

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class XitColorSettingsPage : ColorSettingsPage {
    val DESCRIPTORS = arrayOf(
//        AttributesDescriptor("Task", XitSyntaxHighlighter.TASK),
        AttributesDescriptor("Title", XitSyntaxHighlighter.TITLE),
        AttributesDescriptor("Bad value", XitSyntaxHighlighter.BAD_CHAR),
        AttributesDescriptor("Separator", XitSyntaxHighlighter.SEPARATOR), // TODO: no need it
        AttributesDescriptor("Open checkbox", XitSyntaxHighlighter.OPEN_CHECKBOX), // TODO: no need it
    )
    override fun getAttributeDescriptors(): Array<AttributesDescriptor> {
        return DESCRIPTORS
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
            This is the xit demo sample file
            [ ] Simple checkbox
                continue of task description
            [ ] other task

        """.trimIndent()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey>? {
        return null
    }
}