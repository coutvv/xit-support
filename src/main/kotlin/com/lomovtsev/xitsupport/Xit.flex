
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

//word=[a-zA-Z]+
//space=" "

//digit=[0-9]
//number=digit.*

openCheckbox =     "[ ] "
doneCheckbox =     "[x] "
ongoingCheckbox =  "[@] "
obsoleteCheckbox = "[~] "
questionCheckbox = "[?] "

trueword = [^\n\s\#]+
titleword = [^\n\s]+
hashtag = ("#" {trueword})

descIndent  = "    "

priority = [!.]+

dueDate = ("-> " {trueword})

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
    ^{openCheckbox}        {
        yybegin(OPEN_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.OPEN_CHECKBOX;
      }
    ^{doneCheckbox}        {
        yybegin(CLOSE_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.DONE_CHECKBOX;
      }
    ^{ongoingCheckbox}     {
        yybegin(ONGOING_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.ONGOING_CHECKBOX;
      }
    ^{obsoleteCheckbox}    {
        yybegin(OBSOLETE_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.OBSOLETE_CHECKBOX;
      }
    ^{questionCheckbox}    {
        yybegin(QUESTION_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.QUESTION_CHECKBOX;
      }

    {newline}             { return XitTypes.NEWLINE; }
    {titleword}            { yybegin(TITLE); return XitTypes.TITLE_WORD; }
    {whitespace}          { return XitTypes.SPACE; }
}

<TITLE> {
    {newline}                       { yybegin(YYINITIAL); return XitTypes.NEWLINE; }
    {titleword} | {whitespace}       { yybegin(TITLE);     return XitTypes.TITLE_WORD; }
}


<OPEN_CHECKBOX_DESCRIPTION> {
    {newline}       { yybegin(OPEN_CHECKBOX_DESCRIPTION_END); return XitTypes.NEWLINE; }
    {hashtag}       { yybegin(OPEN_CHECKBOX_DESCRIPTION); return XitTypes.HASHTAG;}
    {priority}      {
                    yybegin(OPEN_CHECKBOX_DESCRIPTION);
                    if (LexerTool.INSTANCE.isPriorityToken(zzCurrentPos, this.yytext())) { return XitTypes.PRIORITY; }
                    return XitTypes.OCH_WORD;
                  }
    {dueDate}       { yybegin(OPEN_CHECKBOX_DESCRIPTION); return XitTypes.DUE_DATE; }
    {trueword}      { yybegin(OPEN_CHECKBOX_DESCRIPTION); return XitTypes.OCH_WORD; }
    {whitespace}    { yybegin(OPEN_CHECKBOX_DESCRIPTION); return XitTypes.OCH_WORD; }
}


<OPEN_CHECKBOX_DESCRIPTION_END> {
    {newline}              { yybegin(YYINITIAL); return XitTypes.GROUP_END; } // group end?
    ^{descIndent}          { yybegin(OPEN_CHECKBOX_DESCRIPTION); return XitTypes.DESC_INDENT;}

    ^{openCheckbox}        {
        yybegin(OPEN_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.OPEN_CHECKBOX;
      }
    ^{doneCheckbox}        {
        yybegin(CLOSE_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.DONE_CHECKBOX;
      }
    ^{ongoingCheckbox}     {
        yybegin(ONGOING_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.ONGOING_CHECKBOX;
      }
    ^{obsoleteCheckbox}    {
        yybegin(OBSOLETE_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.OBSOLETE_CHECKBOX;
      }
    ^{questionCheckbox}    {
        yybegin(QUESTION_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.QUESTION_CHECKBOX;
      }
}


<CLOSE_CHECKBOX_DESCRIPTION> {
    {newline}             { yybegin(CLOSE_CHECKBOX_DESCRIPTION_END); return XitTypes.NEWLINE; }
    {hashtag}             { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.HASHTAG;}
    {priority}      { // TODO: mb no priority for ready task?
        yybegin(CLOSE_CHECKBOX_DESCRIPTION);
        if (LexerTool.INSTANCE.isPriorityToken(zzCurrentPos, yytext())) { return XitTypes.PRIORITY; }
        return XitTypes.CCH_WORD;
      }
    {dueDate}             { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.DUE_DATE; }
    {trueword}            { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.CCH_WORD; }
    {whitespace}          { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.CCH_WORD; }
}

<CLOSE_CHECKBOX_DESCRIPTION_END> {
    {newline}               { yybegin(YYINITIAL); return XitTypes.GROUP_END; } // group end?
    ^{descIndent}          { yybegin(CLOSE_CHECKBOX_DESCRIPTION); return XitTypes.DESC_INDENT;}

    ^{openCheckbox}        {
        yybegin(OPEN_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.OPEN_CHECKBOX;
      }
    ^{doneCheckbox}        {
        yybegin(CLOSE_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.DONE_CHECKBOX;
      }
    ^{ongoingCheckbox}     {
        yybegin(ONGOING_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.ONGOING_CHECKBOX;
      }
    ^{obsoleteCheckbox}    {
        yybegin(OBSOLETE_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.OBSOLETE_CHECKBOX;
      }
    ^{questionCheckbox}    {
        yybegin(QUESTION_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.QUESTION_CHECKBOX;
      }

}


<ONGOING_CHECKBOX_DESCRIPTION> {
    {newline}       { yybegin(ONGOING_CHECKBOX_DESCRIPTION_END); return XitTypes.NEWLINE; }
    {hashtag}       { yybegin(ONGOING_CHECKBOX_DESCRIPTION); return XitTypes.HASHTAG;}
    {priority}      {
        yybegin(ONGOING_CHECKBOX_DESCRIPTION);
        if (LexerTool.INSTANCE.isPriorityToken(zzCurrentPos, yytext())) { return XitTypes.PRIORITY; }
        return XitTypes.GCH_WORD;
      }

    {dueDate}       { yybegin(ONGOING_CHECKBOX_DESCRIPTION); return XitTypes.DUE_DATE; }
    {trueword}      { yybegin(ONGOING_CHECKBOX_DESCRIPTION); return XitTypes.GCH_WORD; }
    {whitespace}    { yybegin(ONGOING_CHECKBOX_DESCRIPTION); return XitTypes.GCH_WORD; }

}

<ONGOING_CHECKBOX_DESCRIPTION_END> {
    {newline}               { yybegin(YYINITIAL); return XitTypes.GROUP_END; } // group end?
    ^{descIndent}          { yybegin(ONGOING_CHECKBOX_DESCRIPTION); return XitTypes.DESC_INDENT;}

    ^{openCheckbox}        {
        yybegin(OPEN_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.OPEN_CHECKBOX;
      }
    ^{doneCheckbox}        {
        yybegin(CLOSE_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.DONE_CHECKBOX;
      }
    ^{ongoingCheckbox}     {
        yybegin(ONGOING_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.ONGOING_CHECKBOX;
      }
    ^{obsoleteCheckbox}    {
        yybegin(OBSOLETE_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.OBSOLETE_CHECKBOX;
      }
    ^{questionCheckbox}    {
        yybegin(QUESTION_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.QUESTION_CHECKBOX;
      }

}


<OBSOLETE_CHECKBOX_DESCRIPTION> {
    {newline}       { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION_END); return XitTypes.NEWLINE; }
    {priority}      { // TODO: mb no priority for obsolete task?
        yybegin(OBSOLETE_CHECKBOX_DESCRIPTION);
        if (LexerTool.INSTANCE.isPriorityToken(zzCurrentPos, yytext())) { return XitTypes.PRIORITY; }
        return XitTypes.OBS_WORD;
      }
    {hashtag}       { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION); return XitTypes.HASHTAG;}
    {dueDate}       { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION); return XitTypes.DUE_DATE; }
    {trueword}      { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION); return XitTypes.OBS_WORD; }
    {whitespace}    { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION); return XitTypes.OBS_WORD; }
}

<OBSOLETE_CHECKBOX_DESCRIPTION_END> {
    {newline}               { yybegin(YYINITIAL); return XitTypes.GROUP_END; } // group end?
    ^{descIndent}          { yybegin(OBSOLETE_CHECKBOX_DESCRIPTION); return XitTypes.DESC_INDENT;}

    ^{openCheckbox}        {
        yybegin(OPEN_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.OPEN_CHECKBOX;
      }
    ^{doneCheckbox}        {
        yybegin(CLOSE_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.DONE_CHECKBOX;
      }
    ^{ongoingCheckbox}     {
        yybegin(ONGOING_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.ONGOING_CHECKBOX;
      }
    ^{obsoleteCheckbox}    {
        yybegin(OBSOLETE_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.OBSOLETE_CHECKBOX;
      }
    ^{questionCheckbox}    {
        yybegin(QUESTION_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.QUESTION_CHECKBOX;
      }

}


<QUESTION_CHECKBOX_DESCRIPTION> {
    {newline}       { yybegin(QUESTION_CHECKBOX_DESCRIPTION_END); return XitTypes.NEWLINE; } // group end?
    {priority}      {
        yybegin(QUESTION_CHECKBOX_DESCRIPTION);
        if (LexerTool.INSTANCE.isPriorityToken(zzCurrentPos, yytext())) { return XitTypes.PRIORITY; }
        return XitTypes.QUESTION_WORD;
      }
    {hashtag}       { yybegin(QUESTION_CHECKBOX_DESCRIPTION); return XitTypes.HASHTAG;}
    {dueDate}       { yybegin(QUESTION_CHECKBOX_DESCRIPTION); return XitTypes.DUE_DATE; }
    {trueword}      { yybegin(QUESTION_CHECKBOX_DESCRIPTION); return XitTypes.QUESTION_WORD; }
    {whitespace}    { yybegin(QUESTION_CHECKBOX_DESCRIPTION); return XitTypes.QUESTION_WORD; }

}

<QUESTION_CHECKBOX_DESCRIPTION_END> {
    {newline}                { yybegin(YYINITIAL); return XitTypes.GROUP_END; } // group end?
    ^{descIndent}          { yybegin(QUESTION_CHECKBOX_DESCRIPTION); return XitTypes.DESC_INDENT;}

    ^{openCheckbox}        {
        yybegin(OPEN_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.OPEN_CHECKBOX;
      }
    ^{doneCheckbox}        {
        yybegin(CLOSE_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.DONE_CHECKBOX;
      }
    ^{ongoingCheckbox}     {
        yybegin(ONGOING_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.ONGOING_CHECKBOX;
      }
    ^{obsoleteCheckbox}    {
        yybegin(OBSOLETE_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.OBSOLETE_CHECKBOX;
      }
    ^{questionCheckbox}    {
        yybegin(QUESTION_CHECKBOX_DESCRIPTION);
        LexerTool.INSTANCE.setLastCheckboxPlace(zzCurrentPos);
        return XitTypes.QUESTION_CHECKBOX;
      }

}

// if undefined token -- then bad character
[^]            { return TokenType.BAD_CHARACTER; }