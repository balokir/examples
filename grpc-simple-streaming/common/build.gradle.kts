/*
 * The build script for common project
 */

import com.google.protobuf.gradle.id

plugins {
    id("buildlogic.java-library-conventions")
    id("com.google.protobuf")
}

group = "me.balokir.grpc_simple_streaming"
version = "1.0-SNAPSHOT"

val grpcVersion = "1.64.0" // CURRENT_GRPC_VERSION
val googleProtobufVersion = "4.27.0"


dependencies {

    api("io.grpc:grpc-protobuf:$grpcVersion")
    api("io.grpc:grpc-stub:$grpcVersion")

    implementation("com.google.protobuf:protobuf-java:$googleProtobufVersion")

    // Workaround for @javax.annotation.Generated
    // see: https://github.com/grpc/grpc-java/issues/3633
    compileOnly ("javax.annotation:javax.annotation-api:1.3.2")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$googleProtobufVersion"
    }

    plugins {
        // Optional: an artifact spec for a protoc plugin, with "grpc" as
        // the identifier, which can be referred to in the "plugins"
        // container of the "generateProtoTasks" closure.

        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
        }
    }

    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                // Apply the "grpc" plugin whose spec is defined above, without
                // options. Note the braces cannot be omitted, otherwise the
                // plugin will not be added. This is because of the implicit way
                // NamedDomainObjectContainer binds the methods.
                id("grpc") { }
            }
        }
    }
}