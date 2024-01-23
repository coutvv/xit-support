package com.lomovtsev.xitsupport

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.lomovtsev.xitsupport.psi.XitTypes

class XitParserDefinition: ParserDefinition {

    private val FILE = IFileElementType(XitLanguage.INSTANCE)
    override fun createLexer(project: Project?): Lexer {
        return XitLexerAdapter()
    }

    @Suppress("UNREACHABLE_CODE")
    override fun createParser(project: Project?): PsiParser {
        return XitParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun getCommentTokens(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun getStringLiteralElements(): TokenSet {
        return XitTokenSets.STRINGS
    }

    override fun createElement(node: ASTNode?): PsiElement {
        return XitTypes.Factory.createElement(node)
    }

    override fun createFile(fileViewProvider: FileViewProvider): PsiFile {
        return XitFile(fileViewProvider)
    }
}