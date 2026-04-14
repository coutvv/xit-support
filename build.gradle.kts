import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij.platform") version "2.14.0"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
}

group = "com.lomovtsev"
version = "1.2.0"

val junitVersion = "5.11.2"
val junitPlatformConsoleVersion = "1.11.2"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "233"
            untilBuild = "261.*"
        }
    }

    signing {
        certificateChainFile = file("certificate/chain.crt")
        privateKeyFile = file("certificate/private.pem")
        password = System.getenv("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = System.getenv("PUBLISH_TOKEN")
    }
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

    test {
        useJUnitPlatform()
    }

    generateLexer {
        sourceFile.set(file("src/main/kotlin/com/lomovtsev/xitsupport/Xit.flex"))
        targetOutputDir.set(file("src/main/gen/com/lomovtsev/xitsupport"))
        purgeOldFiles.set(true)
    }

    generateParser {
        sourceFile.set(file("src/main/kotlin/com/lomovtsev/xitsupport/Xit.bnf"))
        targetRootOutputDir.set(file("src/main/gen"))
        pathToParser.set("XitParser.java")
        pathToPsiRoot.set("com/lomovtsev/xitsupport/psi")
        purgeOldFiles.set(true)
    }
}

sourceSets["main"].java.srcDirs("src/main/gen")

dependencies {
    intellijPlatform {
        create("IC", "2023.3")
    }

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
