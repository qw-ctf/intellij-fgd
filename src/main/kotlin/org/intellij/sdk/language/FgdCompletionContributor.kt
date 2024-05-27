package org.intellij.sdk.language

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.util.ProcessingContext
import org.intellij.sdk.language.psi.FgdTypes

internal class FgdCompletionContributor : CompletionContributor() {
	init {
		extend(
			CompletionType.BASIC,
			psiElement().afterLeaf(psiElement(FgdTypes.AT)),
			object : CompletionProvider<CompletionParameters?>() {
				override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, result: CompletionResultSet) {
					result.addElement(
						LookupElementBuilder.create("BaseClass").withCaseSensitivity(false)
							.withIcon(AllIcons.Nodes.AbstractClass),
					)
					result.addElement(
						LookupElementBuilder.create("PointClass").withCaseSensitivity(false)
							.withIcon(AllIcons.Nodes.Class),
					)
					result.addElement(
						LookupElementBuilder.create("SolidClass").withCaseSensitivity(false)
							.withIcon(AllIcons.Nodes.Class),
					)
				}
			},
		)
	}
}
