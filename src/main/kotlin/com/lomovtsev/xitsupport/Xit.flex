
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
questionCheckbox = "[?] "

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
%state QUESTION_CHECKBOX_DESCRIPTION
%state QUESTION_CHECKBOX_DESCRIPTION_END


%%
<YYINITIAL> {
    ^{openCheckbox}        { yybegin(OPEN_CHECKBOX_DESCRIPTION);     return XitTypes.OPEN_CHECKBOX; }
    ^{doneCheckbox}        { yybegin(CLOSE_CHECKBOX_DESCRIPTION);    return XitTypes.DONE_CHECKBOX; }
    ^{ongoingCheckbox}     { yybegin(ONGOING_CHECKBOX_DESCRIPTION);  return XitTypes.ONGOING_CHECKBOX; }
    ^{obsoleteCheckbox}    { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION); return XitTypes.OBSOLETE_CHECKBOX; }
    ^{questionCheckbox}    { yybegin(QUESTION_CHECKBOX_DESCRIPTION); return XitTypes.QUESTION_CHECKBOX; }

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
    ^{ongoingCheckbox}        { yybegin(ONGOING_CHECKBOX_DESCRIPTION); return XitTypes.ONGOING_CHECKBOX; }
    ^{obsoleteCheckbox}        { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION); return XitTypes.OBSOLETE_CHECKBOX; }
    ^{questionCheckbox}    { yybegin(QUESTION_CHECKBOX_DESCRIPTION); return XitTypes.QUESTION_CHECKBOX; }
}

<CLOSE_CHECKBOX_DESCRIPTION> {
    {newline}       { yybegin(CLOSE_CHECKBOX_DESCRIPTION_END); return XitTypes.NEWLINE; }
    {trueword}      { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.CCH_WORD; }
    {whitespace}    { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.CCH_WORD; }
}

<CLOSE_CHECKBOX_DESCRIPTION_END> {
    {newline}               { yybegin(YYINITIAL); return XitTypes.GROUP_END; } // group end?
    ^{descIndent}          { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.DESC_INDENT;}

    ^{openCheckbox}        { yybegin(OPEN_CHECKBOX_DESCRIPTION); return XitTypes.OPEN_CHECKBOX; }
    ^{doneCheckbox}        { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.DONE_CHECKBOX; }
    ^{ongoingCheckbox}        { yybegin(ONGOING_CHECKBOX_DESCRIPTION); return XitTypes.ONGOING_CHECKBOX; }
    ^{obsoleteCheckbox}        { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION); return XitTypes.OBSOLETE_CHECKBOX; }
    ^{questionCheckbox}    { yybegin(QUESTION_CHECKBOX_DESCRIPTION); return XitTypes.QUESTION_CHECKBOX; }
}

<ONGOING_CHECKBOX_DESCRIPTION> {
    {newline}       { yybegin(ONGOING_CHECKBOX_DESCRIPTION_END); return XitTypes.NEWLINE; }
    {trueword}      { yybegin(ONGOING_CHECKBOX_DESCRIPTION); return XitTypes.GCH_WORD; }
    {whitespace}    { yybegin(ONGOING_CHECKBOX_DESCRIPTION); return XitTypes.GCH_WORD; }
}

<ONGOING_CHECKBOX_DESCRIPTION_END> {
    {newline}               { yybegin(YYINITIAL); return XitTypes.GROUP_END; } // group end?
    ^{descIndent}          { yybegin(ONGOING_CHECKBOX_DESCRIPTION); return XitTypes.DESC_INDENT;}

    ^{openCheckbox}        { yybegin(OPEN_CHECKBOX_DESCRIPTION); return XitTypes.OPEN_CHECKBOX; }
    ^{doneCheckbox}        { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.DONE_CHECKBOX; }
    ^{ongoingCheckbox}        { yybegin(ONGOING_CHECKBOX_DESCRIPTION); return XitTypes.ONGOING_CHECKBOX; }
    ^{obsoleteCheckbox}        { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION); return XitTypes.OBSOLETE_CHECKBOX; }
    ^{questionCheckbox}    { yybegin(QUESTION_CHECKBOX_DESCRIPTION); return XitTypes.QUESTION_CHECKBOX; }
}

<OBSOLETE_CHECKBOX_DESCRIPTION> {
    {newline}       { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION_END); return XitTypes.NEWLINE; }
    {trueword}      { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION); return XitTypes.OBS_WORD; }
    {whitespace}    { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION); return XitTypes.OBS_WORD; }
}

<OBSOLETE_CHECKBOX_DESCRIPTION_END> {
    {newline}               { yybegin(YYINITIAL); return XitTypes.GROUP_END; } // group end?
    ^{descIndent}          { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION); return XitTypes.DESC_INDENT;}

    ^{openCheckbox}        { yybegin(OPEN_CHECKBOX_DESCRIPTION); return XitTypes.OPEN_CHECKBOX; }
    ^{doneCheckbox}        { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.DONE_CHECKBOX; }
    ^{ongoingCheckbox}        { yybegin(ONGOING_CHECKBOX_DESCRIPTION); return XitTypes.ONGOING_CHECKBOX; }
    ^{obsoleteCheckbox}        { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION); return XitTypes.OBSOLETE_CHECKBOX; }
    ^{questionCheckbox}    { yybegin(QUESTION_CHECKBOX_DESCRIPTION); return XitTypes.QUESTION_CHECKBOX; }
}

<QUESTION_CHECKBOX_DESCRIPTION> {
    {newline}       { yybegin(QUESTION_CHECKBOX_DESCRIPTION_END); return XitTypes.GROUP_END; } // group end?
    {trueword}      { yybegin(QUESTION_CHECKBOX_DESCRIPTION); return XitTypes.QUESTION_WORD; }
    {whitespace}    { yybegin(QUESTION_CHECKBOX_DESCRIPTION); return XitTypes.QUESTION_WORD; }

}

<QUESTION_CHECKBOX_DESCRIPTION_END> {
    {newline}                { yybegin(YYINITIAL); return XitTypes.GROUP_END; } // group end?
    ^{descIndent}          { yybegin(QUESTION_CHECKBOX_DESCRIPTION); return XitTypes.DESC_INDENT;}

    ^{openCheckbox}        { yybegin(OPEN_CHECKBOX_DESCRIPTION); return XitTypes.OPEN_CHECKBOX; }
    ^{doneCheckbox}        { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.DONE_CHECKBOX; }
    ^{ongoingCheckbox}        { yybegin(ONGOING_CHECKBOX_DESCRIPTION); return XitTypes.ONGOING_CHECKBOX; }
    ^{obsoleteCheckbox}        { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION); return XitTypes.OBSOLETE_CHECKBOX; }
    ^{questionCheckbox}    { yybegin(QUESTION_CHECKBOX_DESCRIPTION); return XitTypes.QUESTION_CHECKBOX; }
}

// if undefined token -- then bad character
[^]            { return TokenType.BAD_CHARACTER; }