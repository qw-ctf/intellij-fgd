package org.intellij.sdk.language.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import org.intellij.sdk.language.FgdFileType
import org.intellij.sdk.language.FgdLanguage

class FgdFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, FgdLanguage) {
	override fun getFileType(): FileType = FgdFileType
	override fun toString(): String = "FGD File"
}
