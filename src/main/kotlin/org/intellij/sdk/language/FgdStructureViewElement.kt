package org.intellij.sdk.language

import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.containers.toMutableSmartList
import org.intellij.sdk.language.psi.FgdClassName
import org.intellij.sdk.language.psi.FgdFile

class FgdStructureViewElement(e: PsiElement) : PsiTreeElementBase<PsiElement?>(e) {
	override fun getPresentableText(): String {
		return element.let { elem ->
			when (elem) {
				is FgdFile -> elem.name
				is FgdClassName -> elem.literal.text
				else -> throw IllegalStateException("Unexpected element: $element")
			}
		}
	}

	override fun getChildrenBase(): MutableCollection<StructureViewTreeElement> {
		return element.let { elem ->
			when (elem) {
				is FgdFile ->
					PsiTreeUtil.collectElements(elem) { e: PsiElement? -> e is FgdClassName }
						.map(::FgdStructureViewElement)
						.toMutableSmartList()
				else -> throw IllegalStateException("Unexpected element: $elem")
			}
		}
	}
}
