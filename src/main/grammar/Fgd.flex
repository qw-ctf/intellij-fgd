package org.intellij.sdk.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static org.intellij.sdk.language.psi.FgdTypes.*;

%%

%class FgdLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%caseless


EOL="\r"|"\n"|"\r\n"
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+

STRING="\"" [^\"]* "\""
INTEGER=[-]?[0-9]+
FLOAT=[-]?[0-9]+(\.[0-9]+)?

LITERAL=[_a-zA-Z][0-9_a-zA-Z]*
META_CLASS=[a-zA-Z]+

COMMENT="//"[^\r\n]*

%%
<YYINITIAL> {
  {WHITE_SPACE}         { return com.intellij.psi.TokenType.WHITE_SPACE; }

  {COMMENT}             { return COMMENT; }

  "string"              { return STRING_TYPE; }
  "integer"             { return INTEGER_TYPE; }
  "float"               { return FLOAT_TYPE; }
  "string"              { return STRING_TYPE; }
  "choices"             { return CHOICES_TYPE; }
  "color255"            { return COLOR_TYPE; }
  "target_source"       { return TARGET_SRC_TYPE; }
  "target_destination"  { return TARGET_DST_TYPE; }
  "flags"               { return FLAGS_TYPE; }

  {STRING}              { return STRING; }

  {INTEGER}             { return INTEGER; }
  {FLOAT}               { return FLOAT; }
  {LITERAL}             { return LITERAL; }

  "@"                   { return AT; }
  ","                   { return COMMA; }
  ":"                   { return COLON; }
  "="                   { return EQ; }
  "&"                   { return AMP; }
  "->"                  { return ARROW; }
  "["                   { return LBRACKET; }
  "]"                   { return RBRACKET; }
  "("                   { return LPAR; }
  ")"                   { return RPAR; }
  "{{"                  { return LDCURL; }
  "}}"                  { return RDCURL; }
  "{"                   { return LCURL; }
  "}"                   { return RCURL; }

  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}