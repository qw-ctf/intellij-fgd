package org.intellij.sdk.language

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object FgdFileType : LanguageFileType(FgdLanguage) {
	override fun getName(): String = "FGD [Plugin: fgd]"
	override fun getDescription(): String = "Forge Game Data description file"
	override fun getDefaultExtension(): String = "fgd"
	override fun getIcon(): Icon = FgdIcons.FILE
}
