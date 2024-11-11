import org.gradle.accessors.dm.LibrariesForLibs

private val Project.libs: LibrariesForLibs
    get() = extensions.getByType()

plugins {
    // Apply the java Plugin to add support for Java.
    java
}

repositories {
    // The Google mirror is less flaky than mavenCentral()
    maven(
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
    compileOnly(libs.jetbrains.annotations)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)
    implementation(libs.logback.core)

    implementation(libs.bundles.netty)

    // Use JUnit Jupiter for testing.
    testRuntimeOnly(libs.junit.platformLauncher)
    testImplementation(libs.junit.jupiter)
    // Use Hamcrest for assertion.
    testImplementation(libs.hamcrest)
}



tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
