import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

private val Project.libs: LibrariesForLibs
    get() = extensions.getByType()

plugins {
    id("buildlogic.common-conventions")
    java
    // Apply the java Plugin to add support for Java.
    id("com.adarshr.test-logger")
    id("maven-publish")
}

dependencies {
    compileOnly(libs.jetbrains.annotations)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)
    implementation(libs.logback.core)
    implementation(libs.apache.log4j.slf4j)
    // Use JUnit Jupiter for testing.
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
    testRuntimeOnly(libs.junit.platformLauncher)
    testImplementation(libs.junit.jupiter)
    // Use Hamcrest for assertion.
    testImplementation(libs.hamcrest)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = rootProject.group as String
            artifactId = project.name
            version = project.version as String
            from(components["java"])
        }
    }
}

tasks {
    // Apply a specific Java toolchain to ease working on different environments.
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
            vendor = JvmVendorSpec.BELLSOFT
        }
        compileJava{
            options.encoding = "UTF-8"
        }
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    javadoc{
        options.encoding = "UTF-8"
    }

    test {
        // Use JUnit Platform for unit tests.
        defaultCharacterEncoding = "UTF-8"
        useJUnitPlatform()
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
        }
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        into("META-INF/maven/${rootProject.group}/${project.name}") {
            val generatePomFileForMavenJavaPublication by tasks.getting(GenerateMavenPom::class)
            from(generatePomFileForMavenJavaPublication) {
                rename(".*", "pom.xml")
            }

            manifest {
                attributes["Bundle-Version"] = version
                attributes["Bundle-Name"] = project.name
                attributes["Automatic-Module-Name"] = rootProject.group
                attributes["Bundle-SymbolicName"] = rootProject.group
                attributes["Built-By"] = "Marintel Group"
            }
        }
    }
}