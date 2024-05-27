package org.intellij.sdk.language

import com.intellij.formatting.Alignment
import com.intellij.formatting.FormattingContext
import com.intellij.formatting.FormattingModel
import com.intellij.formatting.FormattingModelBuilder
import com.intellij.formatting.FormattingModelProvider
import com.intellij.formatting.Indent
import com.intellij.formatting.SpacingBuilder
import com.intellij.formatting.Wrap
import com.intellij.formatting.WrapType
import com.intellij.psi.codeStyle.CodeStyleSettings
import org.intellij.sdk.language.psi.FgdTypes

internal class FgdFormattingModelBuilder : FormattingModelBuilder {
	override fun createModel(formattingContext: FormattingContext): FormattingModel {
		val codeStyleSettings = formattingContext.codeStyleSettings
		codeStyleSettings.indentOptions.INDENT_SIZE
		return FormattingModelProvider
			.createFormattingModelForPsiFile(
				formattingContext.containingFile,
				FgdBlock(
					formattingContext.node,
					Wrap.createWrap(WrapType.NONE, false),
					Indent.getNoneIndent(),
					Alignment.createAlignment(),
					createSpaceBuilder(codeStyleSettings),
				),
				codeStyleSettings,
			)
	}

}

private fun createSpaceBuilder(settings: CodeStyleSettings): SpacingBuilder {
	return SpacingBuilder(settings, FgdLanguage)
		.around(FgdTypes.CLASS_NAME)
		.spaceIf(settings.getCommonSettings(FgdLanguage.id).SPACE_AROUND_ASSIGNMENT_OPERATORS)
		.before(FgdTypes.DOC)
		.none()
}
