package com.lomovtsev.xitsupport.spellchecker

import com.intellij.psi.PsiElement
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy
import com.intellij.spellchecker.tokenizer.Tokenizer
import com.lomovtsev.xitsupport.XitTokenSets

class XitSpellcheckingStrategy : SpellcheckingStrategy() {

    override fun getTokenizer(element: PsiElement?): Tokenizer<*> {
        if (element != null && XitTokenSets.STRINGS.contains(element.node.elementType)) {
            return TEXT_TOKENIZER
        }
        return super.getTokenizer(element)
    }
}
