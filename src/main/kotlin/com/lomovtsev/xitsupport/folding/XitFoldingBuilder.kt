package com.lomovtsev.xitsupport.folding

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.lomovtsev.xitsupport.psi.XitXitGroup

class XitFoldingBuilder : FoldingBuilderEx() {

    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        val descriptors = mutableListOf<FoldingDescriptor>()
        val groups = PsiTreeUtil.findChildrenOfType(root, XitXitGroup::class.java)

        for (group in groups) {
            val points = group.points ?: continue
            val title = group.title ?: continue

            // Start fold at the title's trailing newline so the gutter arrow
            // appears on the title line (like markdown section headers).
            // Title grammar: (TITLE_WORD | SPACE)+ NEWLINE — last char is '\n'.
            val startOffset = title.textRange.endOffset - 1
            var endOffset = points.textRange.endOffset

            // Trim trailing newlines from the fold region
            val pointsText = points.text
            val trimmedLength = pointsText.trimEnd('\n').length
            if (trimmedLength > 0) {
                endOffset = points.textRange.startOffset + trimmedLength
            }

            if (endOffset > startOffset) {
                descriptors.add(FoldingDescriptor(group.node, TextRange(startOffset, endOffset)))
            }
        }

        return descriptors.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String = "..."

    override fun isCollapsedByDefault(node: ASTNode): Boolean = false
}
