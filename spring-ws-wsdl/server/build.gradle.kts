plugins {
    id("buildlogic.spring-boot-application-conventions")
}

tasks {
    bootJar {
        mainClass = "me.balokir.soap.server.SpringWsServerApp"
    }
}

dependencies {
    implementation(projects.wsdl)
    implementation(libs.spring.boot.starter.web.services)
    implementation(libs.spring.boot.starter.security)
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.9")
}
