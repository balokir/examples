package me.balokir.examples.udp.simple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoServer extends Thread {

    private final DatagramSocket socket;

    public EchoServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
        log.info("Server LocalSocketAddress {}", socket.getLocalSocketAddress());
    }

    public int getPort() {
        return socket.getLocalPort();
    }

    @Override
    public void run() {
        final byte[] buf = new byte[256];

        while (true) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String received = new String(packet.getData(), 0, packet.getLength());
            SocketAddress address = packet.getSocketAddress();

            log.info("Server received '{}' from {}", received, address);

            packet = new DatagramPacket(buf, buf.length, address);

            try {
                log.info("Server send '{}' to {}", received, address);
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (received.equals("end")) {
                break;
            }
        }
        socket.close();
    }
}
