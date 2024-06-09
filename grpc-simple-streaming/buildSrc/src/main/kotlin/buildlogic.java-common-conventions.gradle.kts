/*
 */

plugins {
    // Apply the java Plugin to add support for Java.
    java
}

repositories {
    // The Google mirror is less flaky than mavenCentral()
    maven (
        url = "https://maven-central.storage-download.googleapis.com/maven2/"
    )
    mavenCentral()
    gradlePluginPortal()
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    // https://mvnrepository.com/artifact/org.jetbrains/annotations
    compileOnly("org.jetbrains:annotations:24.1.0")

    constraints {
        // Define dependency versions as constraints
        // implementation("org.apache.commons:commons-text:1.11.0")
    }

    // Use JUnit Jupiter for testing.
//    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
//
//    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}



tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
