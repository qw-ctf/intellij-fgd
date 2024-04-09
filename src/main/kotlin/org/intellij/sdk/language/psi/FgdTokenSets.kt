package org.intellij.sdk.language.psi

import com.intellij.psi.tree.TokenSet

interface FgdTokenSets {
	companion object {
		val STRINGS: TokenSet = TokenSet.create(FgdTypes.STRING)

		val COMMENTS: TokenSet = TokenSet.create(FgdTypes.COMMENT)

		val PARENTHESIS: TokenSet = TokenSet.create(
			FgdTypes.LPAR,
			FgdTypes.RPAR,
			FgdTypes.LDCURL,
			FgdTypes.RDCURL,
			FgdTypes.LCURL,
			FgdTypes.RCURL,
			FgdTypes.LBRACKET,
			FgdTypes.RBRACKET,
		)

		val FIELD_TYPES: TokenSet = TokenSet.create(
			FgdTypes.STRING_TYPE,
			FgdTypes.INTEGER_TYPE,
			FgdTypes.FLOAT_TYPE,
			FgdTypes.FLAGS_TYPE,
			FgdTypes.COLOR_TYPE,
			FgdTypes.CHOICES_TYPE,
			FgdTypes.TARGET_SRC_TYPE,
			FgdTypes.TARGET_DST_TYPE,
		)
	}
}
