package org.intellij.sdk.language

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.richcopy.HtmlSyntaxInfoUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.parentOfType
import org.intellij.sdk.language.psi.FgdClassDefinition
import org.intellij.sdk.language.psi.FgdClassName
import org.intellij.sdk.language.psi.FgdField
import org.intellij.sdk.language.psi.FgdParamReference

fun StringBuilder.appendAs(word: String, textAttributesKey: TextAttributesKey): StringBuilder = also {
	val textAttributes = EditorColorsManager.getInstance().globalScheme.getAttributes(textAttributesKey)
	HtmlSyntaxInfoUtil.appendStyledSpan(this, textAttributes, word, 1.0f)
}

class FgdDocumentationProvider : AbstractDocumentationProvider() {
	private fun generateClassDoc(element: FgdClassName): String? {
		val classDef = element.parentOfType<FgdClassDefinition>() ?: return null
		val sb = StringBuilder()

		sb.append(DocumentationMarkup.DEFINITION_START)
		sb.appendAs(classDef.classVariant.text, FgdSyntaxHighlighter.BUILTINS)
		sb.append(" ")
		sb.appendAs(element.text, FgdSyntaxHighlighter.CLASS_NAME)
		sb.append(DocumentationMarkup.DEFINITION_END)
		classDef.doc?.text?.let { doc ->
			sb.append(DocumentationMarkup.CONTENT_START);
			sb.append("<p>")
			sb.append(doc.trimStart('"').trimEnd('"').replace("\n", "<br/>"));
			sb.append("</p>")
			sb.append(DocumentationMarkup.CONTENT_END);
		}


		sb.append(DocumentationMarkup.SECTIONS_START)
			.append(DocumentationMarkup.SECTION_HEADER_START)
			.append("Params:")
			.append(DocumentationMarkup.SECTION_END)


		PsiTreeUtil.findChildrenOfType(classDef, FgdField::class.java).forEach { field ->
			sb.append("<p><code>")
				.append(field.getFieldName())
				.append("</code>")
			field.getDoc()?.let { doc ->
				sb.append(" - ")
				sb.appendAs(doc, FgdSyntaxHighlighter.COMMENT)
			}
			sb.append("</p>")
		}

		sb.append(DocumentationMarkup.SECTIONS_END)


		return sb.toString()
	}

	override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String {
		return when (element) {
			is FgdClassName ->
				generateClassDoc(element)
			is FgdParamReference ->
				FgdUtil.findClassName(element.project, element.literal.text).firstOrNull()?.let { elem ->
					println(elem as? FgdClassName)
					(elem as? FgdClassName)?.let(::generateClassDoc)
				}

			else -> null
		}  ?: "No documentation available"
	}
}
