
package com.lomovtsev.xitsupport;

import com.intellij.lexer.FlexLexer;

import com.intellij.psi.tree.IElementType;
import com.lomovtsev.xitsupport.psi.XitTypes;
import com.intellij.psi.TokenType;

%%

%class XitLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}


newline              = \r|\n|\r\n
whitespace           = \s

word=[a-zA-Z]+
space=" "

digit=[0-9]
number=digit.*

openCheckbox =     "[ ] "
doneCheckbox =     "[x] "
ongoingCheckbox =  "[@] "
obsoleteCheckbox = "[~] "

trueword = [^\n\s]+

descIndent  = "    "
emptyLine = []

%state TITLE

%state OPEN_CHECKBOX_DESCRIPTION
%state OPEN_CHECKBOX_DESCRIPTION_END
%state CLOSE_CHECKBOX_DESCRIPTION
%state CLOSE_CHECKBOX_DESCRIPTION_END
%state ONGOING_CHECKBOX_DESCRIPTION
%state ONGOING_CHECKBOX_DESCRIPTION_END
%state OBSOLETE_CHECKBOX_DESCRIPTION
%state OBSOLETE_CHECKBOX_DESCRIPTION_END


%%
<YYINITIAL> {
    ^{openCheckbox}        { yybegin(OPEN_CHECKBOX_DESCRIPTION); return XitTypes.OPEN_CHECKBOX; }
    ^{doneCheckbox}        { return XitTypes.DONE_CHECKBOX; }
    ^{ongoingCheckbox}     { return XitTypes.ONGOING_CHECKBOX; }
    ^{obsoleteCheckbox}    { return XitTypes.OBSOLETE_CHECKBOX; }

    {newline}             { return XitTypes.NEWLINE; }
    {trueword}            { yybegin(TITLE); return XitTypes.TITLE_WORD; } // TITLE!
    {whitespace}          { return XitTypes.SPACE; }
}

<TITLE> {
    {newline}                       { yybegin(YYINITIAL); return XitTypes.NEWLINE; }
    {trueword} | {whitespace}       { yybegin(TITLE);     return XitTypes.TITLE_WORD; } // TITLE!
}

<OPEN_CHECKBOX_DESCRIPTION> {
    {newline}       { yybegin(OPEN_CHECKBOX_DESCRIPTION_END); return XitTypes.NEWLINE; }
    {trueword}      { yybegin(OPEN_CHECKBOX_DESCRIPTION); return XitTypes.OCH_WORD; }
    {whitespace}    { yybegin(OPEN_CHECKBOX_DESCRIPTION); return XitTypes.OCH_WORD; }
}

<OPEN_CHECKBOX_DESCRIPTION_END> {
    {newline}               { yybegin(YYINITIAL); return XitTypes.GROUP_END; } // group end?
    ^{descIndent}          { yybegin(OPEN_CHECKBOX_DESCRIPTION); return XitTypes.DESC_INDENT;}
    ^{openCheckbox}        { yybegin(OPEN_CHECKBOX_DESCRIPTION); return XitTypes.OPEN_CHECKBOX; }
    ^{doneCheckbox}        { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.DONE_CHECKBOX; }
}

<CLOSE_CHECKBOX_DESCRIPTION> {
    {newline}       { yybegin(CLOSE_CHECKBOX_DESCRIPTION_END); return XitTypes.NEWLINE; }
    {trueword}      { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.CCH_WORD; }
    {whitespace}    { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.CCH_WORD; }
}

<CLOSE_CHECKBOX_DESCRIPTION_END> {
    {newline}               { yybegin(YYINITIAL); return XitTypes.GROUP_END; } // group end?
    ^{descIndent}          { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.DESC_INDENT;}
    ^{openCheckbox}        { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.OPEN_CHECKBOX; }
    ^{doneCheckbox}        { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.DONE_CHECKBOX; }
}


// if undefined token -- then bad character
[^]            { return TokenType.BAD_CHARACTER; }