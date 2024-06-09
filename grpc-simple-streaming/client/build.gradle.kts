/*
 * The build script for client app
 */
plugins {

    id("buildlogic.java-application-conventions")

    id("org.springframework.boot")
    id("io.spring.dependency-management")
}


group = "me.balokir.grpc_simple_streaming.client"
version = "1.0-SNAPSHOT"

dependencies {

    implementation(project(":common"))

    //spring-boot
    implementation("org.springframework.boot:spring-boot-starter")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    //lombok
    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    //logging
    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api
    implementation("org.apache.logging.log4j:log4j-api:2.23.1")
    runtimeOnly("org.apache.logging.log4j:log4j-core:2.23.1")

    //jackson
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-yaml
    implementation ("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.17.1")

    // https://mvnrepository.com/artifact/io.grpc/grpc-netty-shaded
    runtimeOnly("io.grpc:grpc-netty-shaded:1.64.0")
}
