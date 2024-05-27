package org.intellij.sdk.language

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveElementWalkingVisitor
import com.intellij.psi.util.childrenOfType
import org.intellij.sdk.language.psi.FgdClassDefinition
import org.intellij.sdk.language.psi.FgdClassName
import org.intellij.sdk.language.psi.FgdField

class FgdFoldingBuilder : FoldingBuilderEx(), DumbAware {
	override fun buildFoldRegions(
		root: PsiElement,
		document: Document,
		quick: Boolean
	): Array<out FoldingDescriptor> {
		val descriptors: MutableList<FoldingDescriptor> = mutableListOf()
		root.accept(object : PsiRecursiveElementWalkingVisitor() {
			override fun visitElement(element: PsiElement) {
				if (element is FgdClassDefinition) {
					element.childrenOfType<FgdClassName>().firstOrNull()?.nextSibling?.let { fields ->
						val range = TextRange(fields.textRange.startOffset, element.textRange.endOffset)
						descriptors.add(FoldingDescriptor(element, range))
					}
				} else {
					super.visitElement(element)
				}
			}
		})
		return descriptors.toTypedArray()
	}

	override fun getPlaceholderText(node: ASTNode): String? {
		val numFields = node.psi.childrenOfType<FgdField>().size
		if (numFields > 0) {
			return "... ($numFields fields)"
		}
		return "..."
	}

	override fun isCollapsedByDefault(node: ASTNode): Boolean {
		return false
	}
}
