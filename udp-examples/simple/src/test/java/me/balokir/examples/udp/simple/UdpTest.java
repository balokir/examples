package me.balokir.examples.udp.simple;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UdpTest {
    EchoClient client;

    @BeforeEach
    public void setup() throws Exception {
        EchoServer echoServer = new EchoServer(0);
        echoServer.start();
        client = new EchoClient(echoServer.getPort());
    }
    @AfterEach
    public void tearDown() {
        client.sendEcho("end");
        client.close();
    }

    @Test
    public void whenCanSendAndReceivePacket_thenCorrect() {
        String echo = client.sendEcho("hello server");
        assertEquals("hello server", echo);
        echo = client.sendEcho("server is working");
        assertNotEquals("hello server", echo);
    }

}
