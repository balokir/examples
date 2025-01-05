plugins {
    id("buildlogic.java-library-conventions")
    alias(libs.plugins.wsdl2java)
}

wsdl2java {
    useJakarta.set(false)
//    cxfVersion.set("3.6.4")
}


dependencies{
//    implementation("jakarta.xml.ws:jakarta.xml.ws-api:2.3.3")
//    api("jakarta.xml.ws:jakarta.xml.ws-api:2.3.3")
}

