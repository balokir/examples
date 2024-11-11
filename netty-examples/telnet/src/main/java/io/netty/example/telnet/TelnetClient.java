/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.example.telnet;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.example.util.ServerUtil;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;

import lombok.extern.slf4j.Slf4j;

/**
 * Simplistic telnet client.
 */
@Slf4j
public final class TelnetClient {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8992" : "8023"));

    public static void main(String[] args) throws Exception {
        // Configure SSL.
        final SslContext sslCtx = ServerUtil.buildSslContext();

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            for (; ; ) {
                Bootstrap b = new Bootstrap()//
                    .option(ChannelOption.TCP_NODELAY CONNECT_TIMEOUT_MILLIS, 10000)//
                    .group(group)//
                    .channel(NioSocketChannel.class)//
                    .handler(new LoggingHandler(LogLevel.INFO))//
                    .handler(new TelnetClientInitializer(sslCtx));

                // Start the connection attempt.
                ChannelFuture f = b.connect(HOST, PORT)//
                    .addListener((ChannelFuture cf) -> {
                        if (!cf.isSuccess()) {
                            log.error("Connection error", cf.cause());
                            log.info("Wait for reconnect");
                            Thread.sleep(10_000);
                        }
                    }).awaitUninterruptibly();

                if (f.isCancelled()) {
                    log.info("Connection attempt cancelled by user");
                    return;
                } else if (!f.isSuccess()) {
                    log.error("Connection error", f.cause());
                    continue;
                } else {
                    log.info("Connection established successfully");
                }

                Channel ch = f.channel();
                // Read commands from the stdin.
                ChannelFuture lastWriteFuture = null;
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                for (; ; ) {
                    if(!ch.isActive()){
                        break;
                    }
                    String line = in.readLine();
                    if (line == null) {
                        break;
                    }

                    // Sends the received line to the server.
                    lastWriteFuture = ch.writeAndFlush(line + "\r\n");

                    // If user typed the 'bye' command, wait until the server closes
                    // the connection.
                    if ("bye".equals(line.toLowerCase())) {
                        ch.closeFuture().sync();
                        break;
                    }
                }

                // Wait until all messages are flushed before closing the channel.
                if (lastWriteFuture != null) {
                    lastWriteFuture.sync();
                }
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
