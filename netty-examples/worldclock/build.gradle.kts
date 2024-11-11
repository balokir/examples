import com.google.protobuf.gradle.id


plugins {
    alias(libs.plugins.protobuf)
    id("buildlogic.java-application-conventions")
}

dependencies{
    implementation(project(":util"))
    implementation(libs.grpc.protobuf)
    implementation(libs.grpc.stub)

    implementation(libs.google.protobufJava)
}
protobuf {
    protoc {
        artifact = libs.google.protobufProtoc.get().toString()
    }

    plugins {
        // Optional: an artifact spec for a protoc plugin, with "grpc" as
        // the identifier, which can be referred to in the "plugins"
        // container of the "generateProtoTasks" closure.

        id("grpc") {
            artifact = libs.grpc.protocGenJava.get().toString()
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