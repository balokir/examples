/*
	TYPESAFE_PROJECT_ACCESSORS required to access projects via the following:
	dependencies {
		implementation(projects.utilities)
	}
	instead of

	dependencies {
		api(project(":utilities"))
	}
*/
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "gradle-libs-versions-toml"
include("app", "list", "utilities")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}


dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    // Gradle cannot access the version catalog from here, so hard-code the dependency.
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
