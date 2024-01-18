
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

//CRLF=\R
//WHITE_SPACE=[\ \n\t\f]
//FIRST_VALUE_CHARACTER=[^ \n\f\\] | "\\"{CRLF} | "\\".
//VALUE_CHARACTER=[^\n\f\\] | "\\"{CRLF} | "\\".
//END_OF_LINE_COMMENT=("#"|"!")[^\r\n]*
//SEPARATOR=[:=]
//KEY_CHARACTER=[^:=\ \n\t\f\\] | "\\ "


newline              = \r|\n|\r\n
whitespace           = \s

//CRLF=\R
//TEXT=[^\n\f\\] | "\\"{CRLF} | "\\".
word=[a-zA-Z]+
space=" "

digit=[0-9]
number=digit.*

//pointer=("[ ] ")[^\r\n]*
//pointDescBegin=("] ")[^\r\n]*

//newPoint=       ("[ " {pointDescBegin})
//donePoint=      ("[x" {pointDescBegin})
//ongoingPoint=   ("[@" {pointDescBegin})
//obsoletePoint=  ("[~" {pointDescBegin})

openCheckbox =     "[ ] "
doneCheckbox =     "[x] "
ongoingCheckbox =  "[@] "
obsoleteCheckbox = "[~] "

//any_text = [^\n\f\\] | "\\".
trueword = [^\n\s]+

descIndent  = "    "



//%state WAITING_VALUE


%%
<YYINITIAL> {
    ^{openCheckbox}        { return XitTypes.OPEN_CHECKBOX; }
    ^{doneCheckbox}        { return XitTypes.DONE_CHECKBOX; }
    ^{ongoingCheckbox}     { return XitTypes.ONGOING_CHECKBOX; }
    ^{obsoleteCheckbox}    { return XitTypes.OBSOLETE_CHECKBOX; }
    ^{descIndent}          { return XitTypes.DESC_INDENT; }

    {newline}             { return XitTypes.NEWLINE; }
//    {any_text}+            { return XitTypes.TEXT; }
//    {word}                { return XitTypes.WORD; }
    {trueword}            { return XitTypes.WORD; }
    {whitespace}          { return XitTypes.SPACE; }
//    {donePoint}       { return XitTypes.DONE_TASK; }
//    {ongoingPoint}    { return XitTypes.ONGOING_TASK; }
//    {obsoletePoint}   { return XitTypes.OBSOLETE_TASK; }
//    {newPoint}        { return XitTypes.NEW_TASK; }
}

//<YYINITIAL> {WORD}          { return XitTypes.TEXT; }
//

//<YYINITIAL> {END_OF_LINE_COMMENT}                           { yybegin(YYINITIAL); return XitTypes.COMMENT; }

//<YYINITIAL> {KEY_CHARACTER}+                                { yybegin(YYINITIAL); return XitTypes.KEY; }
//
//<YYINITIAL> {SEPARATOR}                                     { yybegin(WAITING_VALUE); return XitTypes.SEPARATOR; }
//
//<WAITING_VALUE> {CRLF}({CRLF}|{WHITE_SPACE})+               { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
//
//<WAITING_VALUE> {WHITE_SPACE}+                              { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }
//
//<WAITING_VALUE> {FIRST_VALUE_CHARACTER}{VALUE_CHARACTER}*   { yybegin(YYINITIAL); return XitTypes.VALUE; }
//
//({CRLF}|{WHITE_SPACE})+                                     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
//
[^]                                                         { return TokenType.BAD_CHARACTER; }