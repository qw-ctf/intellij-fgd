package org.intellij.sdk.language

import com.intellij.formatting.Alignment
import com.intellij.formatting.Block
import com.intellij.formatting.Indent
import com.intellij.formatting.Spacing
import com.intellij.formatting.SpacingBuilder
import com.intellij.formatting.Wrap
import com.intellij.formatting.WrapType
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock
import org.intellij.sdk.language.psi.FgdTypes


class FgdBlock(node: ASTNode, wrap: Wrap, private val indent: Indent, alignment: Alignment, private val spacingBuilder: SpacingBuilder) : AbstractBlock(node, wrap, alignment) {
	override fun buildChildren(): List<Block> {
		val blocks: MutableList<Block> = ArrayList()
		var child = myNode.firstChildNode
		while (child != null) {
			if (child.elementType !== TokenType.WHITE_SPACE) {
				val alignment = if (child.elementType == FgdTypes.COLON) {
					Alignment.createAlignment(true, Alignment.Anchor.RIGHT)
				} else {
					Alignment.createAlignment()
				}
				val nextIndent = if (child.elementType == FgdTypes.FIELD) {
					Indent.getNormalIndent()
				} else {
					indent
				}
				val wrap = if (child.elementType == FgdTypes.LBRACKET && child.treeParent.elementType == FgdTypes.CLASS_DEFINITION) {
					Wrap.createWrap(WrapType.ALWAYS, true)
				} else {
					Wrap.createWrap(WrapType.NONE, false)
				}
				val block: Block = FgdBlock(
					child, wrap, nextIndent, alignment,
					spacingBuilder,
				)
				blocks.add(block)
			}
			child = child.treeNext
		}
		return blocks
	}

	override fun getIndent(): Indent {
		return indent
	}

	override fun getSpacing(child1: Block?, child2: Block): Spacing? {
		return spacingBuilder.getSpacing(this, child1, child2)
	}

	override fun isLeaf(): Boolean {
		return myNode.firstChildNode == null
	}
}
