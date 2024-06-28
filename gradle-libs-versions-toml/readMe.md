# Example of using gradle *version catalog* with precompiled plugin scripts (buildSrc) 

__BaloKir (c)__

The project based on default gradle "Application and library project" template.
Uses gradle v 8.8

All dependencies declared inside the [libs.versions.toml](gradle/libs.versions.toml) file.

Precompiled script [buildlogic.java-common-conventions](buildSrc/src/main/kotlin/buildlogic.java-common-conventions.gradle.kts)
applies "com.adarshr.test-logger".