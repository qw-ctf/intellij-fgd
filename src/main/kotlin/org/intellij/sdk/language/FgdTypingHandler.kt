package org.intellij.sdk.language

import com.intellij.codeInsight.completion.CodeCompletionHandlerBase
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

class FgdTypingHandler : TypedHandlerDelegate() {
	override fun beforeCharTyped(c: Char, project: Project, editor: Editor, file: PsiFile, fileType: FileType): Result {
		if (c == '@') {
			if (shouldTriggerCompletion(editor, file)) {
				ApplicationManager.getApplication().invokeLater {
					editor.project?.let { project ->
						if (!editor.isDisposed) {
							CodeCompletionHandlerBase(CompletionType.BASIC).invokeCompletion(project, editor, 1)
						}
					}
				}
			}
		}
		return super.beforeCharTyped(c, project, editor, file, fileType)
	}

	private fun shouldTriggerCompletion(editor: Editor, file: PsiFile): Boolean {
		// check if position is after root>at
		return true
	}
}
