package org.intellij.sdk.language

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.intellij.sdk.language.parser.FgdParser
import org.intellij.sdk.language.psi.FgdFile
import org.intellij.sdk.language.psi.FgdTokenSets
import org.intellij.sdk.language.psi.FgdTypes

internal class FgdParserDefinition : ParserDefinition {
	override fun createLexer(project: Project): Lexer = FgdLexerAdapter()
	override fun getCommentTokens(): TokenSet = FgdTokenSets.COMMENTS
	override fun getStringLiteralElements(): TokenSet = FgdTokenSets.STRINGS
	override fun createParser(project: Project): PsiParser = FgdParser()
	override fun getFileNodeType(): IFileElementType = FILE
	override fun createFile(viewProvider: FileViewProvider): PsiFile = FgdFile(viewProvider)
	override fun createElement(node: ASTNode): PsiElement = FgdTypes.Factory.createElement(node)
}

val FILE: IFileElementType = IFileElementType(FgdLanguage)
