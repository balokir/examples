package me.balokir.grpc_simple_streaming.server;

import io.grpc.stub.StreamObserver;
import me.balokir.grpc_simple_streaming.common.grpc.ClientMessage;

public interface ClientConnectionsListener {
    void clientConnected(StreamObserver<ClientMessage> inputStreamObserver);

    void clientDisconnected(StreamObserver<ClientMessage> inputStreamObserver);
}
