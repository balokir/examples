plugins {
    java
    id("org.springframework.boot") version libs.versions.springBoot
    id("io.spring.dependency-management") version libs.versions.springBootDependencyManagement
}

group = "me.balokir"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencyManagement {
    repositories {
        mavenCentral()
    }

    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:${libs.versions.springBoot.get()}")
        {
            bomProperty("logback.version", libs.versions.logback.get())
            bomProperty("slf4j.version", libs.versions.slf4j.get())
            bomProperty("log4j2.version", libs.versions.apacheLog4j.get())
            bomProperty("jackson-bom.version", libs.versions.jackson.get())
            bomProperty("netty.version", libs.versions.netty.get())
            bomProperty("reactor-bom.version", libs.versions.reactorBom.get())

        }

        mavenBom("org.springframework:spring-framework-bom:${libs.versions.spring.get()}")
        {
            bomProperty("logback.version", libs.versions.logback.get())
            bomProperty("slf4j.version", libs.versions.slf4j.get())
            bomProperty("log4j2.version", libs.versions.apacheLog4j.get())
            bomProperty("jackson-bom.version", libs.versions.jackson.get())
            bomProperty("netty.version", libs.versions.netty.get())
            bomProperty("reactor-bom.version", libs.versions.reactorBom.get())
        }
    }

    // Accessing propeties from Imported Boms
//    println(dependencyManagement.importedProperties)
}


dependencies {


    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.webflux)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
