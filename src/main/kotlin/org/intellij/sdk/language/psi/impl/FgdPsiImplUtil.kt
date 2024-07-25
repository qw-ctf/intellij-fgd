package org.intellij.sdk.language.psi.impl

import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiFile
import org.intellij.sdk.language.psi.FgdClassName
import org.intellij.sdk.language.psi.FgdField
import org.intellij.sdk.language.psi.FgdTypes
import javax.swing.Icon

object FgdPsiImplUtil {
	fun getKey(element: FgdClassName): String? {
		return element.node.findChildByType(FgdTypes.CLASS_NAME)?.text
	}

	fun getPresentation(element: FgdClassName): ItemPresentation {
		return object : ItemPresentation {
			override fun getPresentableText(): String? {
				return element.text
			}

			override fun getLocationString(): String? {
				val containingFile: PsiFile = element.containingFile
				return containingFile.name
			}

			override fun getIcon(unused: Boolean): Icon? {
				return element.getIcon(0)
			}
		}
	}

	@JvmStatic
	fun getDoc(element: FgdField): String? {
		// Implementation to retrieve doc
		val docElement = element.node.findChildByType(FgdTypes.DOC)
		return docElement?.text
	}

	@JvmStatic
	fun getFieldName(element: FgdField): String {
		// Implementation to retrieve field name
		val nameElement = element.node.findChildByType(FgdTypes.FIELD_NAME)
		return nameElement?.text ?: ""
	}

	@JvmStatic
	fun getDescription(element: FgdField): String {
		// Implementation to retrieve description
		val descElement = element.node.findChildByType(FgdTypes.DESCRIPTION)
		return descElement?.text ?: ""
	}
}
