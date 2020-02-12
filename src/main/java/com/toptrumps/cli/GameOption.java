package com.toptrumps.cli;

enum GameOption {
    GAME_MODE("f"),
    STATISTICS_MODE("s"),
    QUIT("q"),
    NOT_VALID("NOT_VALID");

    private String flagValue;

    GameOption(String flagValue) {
        this.flagValue = flagValue;
    }

    public String getFlagValue() {
        return flagValue;
    }

    public boolean equals(String input) {
        return input.equalsIgnoreCase(flagValue);
    }

    public static boolean contains(String input) {
        for (GameOption option : GameOption.values()) {
            if (option.getFlagValue().equals(input)) {
                return true;
            }
        }
        return false;
    }

    public static GameOption fromInput(String input) {
        for (GameOption option : GameOption.values()) {
            if (option.getFlagValue().equals(input)) {
                return option;
            }
        }
        return NOT_VALID;
    }

}
