package org.intellij.sdk.language

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class FgdColorSettingsPage : ColorSettingsPage {
	override fun getIcon(): Icon = FgdIcons.FILE

	override fun getHighlighter(): SyntaxHighlighter = FgdSyntaxHighlighter()

	override fun getDemoText(): String {
		return """
        //
        // toggleable laser, damages on touch
        //

        @SolidClass base(AppearFlags, TargetName, Target) color(255 128 128) = func_laser : 
        "A togglable laser, hurts to touch, can be used to block players." 
        [
        	dmg(integer) : "Damage" : 1 : "Damage dealt. Set to -1 to do no damage."
        	alpha(float) : "Opacity" : "0.5" : "Approximate alpha you want the laser drawn at. default 0.5. alpha will flicker in a 20% range around this value."
        	noise(string) : "Sound name for laser inactive, default buttons/switch02.wav."
        	noise1(string) : "Sound name for laser active, default buttons/switch04.wav."
        	noise2(string) : "Sound name for laser touched, default misc/null.wav."
        	spawnflags(flags) =
        	[
        		1 : "Starts off" : 0
        		2 : "Solid" : 0
        	]
        ]
		""".trimIndent()
	}

	override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? = null

	override fun getAttributeDescriptors(): Array<out AttributesDescriptor> = DESCRIPTORS

	override fun getColorDescriptors(): Array<out ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

	override fun getDisplayName(): String = "FGD"

	companion object {
		private val DESCRIPTORS = arrayOf(
			AttributesDescriptor("Key", FgdSyntaxHighlighter.KEY),
			AttributesDescriptor("Separator", FgdSyntaxHighlighter.SEPARATOR),
			AttributesDescriptor("Value", FgdSyntaxHighlighter.VALUE),
			AttributesDescriptor("Bad value", FgdSyntaxHighlighter.BAD_CHARACTER),
		)
	}
}
