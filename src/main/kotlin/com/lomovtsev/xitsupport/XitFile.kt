package com.lomovtsev.xitsupport

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class XitFile(
    viewProvider: FileViewProvider
) : PsiFileBase(viewProvider, XitLanguage.INSTANCE) {
    override fun getFileType(): FileType {
        return XitFileType.INSTANCE
    }

    override fun toString(): String {
        return "Xit File"
    }
}