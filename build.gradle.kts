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
    antlr("org.antlr:antlr4:4.10.1")
    implementation("org.antlr:antlr4-runtime:4.10.1")
}

tasks {
    generateGrammarSource {
        maxHeapSize = "128m"
        arguments = arguments + listOf("-visitor", "-long-messages")
        source = fileTree("src/main/antlr4")
        outputDirectory = file("${buildDir}/generated-src/antlr4")
    }
    
    compileKotlin {
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

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}