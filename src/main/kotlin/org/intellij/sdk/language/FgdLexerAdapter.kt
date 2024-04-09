package org.intellij.sdk.language

import com.intellij.lexer.FlexAdapter

class FgdLexerAdapter : FlexAdapter(FgdLexer(null))
