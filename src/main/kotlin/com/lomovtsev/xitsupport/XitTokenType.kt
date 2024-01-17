package com.lomovtsev.xitsupport

import com.intellij.psi.tree.IElementType

class XitTokenType(
    debugName: String
) : IElementType(debugName, XitLanguage.INSTANCE) {

    override fun toString(): String {
        return "XitTokenType." + super.toString()
    }
}