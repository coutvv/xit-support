import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij") version "1.16.1"

    id("org.jetbrains.grammarkit") version "2022.3.2.1"
}

group = "com.lomovtsev"
version = "1.0.4"

val junitVersion = "5.11.2"
val junitPlatformConsoleVersion = "1.11.2"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.1.5")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
        dependsOn(generateLexer, generateParser)
    }

    patchPluginXml {
        sinceBuild.set("231")
        untilBuild.set("243.*")
    }

    signPlugin {
        certificateChainFile.set(file("certificate/chain.crt"))
        privateKeyFile.set(file("certificate/private.pem"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    test {
        useJUnitPlatform()
    }

    generateLexer {
        sourceFile = file("src/main/kotlin/com/lomovtsev/xitsupport/Xit.flex")
        targetDir.set("src/main/gen/com/lomovtsev/xitsupport")
        targetClass.set("XitLexer")
        purgeOldFiles.set(true)
    }

    generateParser {
        sourceFile = file("src/main/kotlin/com/lomovtsev/xitsupport/Xit.bnf")
        targetRoot.set("src/main/gen")

        pathToParser.set("XitParser.java")
        pathToPsiRoot.set("com/lomovtsev/xitsupport/psi")
        purgeOldFiles.set(true)
    }
}

sourceSets["main"].java.srcDirs("src/main/gen")


dependencies {

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-console:$junitPlatformConsoleVersion")
}

grammarKit {
    jflexRelease.set("1.7.0-1")
    grammarKitRelease.set("2021.1.2")
    intellijRelease.set("203.7717.81")
}
