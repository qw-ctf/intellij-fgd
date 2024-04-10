import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("java")
	id("org.jetbrains.kotlin.jvm") version "1.9.23"
	id("org.jetbrains.intellij") version "1.17.3"
	id("org.jetbrains.grammarkit") version "2022.3.2.2"
	id("com.diffplug.spotless") version "6.25.0"
}

group = "fgd"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

sourceSets["main"].java.srcDirs("src/main/gen/lexer", "src/main/gen/parser")

intellij {
	version.set("2023.3.3")
	type.set("IC")
	plugins.set(listOf())
}

spotless {
	isEnforceCheck = false

	kotlin {
		targetExclude("**/build/**")
		ktlint("1.2.1")
			.setEditorConfigPath("$projectDir/.editorconfig")
			.editorConfigOverride(
				mapOf(
					"ktlint_standard_no-wildcard-imports" to "disabled",
				),
			)
		isEnforceCheck = "true" == System.getProperty("CI")
	}
}

tasks {
	generateLexer {
		sourceFile = file("src/main/grammar/Fgd.flex")
		targetOutputDir = file("src/main/gen/lexer/org/intellij/sdk/language")
		purgeOldFiles = true
	}

	generateParser {
		sourceFile = file("src/main/grammar/Fgd.bnf")
		targetRootOutputDir = file("src/main/gen/parser")
		pathToParser = "org/org/intellij/sdk/language/parser/FgdParser.java"
		pathToPsiRoot = "org/intellij/sdk/language/psi"
		purgeOldFiles = true
	}

	withType<JavaCompile> {
		sourceCompatibility = JavaVersion.VERSION_17.toString()
		targetCompatibility = JavaVersion.VERSION_17.toString()
	}

	withType<KotlinCompile> {
		kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
		dependsOn(generateLexer, generateParser)
	}

	patchPluginXml {
		sinceBuild.set("233")
		untilBuild.set("241.*")
	}

	signPlugin {
		certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
		privateKey.set(System.getenv("PRIVATE_KEY"))
		password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
	}

	publishPlugin {
		token.set(System.getenv("PUBLISH_TOKEN"))
	}

	wrapper {
		gradleVersion = "8.7"
		distributionUrl = "https://services.gradle.org/distributions/gradle-${gradleVersion}-bin.zip"
		distributionSha256Sum = "544c35d6bd849ae8a5ed0bcea39ba677dc40f49df7d1835561582da2009b961d"
	}
}
