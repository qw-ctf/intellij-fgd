package org.intellij.sdk.language

import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar
import org.intellij.sdk.language.psi.FgdParamReferences

class FgdReferenceContributor : PsiReferenceContributor() {
	private val onBaseClass = psiElement(FgdParamReferences::class.java)

	override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
		/*
		registrar.registerReferenceProvider(onBaseClass, FgdBaseClassReferenceProvider())
		registrar.registerReferenceProvider(psiElement(FgdClassName::class.java), FgdBaseClassReferenceProvider())
		 */
	}
}
