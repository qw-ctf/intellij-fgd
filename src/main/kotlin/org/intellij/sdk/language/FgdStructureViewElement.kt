package org.intellij.sdk.language

import com.intellij.icons.AllIcons
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.containers.toMutableSmartList
import org.intellij.sdk.language.psi.FgdClassDefinition
import org.intellij.sdk.language.psi.FgdField
import org.intellij.sdk.language.psi.FgdFile

class FgdStructureViewElement(e: PsiElement) : PsiTreeElementBase<PsiElement?>(e) {
	override fun getPresentableText(): String = element.let { elem ->
		when (elem) {
			is FgdFile -> elem.name
			is FgdClassDefinition -> elem.className.literal.text + " : " + elem.literal.text
			is FgdField -> elem.firstChild.let { field ->
				val name = field.firstChild.firstChild.text
				val typ = field.firstChild.nextSibling.nextSibling.text
				"$name : $typ"
			}

			else -> throw IllegalStateException("Unexpected element: $elem")
		}
	}

	override fun getPresentation(): ItemPresentation {
		return object : ItemPresentation {
			override fun getPresentableText(): String = this@FgdStructureViewElement.presentableText

			override fun getLocationString(): String? = null

			override fun getIcon(unused: Boolean): javax.swing.Icon = this@FgdStructureViewElement.element.let { elem ->
				when (elem) {
					is FgdFile -> AllIcons.FileTypes.Any_type
					is FgdClassDefinition -> AllIcons.Nodes.Class
					is FgdField -> AllIcons.Nodes.Field
					else -> throw IllegalStateException("Unexpected element: $elem")
				}
			}
		}
	}

	override fun getChildrenBase(): MutableCollection<StructureViewTreeElement> = element.let { elem ->
		when (elem) {
			is FgdFile ->
				PsiTreeUtil.collectElements(elem) { e: PsiElement? -> e is FgdClassDefinition }
					.map(::FgdStructureViewElement)
					.toMutableSmartList()

			is FgdClassDefinition ->
				PsiTreeUtil.collectElements(elem) { e: PsiElement? -> e is FgdField }
					.map(::FgdStructureViewElement)
					.toMutableSmartList()

			else -> throw IllegalStateException("Unexpected element: $elem")
		}
	}
}
