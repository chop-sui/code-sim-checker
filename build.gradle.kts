import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
    antlr
    java
    idea
}

idea {
    module {
        generatedSourceDirs.add(file("${buildDir}/generated-src/antlr4"))
    }
}

group = "me.jrsuh"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    antlr("org.antlr:antlr4:4.9.3")
    implementation("org.antlr:antlr4-runtime:4.9.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.0.1")
    implementation(files("$projectDir/external-jars/static-diff-1.0.0.jar"))
}

tasks {
    generateGrammarSource {
        maxHeapSize = "128m"
        arguments = arguments + listOf("-visitor")
        source = fileTree("src/main/antlr4")
        outputDirectory = file("${buildDir}/generated-src/antlr4")
    }
    
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
        dependsOn(generateGrammarSource)
    }
    
    clean {
        delete("generated-src")
    }
    
}

sourceSets {
    main {
        java.srcDir("${buildDir}/generated-src/antlr4")
    }
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}