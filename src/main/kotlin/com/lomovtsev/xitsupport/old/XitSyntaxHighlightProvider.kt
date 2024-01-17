package com.lomovtsev.xitsupport.old

import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class XitSyntaxHighlightProvider: SyntaxHighlighterProvider {
    override fun create(p0: FileType, p1: Project?, p2: VirtualFile?): SyntaxHighlighter? {
        return XitSyntaxHighlighter()
        TODO("Not yet implemented")
    }
}