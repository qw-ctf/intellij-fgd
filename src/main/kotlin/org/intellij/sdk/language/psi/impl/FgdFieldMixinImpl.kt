package org.intellij.sdk.language.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.util.PsiTreeUtil
import org.intellij.sdk.language.psi.FgdDescription
import org.intellij.sdk.language.psi.FgdDoc
import org.intellij.sdk.language.psi.FgdFieldMixin
import org.intellij.sdk.language.psi.FgdFieldName

abstract class FgdFieldMixinImpl(node: ASTNode) : ASTWrapperPsiElement(node), FgdFieldMixin {
	override fun getDoc(): String? =
		PsiTreeUtil.findChildOfType(this, FgdDoc::class.java)?.text?.let(::trimQuotes)

	override fun getFieldName(): String =
		PsiTreeUtil.findChildOfType(this, FgdFieldName::class.java)?.text?.let(::trimQuotes) ?: ""

	override fun getDescription(): String =
		PsiTreeUtil.findChildOfType(this, FgdDescription::class.java)?.text?.let(::trimQuotes) ?: ""

	companion object {
		private fun trimQuotes(text: String): String =
			text.trimStart('"').trimEnd('"')
	}
}
