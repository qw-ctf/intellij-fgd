package org.intellij.sdk.language

import com.intellij.navigation.ChooseByNameContributorEx
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.Processor
import com.intellij.util.containers.ContainerUtil
import com.intellij.util.indexing.FindSymbolParameters
import com.intellij.util.indexing.IdFilter
import org.intellij.sdk.language.psi.FgdClassName
import java.util.Objects

class FgdChooseByNameContributor : ChooseByNameContributorEx {
	override fun processNames(processor: Processor<in String>, scope: GlobalSearchScope, filter: IdFilter?) {
		val project: Project? = Objects.requireNonNull(scope.project)
		val propertyKeys: List<String> = FgdUtil.findClassName(project).map(FgdClassName::getText)
		ContainerUtil.process(propertyKeys, processor)
	}

	override fun processElementsWithName(name: String, processor: Processor<in NavigationItem>, parameters: FindSymbolParameters) {
		val properties: MutableList<NavigationItem> = ContainerUtil.map(
			FgdUtil.findClassName(parameters.project, name),
		) { property -> property as NavigationItem }
		ContainerUtil.process(properties, processor)
	}
}
