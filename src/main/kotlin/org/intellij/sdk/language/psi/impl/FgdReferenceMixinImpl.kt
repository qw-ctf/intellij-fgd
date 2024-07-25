package org.intellij.sdk.language.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceBase
import org.intellij.sdk.language.FgdUtil
import org.intellij.sdk.language.psi.FgdTypes

abstract class FgdReferenceMixinImpl(node: ASTNode) : ASTWrapperPsiElement(node), PsiReference {
	override fun getName(): String? {
		return getNameIdentifier()?.text
	}

	override fun resolve(): PsiElement? {
		return this
	}

	override fun getReference(): PsiReference? {
		return getNameIdentifier()?.text?.let { name -> FgdUtil.findClassName(project, name) }?.firstOrNull()?.let {
			PsiReferenceBase.Immediate<FgdReferenceMixinImpl>(this, it)
		}
	}

	private fun getNameIdentifier(): PsiElement? {
		return findChildByType(FgdTypes.LITERAL)
	}
}
