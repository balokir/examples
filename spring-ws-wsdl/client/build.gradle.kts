plugins {
    id("buildlogic.spring-boot-application-conventions")
}

tasks {
    bootJar {
        mainClass = "me.balokir.soap.client.SpringWsClientApp"
    }
}

dependencies {
    implementation(projects.wsdl)
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.9")
    implementation("org.springframework:spring-oxm:5.3.31")
    implementation("org.springframework.ws:spring-ws-core:3.1.8")
    implementation("com.sun.xml.messaging.saaj:saaj-impl:1.5.3")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")


}
