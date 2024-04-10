@file:Suppress("UnstableApiUsage")

package org.intellij.sdk.language

import com.intellij.codeInsight.hints.ChangeListener
import com.intellij.codeInsight.hints.FactoryInlayHintsCollector
import com.intellij.codeInsight.hints.ImmediateConfigurable
import com.intellij.codeInsight.hints.InlayHintsCollector
import com.intellij.codeInsight.hints.InlayHintsProvider
import com.intellij.codeInsight.hints.InlayHintsSink
import com.intellij.codeInsight.hints.NoSettings
import com.intellij.codeInsight.hints.SettingsKey
import com.intellij.codeInsight.hints.presentation.BasePresentation
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import org.intellij.sdk.language.psi.FgdAttribute
import org.intellij.sdk.language.psi.FgdNumber
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import javax.swing.JComponent
import javax.swing.JPanel

class FgdColorInlayProvider : InlayHintsProvider<NoSettings> {
	fun getColorOrNull(elem: PsiElement): List<FgdNumber>? {
		if (elem !is FgdAttribute) {
			return null
		}
		if (elem.literal.text != "color") {
			return null
		}
		return elem.paramNumber3?.number3List?.first()?.numberList
	}

	override fun getCollectorFor(file: PsiFile, editor: Editor, settings: NoSettings, sink: InlayHintsSink): InlayHintsCollector {
		return object : FactoryInlayHintsCollector(editor) {
			override fun collect(element: PsiElement, editor: Editor, sink: InlayHintsSink): Boolean {
				getColorOrNull(element)?.let { color ->
					val r = color[0].float?.text ?: color[0].integer?.text
					val g = color[1].float?.text ?: color[1].integer?.text
					val b = color[2].float?.text ?: color[2].integer?.text
					var rf = r?.toFloatOrNull() ?: 1.0f
					var gf = g?.toFloatOrNull() ?: 1.0f
					var bf = b?.toFloatOrNull() ?: 1.0f
					if (rf > 1.0 || gf > 1.0 || bf > 1.0) {
						rf /= 255.0f
						gf /= 255.0f
						bf /= 255.0f
					}
					val color = Color(rf, gf, bf)
					sink.addInlineElement(element.textRange.endOffset, true, ColorBoxPresentation(editor, color), false)
				}
				return true
			}
		}
	}

	override val key: SettingsKey<NoSettings> = SettingsKey("color.inlay.key")
	override val name: String = "Color Preview"
	override val previewText: String? = null
	override fun createConfigurable(settings: NoSettings): ImmediateConfigurable {
		return object : ImmediateConfigurable {
			override fun createComponent(listener: ChangeListener): JComponent {
				return JPanel()
			}
		}
	}

	override fun createSettings(): NoSettings = NoSettings()
}

class ColorBoxPresentation(private val editor: Editor, private val color: Color) : BasePresentation() {
	override val height: Int
		get() = editor.colorsScheme.getFont(EditorFontType.PLAIN).size
	override val width: Int
		get() = editor.colorsScheme.getFont(EditorFontType.PLAIN).size * 2

	override fun paint(g: Graphics2D, attributes: TextAttributes) {
		val oldColor: Color = g.color
		g.color = color
		g.fill(Rectangle2D.Float(height / 2.0f, height / 2.0f, height.toFloat(), height.toFloat()))
		g.color = oldColor
	}

	override fun toString(): String {
		return "FgdColorPreview"
	}
}
