package me.balokir.grpc_simple_streaming.client;

import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import lombok.extern.slf4j.Slf4j;
import me.balokir.grpc_simple_streaming.common.CommandType;
import me.balokir.grpc_simple_streaming.common.grpc.ClientMessage;
import me.balokir.grpc_simple_streaming.common.grpc.ServerMessage;

@Slf4j
public class ClientInputStreamObserver implements ClientResponseObserver<ClientMessage, ServerMessage> {

    private final GrpcClient grpcClient;
    private ClientCallStreamObserver<ClientMessage> outputStreamObserver;

    public ClientInputStreamObserver(GrpcClient grpcClient){
        this.grpcClient = grpcClient;
    }
    @Override
    public void beforeStart(ClientCallStreamObserver<ClientMessage> requestStream) {
        outputStreamObserver = requestStream;
    }

    @Override
    public void onNext(ServerMessage serverMessage) {
        CommandType commandType = CommandType.getById(serverMessage.getType());
        log.info("serverMessage {} {}", commandType, serverMessage.getCommand());
        if (commandType == CommandType.RESPONSE) {
            return;
        }

        if (commandType == CommandType.REQUEST) {
            ClientMessage command = ClientMessage.newBuilder()//
                                                 .setType(CommandType.RESPONSE.ordinal())//
                                                 .setCommand(serverMessage.getCommand() + " - processed")//
                                                 .build();
            outputStreamObserver.onNext(command);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error {}", throwable.toString());
        log.info("Error explanation:", throwable);
        grpcClient.streamOnError();
    }

    @Override
    public void onCompleted() {
        log.info("Stream onCompleted");
        grpcClient.streamOnCompleted();
    }
}
