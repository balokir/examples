package me.balokir.examples.udp.simple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoClient {
    private final DatagramSocket socket;
    private final InetSocketAddress serverSocketAddress;

    public EchoClient(int port) throws Exception {
        socket = new DatagramSocket();
        log.info("Client LocalSocketAddress {}", socket.getLocalSocketAddress());
        serverSocketAddress = new InetSocketAddress("localhost", port);
    }

    public String sendEcho(String msg) {
        byte[] buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, serverSocketAddress);
        try {
            log.info("Client send '{}' to {}", msg, packet.getSocketAddress());
            socket.send(packet);
            packet = new DatagramPacket(buf, buf.length);

            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());

            log.info("Client received '{}' from {}", received, packet.getSocketAddress());
            return received;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        socket.close();
    }
}
