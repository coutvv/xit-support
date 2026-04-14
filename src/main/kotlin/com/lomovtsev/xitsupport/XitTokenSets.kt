package com.lomovtsev.xitsupport

import com.intellij.psi.tree.TokenSet
import com.lomovtsev.xitsupport.psi.XitTypes.CCH_WORD
import com.lomovtsev.xitsupport.psi.XitTypes.GCH_WORD
import com.lomovtsev.xitsupport.psi.XitTypes.OBS_WORD
import com.lomovtsev.xitsupport.psi.XitTypes.OCH_WORD
import com.lomovtsev.xitsupport.psi.XitTypes.QUESTION_WORD
import com.lomovtsev.xitsupport.psi.XitTypes.TITLE_WORD

object XitTokenSets {
//    val IDENTIFIERS = TokenSet.create(XitTypes.KEY)
//    val COMMENTS = TokenSet.create(XitTypes.COMMENT)
    val TASKS = TokenSet.create()
    val STRINGS = TokenSet.create(
        TITLE_WORD, OCH_WORD, CCH_WORD, GCH_WORD, OBS_WORD, QUESTION_WORD
    )
}