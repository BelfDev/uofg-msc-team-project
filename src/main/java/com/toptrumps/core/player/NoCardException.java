package com.toptrumps.core.player;

public class NoCardException extends RuntimeException {

    public NoCardException() {
        super("No cards were found", new Throwable());
    }
}
