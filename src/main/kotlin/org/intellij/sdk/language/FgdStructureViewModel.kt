package org.intellij.sdk.language

import com.intellij.ide.structureView.StructureViewModel.ElementInfoProvider
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile
import org.intellij.sdk.language.psi.FgdClassDefinition
import org.intellij.sdk.language.psi.FgdField
import org.intellij.sdk.language.psi.FgdFile

class FgdStructureViewModel(editor: Editor?, psiFile: PsiFile) :
	StructureViewModelBase(psiFile, editor, FgdStructureViewElement(psiFile)), ElementInfoProvider {
	init {
		withSuitableClasses(
			FgdFile::class.java,
			FgdClassDefinition::class.java,
			FgdField::class.java,
		).withSorters(Sorter.ALPHA_SORTER)
	}

	override fun isAlwaysShowsPlus(element: StructureViewTreeElement): Boolean {
		return element.value is FgdClassDefinition
	}

	override fun isAlwaysLeaf(element: StructureViewTreeElement): Boolean {
		return element.value is FgdField
	}
}
