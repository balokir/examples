package me.balokir.grpc_simple_streaming.server;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import me.balokir.grpc_simple_streaming.common.grpc.ClientMessage;
import me.balokir.grpc_simple_streaming.common.grpc.ServerMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Slf4j
@Component
public final class ClientConnectionsManager {
    private final CopyOnWriteArrayList<ClientConnectionsListener>  listeners = new CopyOnWriteArrayList<>();

    private final ConcurrentHashMap<StreamObserver<ClientMessage>, StreamObserver<ServerMessage>> clientToServerMap =
            new ConcurrentHashMap<>();

    public void addClientConnectionsListener(ClientConnectionsListener listener){
        listeners.add(listener);
    }

    public void removeClientConnectionsListener(ClientConnectionsListener listener){
        listeners.remove(listener);
    }

    private void notifyListeners(Consumer<ClientConnectionsListener> consumer){
        for (ClientConnectionsListener listener : listeners) {
            try{
                consumer.accept(listener);
            } catch (Exception e){
                log.error("An error occurred while calling the listener." , e);
            }
        }

    }

    public void registerClientConnection(StreamObserver<ClientMessage> inputStreamObserver,
                                         StreamObserver<ServerMessage> outputStreamObserver) {
        log.info("New gRPC client connected");
        clientToServerMap.put(inputStreamObserver, outputStreamObserver);
        notifyListeners((l)-> l.clientConnected(inputStreamObserver));
    }

    public void removeClientConnection(StreamObserver<ClientMessage> inputStreamObserver) {
        log.info("gRPC client disconnected");
        clientToServerMap.remove(inputStreamObserver);
        notifyListeners((l)-> l.clientDisconnected(inputStreamObserver));
    }


    public Collection<StreamObserver<ClientMessage>> inputObservers() {
        return Collections.unmodifiableCollection(new ArrayList<>(clientToServerMap.keySet()));
    }

    public Collection<StreamObserver<ServerMessage>> outputObservers() {
        return Collections.unmodifiableCollection(new ArrayList<>(clientToServerMap.values()));
    }

    public StreamObserver<ServerMessage> getOutputStreamObserver(StreamObserver<ClientMessage> inputStreamObserver) {
        return clientToServerMap.get(inputStreamObserver);
    }

}
