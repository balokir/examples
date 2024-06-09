package me.balokir.grpc_simple_streaming.server;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.balokir.grpc_simple_streaming.common.CommandType;
import me.balokir.grpc_simple_streaming.common.grpc.ServerMessage;
import me.balokir.grpc_simple_streaming.server.config.RootConfig;
import me.balokir.grpc_simple_streaming.server.config.ServerConfig;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


@Slf4j
@RequiredArgsConstructor
@Component
public class GrpcServer {
    private final RootConfig rootConfig;
    private final ClientConnectionsManager memoryCache;
    private final StreamingService streamingService;

    private Server grpcServer;
    private volatile boolean started;


    synchronized public void start() throws IOException {
        log.info("Start server");
        ServerConfig serverConfig = rootConfig.getServer();
        Integer port = serverConfig.getPort();
        grpcServer = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())//
                         .addService(streamingService)//
                         .build();
        grpcServer.start();
        started = true;
        log.info("Server started on port {}", port);
    }

    synchronized public void stop() throws InterruptedException {
        log.info("Stop started");
        started = false;
        if (grpcServer != null) {
            grpcServer.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            grpcServer = null;
        }
        log.info("Server stopped");
    }

    public void sendMessage() {
        if (!started) {
            return;
        }
        for (StreamObserver<ServerMessage> outputStreamObserver : memoryCache.outputObservers()) {
            log.info("Send messages to client -- start");
            for (int i = 0; i < 5; i++) {
                outputStreamObserver.onNext(ServerMessage.newBuilder()//
                                                         .setType(CommandType.REQUEST.id())//
                                                         .setCommand("Command " + i)//
                                                         .build());
            }
            log.info("Send messages to client -- finish");
        }
    }
}
