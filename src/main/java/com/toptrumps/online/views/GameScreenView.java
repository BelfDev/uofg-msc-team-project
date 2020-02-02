package com.toptrumps.online.views;

import io.dropwizard.views.View;

import java.util.ArrayList;
import java.util.List;
import com.toptrumps.core.Player;

/**
 * Each HTML page that is specified in GameWebPagesResource first needs a class that extends
 * View, which is Dropwizard's internal representation of the page. This then points to a
 * separate file found in the resource directory that contains the actual HTML/Javascript.
 * <p>
 * The HTML/Javascript page for this View can be found in resources/dwViews/GameScreen.ftl
 * <p>
 * You do not need to edit this class. You will need to complete GameScreen.ftl.
 * <p>
 * Note: The HTML/Javascript file is actially a freemarker file (https://freemarker.apache.org/),
 * however we do not expect you to use the additional functionality that freemarker provides.
 */
public class GameScreenView extends View {
    private final List<String> players;

    public GameScreenView(List<String> players) {
        super("GameScreen.ftl");
        this.players = players;
    }

    public List<String> getPlayers() {
        return players;
    }
}
