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
        generatedSourceDirs.add(file("${project.buildDir}/generated-src/antlr/main"))
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
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    antlr("org.antlr:antlr4:4.10.1")
    implementation("org.antlr:antlr4-runtime:4.10.1")
}

tasks {
    generateGrammarSource {
        maxHeapSize = "128m"
        arguments = arguments + listOf("-package", "me.jrsuh.grammar", "-visitor", "-long-messages", "-no-listener")
        outputDirectory = file("${project.buildDir}/generated-src/antlr/main/me/jrsuh/grammar")
    }
    
    compileJava {
        dependsOn(generateGrammarSource)
    }
    
    clean {
        delete("generated-src")
    }
    
}

sourceSets {
    main {
        java.srcDir("generated-src/antlr/main/")
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.named("compileKotlin") {
    dependsOn(":generateGrammarSource")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}