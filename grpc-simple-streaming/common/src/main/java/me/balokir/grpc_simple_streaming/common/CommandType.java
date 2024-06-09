package me.balokir.grpc_simple_streaming.common;

public enum CommandType {
    REQUEST, RESPONSE;

    public int id() {
        return ordinal();
    }

    public static CommandType getById(int id) {
        return values()[id];
    }

}
