package com.lomovtsev.xitsupport

import com.intellij.psi.tree.TokenSet
import com.lomovtsev.xitsupport.psi.XitTypes.OCH_WORD
import com.lomovtsev.xitsupport.psi.XitTypes.TITLE_WORD

object XitTokenSets {
//    val IDENTIFIERS = TokenSet.create(XitTypes.KEY)
//    val COMMENTS = TokenSet.create(XitTypes.COMMENT)
    val TASKS = TokenSet.create()
    val STRINGS = TokenSet.create(
        TITLE_WORD, OCH_WORD
    )
}