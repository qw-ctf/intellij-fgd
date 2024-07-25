package org.intellij.sdk.language

import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar
import org.intellij.sdk.language.psi.FgdClassName


class FgdReferenceContributor : PsiReferenceContributor() {
	override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
		val pattern = PlatformPatterns.psiElement(FgdClassName::class.java)
		registrar.registerReferenceProvider(pattern, FgdReferenceProvider())
	}
}
