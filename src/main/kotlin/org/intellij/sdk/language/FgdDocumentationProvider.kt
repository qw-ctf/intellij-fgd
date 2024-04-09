package org.intellij.sdk.language

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.util.parentOfType
import org.intellij.sdk.language.psi.FgdClassDefinition
import org.intellij.sdk.language.psi.FgdClassName

class FgdDocumentationProvider : AbstractDocumentationProvider() {
	override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
		if (element is FgdClassName) {
			return element.parentOfType<FgdClassDefinition>()?.doc?.text?.let { doc ->
				"Documentation for $doc"
			}
		}
		return null
	}
}
