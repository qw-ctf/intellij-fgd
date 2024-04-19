package org.intellij.sdk.language

import com.google.common.collect.Lists
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import org.intellij.sdk.language.psi.FgdClassName
import org.intellij.sdk.language.psi.FgdFile
import java.util.LinkedList

object FgdUtil {
	fun findProperties(project: Project?, key: String): List<FgdClassName> {
		val result: MutableList<FgdClassName> = ArrayList<FgdClassName>()
		val virtualFiles =
			FileTypeIndex.getFiles(FgdFileType, GlobalSearchScope.allScope(project!!))
		for (virtualFile in virtualFiles) {
			val simpleFile: FgdFile? = PsiManager.getInstance(project).findFile(virtualFile!!) as FgdFile?
			if (simpleFile != null) {
				val properties: Array<out FgdClassName>? = PsiTreeUtil.getChildrenOfType(simpleFile, FgdClassName::class.java)
				if (properties != null) {
					for (property in properties) {
						if (key == property.text) {
							result.add(property)
						}
					}
				}
			}
		}
		return result
	}

	fun findProperties(project: Project?): List<FgdClassName> {
		val result = mutableListOf<FgdClassName>()
		val virtualFiles =
			FileTypeIndex.getFiles(FgdFileType, GlobalSearchScope.allScope(project!!))
		for (virtualFile in virtualFiles) {
			val simpleFile: FgdFile? = PsiManager.getInstance(project).findFile(virtualFile!!) as FgdFile?
			if (simpleFile != null) {
				val properties: Array<out FgdClassName>? = PsiTreeUtil.getChildrenOfType(simpleFile, FgdClassName::class.java)
				if (properties != null) {
					result.addAll(properties)
				}
			}
		}
		return result
	}
}
