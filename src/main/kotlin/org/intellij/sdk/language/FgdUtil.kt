package org.intellij.sdk.language

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import org.intellij.sdk.language.psi.FgdClassName
import org.intellij.sdk.language.psi.FgdFile

object FgdUtil {
	fun findClassName(project: Project?, key: String): List<FgdClassName> {
		return FileTypeIndex.getFiles(FgdFileType, GlobalSearchScope.allScope(project!!))
			.asSequence()
			.mapNotNull { PsiManager.getInstance(project).findFile(it) as? FgdFile }
			.flatMap { PsiTreeUtil.findChildrenOfType(it, FgdClassName::class.java).asSequence() }
			.filter { it.text.lowercase() == key.lowercase() }
			.toList()
	}

	fun findClassName(project: Project?): List<FgdClassName> {
		val result = mutableListOf<FgdClassName>()
		val virtualFiles =
			FileTypeIndex.getFiles(FgdFileType, GlobalSearchScope.allScope(project!!))
		for (virtualFile in virtualFiles) {
			val simpleFile: FgdFile? = PsiManager.getInstance(project).findFile(virtualFile!!) as FgdFile?
			if (simpleFile != null) {
				val properties = PsiTreeUtil.findChildrenOfType(simpleFile, FgdClassName::class.java)
				result.addAll(properties)
			}
		}
		return result
	}
}
