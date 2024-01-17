package com.lomovtsev.xitsupport

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

class XitFileType : LanguageFileType(XitLanguage.INSTANCE) {
    companion object {
        val INSTANCE = XitFileType()
    }
    override fun getName(): String {
        return "Xit File"
    }

    override fun getDescription(): String {
        return "Xit language file"
    }

    override fun getDefaultExtension(): String {
        return "xit"
    }

    override fun getIcon(): Icon {
        return XitIcons.FILE
    }
}
