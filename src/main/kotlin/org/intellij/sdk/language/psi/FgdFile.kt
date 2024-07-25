package org.intellij.sdk.language.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.DelegatingScopeProcessor
import com.intellij.psi.scope.PsiScopeProcessor
import org.intellij.sdk.language.FgdFileType
import org.intellij.sdk.language.FgdLanguage

class FgdFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, FgdLanguage) {
	override fun getFileType(): FileType = FgdFileType
	override fun toString(): String = "FGD File"

	override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
		val wrapper = object : DelegatingScopeProcessor(processor) {
			override fun execute(element: PsiElement, state: ResolveState): Boolean {
				val result = super.execute(element, state)
				return result
			}
		}
		return super.processDeclarations(wrapper, state, lastParent, place)
	}
}
