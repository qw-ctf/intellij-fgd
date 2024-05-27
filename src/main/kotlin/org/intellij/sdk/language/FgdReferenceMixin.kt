package org.intellij.sdk.language

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.util.PsiTreeUtil
import org.intellij.sdk.language.psi.FgdFile
import org.intellij.sdk.language.psi.FgdTypes

abstract class FgdReferenceMixin(node: ASTNode) : ASTWrapperPsiElement(node), PsiNamedElement {
	override fun getName(): String? {
		return getNameIdentifier()?.text
	}

	override fun setName(name: String): PsiElement {
		val file = PsiFileFactory.getInstance(project)
			.createFileFromText("dummy." + FgdFileType.defaultExtension, FgdLanguage.baseLanguage!!, "dummy $name") as FgdFile
		val newNameElement = PsiTreeUtil.findChildOfType(file, PsiNamedElement::class.java)
		val nameIdentifier = getNameIdentifier()

		if (newNameElement != null && nameIdentifier != null) {
			node.replaceChild(nameIdentifier.node, newNameElement.node)
		}
		return this
	}

	private fun getNameIdentifier(): PsiElement? {
		return findChildByType(FgdTypes.LITERAL)
	}
}
