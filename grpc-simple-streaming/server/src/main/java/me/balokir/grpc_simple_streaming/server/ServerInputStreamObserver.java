package me.balokir.grpc_simple_streaming.server;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import me.balokir.grpc_simple_streaming.common.CommandType;
import me.balokir.grpc_simple_streaming.common.grpc.ClientMessage;
import me.balokir.grpc_simple_streaming.common.grpc.ServerMessage;

@Slf4j
public class ServerInputStreamObserver implements StreamObserver<ClientMessage> {


    private final ClientConnectionsManager connectionsManager;
    private final ServerCallStreamObserver<ServerMessage> outputStreamObserver;

    public ServerInputStreamObserver(ClientConnectionsManager connectionsManager,
                                     ServerCallStreamObserver<ServerMessage> outputStreamObserver) {
        this.connectionsManager = connectionsManager;
        this.outputStreamObserver = outputStreamObserver;
    }

    @Override
    public void onNext(ClientMessage clientMessage) {
        log.info("onNext");

        CommandType commandType = CommandType.getById(clientMessage.getType());
        log.info("clientMessage {} {}", commandType, clientMessage.getCommand());
        if (commandType == CommandType.RESPONSE) {
            return;
        }

        if (commandType == CommandType.REQUEST) {
            ServerMessage command = ServerMessage.newBuilder()//
                                                 .setType(CommandType.RESPONSE.ordinal())//
                                                 .setCommand(clientMessage.getCommand() + " - processed")//
                                                 .build();
            outputStreamObserver.onNext(command);
////            outputStreamObserver.onCompleted();
//            outputStreamObserver.onError(
//                    Status.UNKNOWN.withDescription("Error handling request").
//                            withCause(new Exception()).asException());

        }

    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error {}", throwable.toString());
        log.debug("Error explanation:", throwable);
        connectionsManager.removeClientConnection(this);
    }

    @Override
    public void onCompleted() {
        connectionsManager.removeClientConnection(this);
    }
}
