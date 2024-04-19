package org.intellij.sdk.language

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.util.parentOfType
import org.intellij.sdk.language.psi.FgdClassDefinition
import org.intellij.sdk.language.psi.FgdClassName
import org.intellij.sdk.language.psi.FgdParamReference

class FgdDocumentationProvider : AbstractDocumentationProvider() {
	override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
		return when (element) {
			is FgdClassName ->
				element.parentOfType<FgdClassDefinition>()?.doc?.text?.let { doc ->
					"Documentation for $doc"
				}
			is FgdParamReference ->
				FgdUtil.findProperties(element.project, element.literal.text).let { elems ->
					elems.first()
					null
				}

			else -> null
		}
	}
}
