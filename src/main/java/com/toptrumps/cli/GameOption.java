package com.toptrumps.cli;

enum GameOption {
    GAME_MODE("f"),
    STATISTICS_MODE("s"),
    QUIT("q");

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
            return option.getFlagValue().equals(input);
        }
        return false;
    }

}
