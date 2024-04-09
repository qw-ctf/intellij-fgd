package org.intellij.sdk.language.psi.impl

import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiFile
import org.intellij.sdk.language.psi.FgdClassName
import org.intellij.sdk.language.psi.FgdTypes
import javax.swing.Icon

object FgdPsiImplUtil {
	fun getKey(element: FgdClassName): String? {
		return element.getNode().findChildByType(FgdTypes.CLASS_NAME)?.text
	}

	fun getPresentation(element: FgdClassName): ItemPresentation {
		return object : ItemPresentation {
			override fun getPresentableText(): String? {
				return element.text
			}

			override fun getLocationString(): String? {
				val containingFile: PsiFile = element.getContainingFile()
				return if (containingFile == null) null else containingFile.name
			}

			override fun getIcon(unused: Boolean): Icon? {
				return element.getIcon(0)
			}
		}
	}
}
