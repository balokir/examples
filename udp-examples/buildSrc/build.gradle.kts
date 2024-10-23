//   libs.versions.toml support inside buildSrc
import org.gradle.accessors.dm.LibrariesForLibs

private val Project.libs: LibrariesForLibs
    get() = extensions.getByType()

plugins {
    // Support convention plugins written in Kotlin. Convention plugins are build scripts in 'src/main' that automatically become available as plugins in the main build.
    `kotlin-dsl`
}

repositories {
    // Use the plugin portal to apply community plugins in convention plugins.
    gradlePluginPortal()
}

dependencies {
    //   magic string which allows support libs.versions.toml inside buildSrc
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    /*
     * References to Maven coordinates for Gradle plugins
     * which is necessary to use them in precompiled plugin scripts.
     */
    implementation(libs.plugin.testLogger)

}