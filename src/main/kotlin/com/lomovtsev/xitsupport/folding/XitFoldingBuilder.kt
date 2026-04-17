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
            if (group.points == null) {
                continue
            }

            val startOffset = group.textRange.startOffset
            var endOffset = group.textRange.endOffset

            // Trim trailing newlines from the fold region so the fold ends at the last content line
            val text = group.text
            val trimmedLength = text.trimEnd('\n').length
            if (trimmedLength > 0) {
                endOffset = startOffset + trimmedLength
            }

            if (endOffset > startOffset) {
                descriptors.add(FoldingDescriptor(group.node, TextRange(startOffset, endOffset)))
            }
        }

        return descriptors.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String {
        val element = node.psi
        if (element is XitXitGroup) {
            val title = element.title
            if (title != null) {
                val titleText = title.text.trim()
                if (titleText.isNotEmpty()) {
                    return "$titleText ..."
                }
            }

            val points = element.points
            if (points != null) {
                val firstPoint = points.pointList.firstOrNull()
                if (firstPoint != null) {
                    val checkboxText = firstPoint.checkboxPart.text
                    val descriptionText = firstPoint.pointDescription.text.trim()
                    val summary = if (descriptionText.length > 40) {
                        descriptionText.take(40) + "..."
                    } else {
                        descriptionText
                    }
                    return "$checkboxText $summary ..."
                }
            }
        }
        return "..."
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean = false
}
