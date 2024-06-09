rootProject.name = "grpc-simple-streaming"
include("common", "client", "server")

pluginManagement {
    plugins {
        id("org.springframework.boot") version "3.3.0"
        id("io.spring.dependency-management") version "1.1.5"
        id("com.google.protobuf") version "0.9.4"

    }
    resolutionStrategy {

    }
}
