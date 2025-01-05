import io.spring.gradle.dependencymanagement.dsl.MavenBomHandler
import org.gradle.accessors.dm.LibrariesForLibs

private val Project.libs: LibrariesForLibs
    get() = extensions.getByType()

plugins {
    id("buildlogic.java-common-conventions")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(libs.spring.boot.starter)
    annotationProcessor(libs.spring.boot.configuration)

    testImplementation(libs.spring.boot.test)
    testImplementation(libs.spring.test)
}

tasks {
    test {
        // avoid warnings in test phase
        jvmArgs("-Xshare:off", "-XX:+EnableDynamicAgentLoading")
    }
}

