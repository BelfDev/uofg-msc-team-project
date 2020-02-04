package com.toptrumps.core.player;

public class NoSelectionException extends RuntimeException {

    public NoSelectionException() {
        super("No attribute selection detected", new Throwable());
    }

}
