package org.intellij.sdk.language.psi

import com.intellij.psi.PsiElement

interface FgdFieldMixin : PsiElement {
	fun getDoc(): String?
	fun getFieldName(): String
	fun getDescription(): String
}
