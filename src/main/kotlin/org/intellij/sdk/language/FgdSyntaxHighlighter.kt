package org.intellij.sdk.language

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import org.intellij.sdk.language.psi.FgdTokenSets
import org.intellij.sdk.language.psi.FgdTypes

class FgdSyntaxHighlighter : SyntaxHighlighterBase() {
	override fun getHighlightingLexer(): Lexer = FgdLexerAdapter()

	override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> = when (tokenType) {
		FgdTypes.STRING -> STRING_KEYS
		FgdTypes.INTEGER, FgdTypes.FLOAT -> NUMBER_KEYS
		FgdTypes.FIELD_NAME -> FIELD_KEYS
		FgdTypes.DOC, FgdTypes.DESCRIPTION -> DOC_KEYS
		FgdTypes.COMMENT -> COMMENT_KEYS
		FgdTypes.AMP, FgdTypes.EQ, FgdTypes.COLON -> OP_KEYS
		in FgdTokenSets.PARENTHESIS.types -> PARENTHESIS_KEYS
		in FgdTokenSets.FIELD_TYPES.types, FgdTypes.CLASS_NAME -> KEY_KEYS
		FgdTypes.ATTRIBUTE -> BUILTIN_KEYS
		TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
		else -> EMPTY_KEYS
	}

	companion object {
		val BAD_CHARACTER = createTextAttributesKey("FGD_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
		val SEPARATOR = createTextAttributesKey("FGD_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
		val KEY = createTextAttributesKey("FGD_KEY", DefaultLanguageHighlighterColors.KEYWORD)
		val VALUE = createTextAttributesKey("FGD_VALUE", DefaultLanguageHighlighterColors.STRING)
		val COMMENT = createTextAttributesKey("FGD_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
		val NUMBER = createTextAttributesKey("FGD_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
		val STRING = createTextAttributesKey("FGD_STRING", DefaultLanguageHighlighterColors.STRING)
		val FIELD = createTextAttributesKey("FGD_FIELD", DefaultLanguageHighlighterColors.STATIC_FIELD)
		val CLASS_NAME = createTextAttributesKey("FGD_CLASS_NAME", DefaultLanguageHighlighterColors.CLASS_NAME)
		val DOC = createTextAttributesKey("FGD_DOC", DefaultLanguageHighlighterColors.DOC_COMMENT)
		val OP = createTextAttributesKey("FGD_OP", DefaultLanguageHighlighterColors.OPERATION_SIGN)
		val PARENTHESIS = createTextAttributesKey("FGD_PARENTHESIS", DefaultLanguageHighlighterColors.PARENTHESES)
		val BUILTINS = createTextAttributesKey("FGD_BUILTINS", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL)

		private val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)
		private val SEPARATOR_KEYS = arrayOf(SEPARATOR)
		private val KEY_KEYS = arrayOf(KEY)
		private val VALUE_KEYS = arrayOf(VALUE)
		private val COMMENT_KEYS = arrayOf(COMMENT)
		private val STRING_KEYS = arrayOf(STRING)
		private val NUMBER_KEYS = arrayOf(NUMBER)
		private val FIELD_KEYS = arrayOf(FIELD)
		private val CLASS_NAME_KEYS = arrayOf(CLASS_NAME)
		private val DOC_KEYS = arrayOf(DOC)
		private val OP_KEYS = arrayOf(OP)
		private val PARENTHESIS_KEYS = arrayOf(PARENTHESIS)
		private val BUILTIN_KEYS = arrayOf(BUILTINS)
		private val EMPTY_KEYS = emptyArray<TextAttributesKey>()
	}
}
