package com.lomovtsev.xitsupport.old

import com.intellij.openapi.editor.colors.EditorColorsScheme
import com.intellij.openapi.editor.highlighter.EditorHighlighter
import com.intellij.openapi.fileTypes.EditorHighlighterProvider
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class MyHighlightProvider: EditorHighlighterProvider {

    override fun getEditorHighlighter(
        p0: Project?,
        p1: FileType,
        p2: VirtualFile?,
        p3: EditorColorsScheme
    ): EditorHighlighter {

        TODO("Not yet implemented")
    }
}