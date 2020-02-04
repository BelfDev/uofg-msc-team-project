package com.toptrumps.core.player;

public class IncompatibleComparisonException extends RuntimeException {

    public IncompatibleComparisonException() {
        super("Players can only compare attributes with the same name", new Throwable());
    }
}
