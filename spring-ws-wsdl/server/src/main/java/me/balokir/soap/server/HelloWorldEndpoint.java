package me.balokir.soap.server;

import me.balokir.types.helloworld.Greeting;
import me.balokir.types.helloworld.ObjectFactory;
import me.balokir.types.helloworld.Person;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Endpoint
public class HelloWorldEndpoint {

    @PayloadRoot(namespace = "http://balokir.me/types/helloworld", localPart = "person")
    @ResponsePayload
    public Greeting sayHello(@RequestPayload Person request) {
        log.info("Endpoint received person[firstName={},lastName={}]", request.getFirstName(), request.getLastName());

        String greeting = "Hello " + request.getFirstName() + " " + request.getLastName() + "!";

        ObjectFactory factory = new ObjectFactory();
        Greeting response = factory.createGreeting();
        response.setGreeting(greeting);

        log.info("Endpoint sending greeting='{}'", response.getGreeting());
        return response;
    }
}