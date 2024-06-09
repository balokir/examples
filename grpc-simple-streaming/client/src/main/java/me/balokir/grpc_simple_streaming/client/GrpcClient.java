package me.balokir.grpc_simple_streaming.client;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.balokir.grpc_simple_streaming.client.config.RootConfig;
import me.balokir.grpc_simple_streaming.client.config.ServerConfig;
import me.balokir.grpc_simple_streaming.common.CommandType;
import me.balokir.grpc_simple_streaming.common.grpc.ClientMessage;
import me.balokir.grpc_simple_streaming.common.grpc.ExampleStreamingServiceGrpc;
import me.balokir.grpc_simple_streaming.common.grpc.ServerMessage;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class GrpcClient {


    private enum LifeCycle {
        STARTED, STOPPED
    }

    private final RootConfig rootConfig;
    private ManagedChannel channel;
    private ExampleStreamingServiceGrpc.ExampleStreamingServiceStub stub;
    private volatile StreamObserver<ClientMessage> outputStreamObserver;
    private volatile StreamObserver<ServerMessage> inputStreamObserver;
    private volatile LifeCycle lifeCycle = LifeCycle.STOPPED;

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    synchronized public void start() {
        log.info("Start client");
        ServerConfig serverConfig = rootConfig.getServer();
        if (channel != null) {
            return;
        }
        channel = Grpc.newChannelBuilderForAddress(serverConfig.getHost(),//
                                                   serverConfig.getPort(), //
                                                   InsecureChannelCredentials.create())//
                      .build();
        stub = ExampleStreamingServiceGrpc.newStub(channel);
        addChanelStatusNotifier();

        log.info("Client started for {}:{}", serverConfig.getHost(), serverConfig.getPort());
    }

    synchronized public void stop() {
        if (channel == null) {
            return;
        }
        log.info("Stop client");

        try {
            channel.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            log.info("Client stopped");
        } catch (InterruptedException e) {
            log.error("Shutdown timeout exceeded");
        } finally {
            channel = null;
        }
    }

    private void addChanelStatusNotifier() {
        if (channel == null) {
            return;
        }

        ConnectivityState state = channel.getState(true);
        channel.notifyWhenStateChanged(state, this::addChanelStatusNotifier);
        log.info("Channel state {}", state);
        switch (state) {
            case READY -> {
                createObservers();
            }
            case TRANSIENT_FAILURE -> {
                destroyObservers();
            }
            default -> {
                // do nothing
            }
        }

    }

    private void createObservers() {
        if (lifeCycle == LifeCycle.STOPPED) {
            synchronized (this) {
                if (lifeCycle == LifeCycle.STOPPED) {
                    log.info("createObservers()");
                    inputStreamObserver = new ClientInputStreamObserver(this);
                    outputStreamObserver = stub.exampleGrpcStreaming(inputStreamObserver);
                    lifeCycle = LifeCycle.STARTED;
                }
            }
        }
    }

    private void destroyObservers() {
        if (lifeCycle == LifeCycle.STARTED) {
            synchronized (this) {
                if (lifeCycle == LifeCycle.STARTED) {
                    log.info("destroyObservers()");
                    lifeCycle = LifeCycle.STOPPED;
                    inputStreamObserver = null;
                    outputStreamObserver = null;
                }
            }
        }
    }

    public void streamOnCompleted() {
        // schedule reconnect
        destroyObservers();
        int delay = 30;
        log.info("Client is inactive, schedule reconnect after {} seconds", delay);
        executorService.schedule(() -> {
            log.info("Try reconnect...");
            createObservers();
        }, delay, TimeUnit.SECONDS);
    }

    public void streamOnError() {
        if (lifeCycle != LifeCycle.STARTED) {
            return;
        }
        // schedule reconnect on error
        destroyObservers();
        int delay = 30;
        log.info("There was a communication error with the server. Scheduled to reconnect in {} seconds.", delay);
        executorService.schedule(() -> {
            ConnectivityState state = channel.getState(true);
            if(state == ConnectivityState.READY) {
                log.info("Try reconnect...");
                createObservers();
            } else{
                log.info("Invalid channel state {}. Switched to channel life cycle recovery.", state);
            }
        }, delay, TimeUnit.SECONDS);
    }

    public void sendMessage() {
        if (lifeCycle != LifeCycle.STARTED) {
            return;
        }
        log.info("Send message...");
        ClientMessage command = ClientMessage.newBuilder()//
                                             .setType(CommandType.REQUEST.id())//
                                             .setCommand("message")//
                                             .build();
        outputStreamObserver.onNext(command);
    }
}
