package org.intellij.sdk.language

import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.childrenOfType
import com.intellij.psi.util.nextLeaf
import com.intellij.util.containers.toMutableSmartList
import org.intellij.sdk.language.psi.FgdClassDefinition
import org.intellij.sdk.language.psi.FgdDoc
import org.intellij.sdk.language.psi.FgdField
import org.intellij.sdk.language.psi.FgdFile

class FgdStructureViewElement(e: PsiElement) : PsiTreeElementBase<PsiElement?>(e) {
	override fun getPresentableText(): String = element.let { elem ->
		when (elem) {
			is FgdFile -> elem.name
			is FgdClassDefinition -> elem.className.literal.text + " : " + elem.classVariant.text
			is FgdField -> elem.firstChild.let { field ->
				val name = field.firstChild.firstChild.text
				val typ = field.firstChild.nextLeaf { leaf -> leaf !is PsiWhiteSpace }?.nextSibling?.text ?:
					throw IllegalStateException("Type for field $name not found")
				"$name : $typ"
			}

			else -> throw IllegalStateException("Unexpected element: $elem")
		}
	}

	override fun getPresentation(): ItemPresentation {
		return object : PresentationData() {
			override fun getPresentableText(): String = this@FgdStructureViewElement.presentableText

			override fun getLocationString(): String? = null

			override fun getTooltip(): String? = "korv" /*this@FgdStructureViewElement.element.let { elem ->
				when (elem) {
					is FgdClassDefinition -> elem.childrenOfType<FgdDoc>().firstOrNull()?.text
					is FgdField -> elem.childrenOfType<FgdDoc>().firstOrNull()?.text
					else -> null
				}
			}*/

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
