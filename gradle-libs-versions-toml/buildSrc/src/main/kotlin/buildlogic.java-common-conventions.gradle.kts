//   libs.versions.toml support inside buildSrc #3
import org.gradle.accessors.dm.LibrariesForLibs

private val Project.libs: LibrariesForLibs
    get() = extensions.getByType()


plugins {
    // Apply the java Plugin to add support for Java.
    java
    id("com.adarshr.test-logger")
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

//  now we can refer libs from  libs.versions.toml
dependencies {
//    implementation(libs.plugins.testLogger)
    constraints {
        // Define dependency versions as constraints
        implementation(libs.commons.text)
    }

    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly(libs.junit.platform.launcher)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks {
    test {
        useJUnitPlatform()
    }

    testlogger {
        theme = com.adarshr.gradle.testlogger.theme.ThemeType.PLAIN
    }
}
