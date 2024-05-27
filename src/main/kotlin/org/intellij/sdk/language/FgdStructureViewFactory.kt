package org.intellij.sdk.language

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.structureView.StructureView
import com.intellij.ide.structureView.StructureViewBuilder
import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder
import com.intellij.lang.PsiStructureViewFactory
import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionAdapter
import javax.swing.JTree
import javax.swing.tree.TreePath

// https://github.com/lice-lang/lice-intellij/blob/9b3e23dd5176126691e99061c65124106b098d3a/src/org/lice/lang/editing/lice-basic-editing.kt#L99
class FgdStructureViewFactory : PsiStructureViewFactory {
	override fun getStructureViewBuilder(psiFile: PsiFile): StructureViewBuilder? {
		return object : TreeBasedStructureViewBuilder() {
			override fun createStructureViewModel(editor: Editor?): StructureViewModel {
				return FgdStructureViewModel(editor, psiFile)
			}

			override fun createStructureView(fileEditor: FileEditor?, project: Project): StructureView {
				val view = super.createStructureView(fileEditor, project)

				view.component.addMouseMotionListener(
					object : MouseMotionAdapter() {
						override fun mouseMoved(e: MouseEvent) {
							val path: TreePath? = (view.component as JTree).getPathForLocation(e.x, e.y)
							if (path != null) {
								val component: Any = path.lastPathComponent
								if (component is FgdStructureViewElement) {
									val presentation: ItemPresentation = component.presentation
									//component.setToolTipText(tooltipText)
								} else {
									//tree.setToolTipText(null)
								}
							}
						}
					},
				)
				return view
			}
		}
	}
}
