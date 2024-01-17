package com.lomovtsev.xitsupport

import com.intellij.lexer.FlexAdapter

class XitLexerAdapter : FlexAdapter(XitLexer(null)) {
}