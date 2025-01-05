package me.balokir.soap.client;

import me.balokir.types.helloworld.Greeting;
import me.balokir.types.helloworld.ObjectFactory;
import me.balokir.types.helloworld.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HelloWorldClient {


    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public String sayHello(String firstName, String lastName) {
        ObjectFactory factory = new ObjectFactory();
        Person person = factory.createPerson();

        person.setFirstName(firstName);
        person.setLastName(lastName);

        log.info("Client sending person[firstName={},lastName={}]", person.getFirstName(),
            person.getLastName());

        Greeting greeting = (Greeting) webServiceTemplate.marshalSendAndReceive(person);

        log.info("Client received greeting='{}'", greeting.getGreeting());
        return greeting.getGreeting();
    }
}