
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

LINE_SEPARATOR=[\n]
CRLF=\R
//TEXT=[^\n\f\\] | "\\"{CRLF} | "\\".
WORD=[a-zA-Z]*

digit=[0-9]
number=digit.*

//pointer=("[ ] ")[^\r\n]*
pointDescBegin=("] ")[^\r\n]*

newPoint=       ("[ " {pointDescBegin})
donePoint=      ("[x" {pointDescBegin})
ongoingPoint=   ("[@" {pointDescBegin})
obsoletePoint=  ("[~" {pointDescBegin})

%state WAITING_VALUE


%%
<YYINITIAL> {
    {donePoint}       { return XitTypes.DONE_TASK; }
    {ongoingPoint}    { return XitTypes.ONGOING_TASK; }
    {obsoletePoint}   { return XitTypes.OBSOLETE_TASK; }
    {newPoint}        { return XitTypes.NEW_TASK; }
    {newline}         { return XitTypes.NEWLINE; }
}

<YYINITIAL> {WORD}          { return XitTypes.TEXT; }
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