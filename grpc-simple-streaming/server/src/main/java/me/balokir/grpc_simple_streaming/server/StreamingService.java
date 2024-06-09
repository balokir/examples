package me.balokir.grpc_simple_streaming.server;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.balokir.grpc_simple_streaming.common.grpc.ExampleStreamingServiceGrpc;
import me.balokir.grpc_simple_streaming.common.grpc.ClientMessage;
import me.balokir.grpc_simple_streaming.common.grpc.ServerMessage;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StreamingService extends ExampleStreamingServiceGrpc.ExampleStreamingServiceImplBase {
    private final ClientConnectionsManager connectionsManager;

    @Override
    public StreamObserver<ClientMessage> exampleGrpcStreaming(StreamObserver<ServerMessage> responseObserver) {
        ServerCallStreamObserver<ServerMessage> outputStreamObserver =
                (ServerCallStreamObserver<ServerMessage>) responseObserver;

        ServerInputStreamObserver inputStreamObserver =
                new ServerInputStreamObserver(connectionsManager, outputStreamObserver);
        connectionsManager.registerClientConnection(inputStreamObserver, outputStreamObserver);
        return inputStreamObserver;
    }

}
