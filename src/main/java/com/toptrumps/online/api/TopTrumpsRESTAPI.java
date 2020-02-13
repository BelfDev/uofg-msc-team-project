package com.toptrumps.online.api;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.engine.GameEngine;
import com.toptrumps.core.engine.RoundOutcome;
import com.toptrumps.core.player.AIPlayer;
import com.toptrumps.core.player.Player;
import com.toptrumps.core.statistics.GameStateCollector;
import com.toptrumps.db.Statistics;
import com.toptrumps.online.api.request.*;
import com.toptrumps.online.api.response.InitialGameState;
import com.toptrumps.online.api.response.Outcome;
import com.toptrumps.online.api.response.StatisticsContent;
import com.toptrumps.online.configuration.TopTrumpsJSONConfiguration;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input
/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 *
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controled from a Web page.
 */
public class TopTrumpsRESTAPI {

    GameEngine gameEngine;

    /**
     * Contructor method for the REST API. This is called first. It provides
     * a TopTrumpsJSONConfiguration from which you can get the location of
     * the deck file and the number of AI players.
     *
     * @param conf
     */
    public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
        this.gameEngine = new GameEngine(conf.getDeckFile());
    }

    // ----------------------------------------------------
    // Add relevant API methods here
    // ----------------------------------------------------

    @POST
    @Path("/api/game")
    @Produces(MediaType.APPLICATION_JSON)
    public InitialGameState startNewGame(GamePreferences gamePreferences) {
        int numberOfOpponents = gamePreferences.getNumberOfOpponents();
        List<Player> players = gameEngine.startUp(numberOfOpponents);
        Player activePlayer = gameEngine.assignActivePlayer(players);
        // TODO: Double check if we are going to rely on this convention
        Player humanPlayer = players.get(0);
        List<Player> aiPlayers = players.subList(1, numberOfOpponents + 1);

        return new InitialGameState(numberOfOpponents, activePlayer.getId(), humanPlayer, aiPlayers);
    }

    @POST
    @Path("/api/outcome/human")
    @Produces(MediaType.APPLICATION_JSON)
    public Outcome generateRoundOutcome(HumanPlayerMove humanPlayerMove) {
        List<Player> players = humanPlayerMove.getPlayerStates().stream().map(PlayerState::toPlayer).collect(toList());
        List<Player> winners = gameEngine.getWinners(humanPlayerMove.getSelectedAttribute(), players);
        players.forEach(Player::removeTopCard);
        RoundOutcome roundOutcome = gameEngine.processRoundOutcome(winners, players);
        return new Outcome(roundOutcome);
    }

    @POST
    @Path("/api/outcome/ai")
    @Produces(MediaType.APPLICATION_JSON)
    public Outcome generateRoundOutcome(PlayerMove aiPlayerMove) {
        Player activeAIPlayer = aiPlayerMove.getActivePlayerState().toPlayer();
        List<Player> players = aiPlayerMove.getPlayerStates().stream().map(PlayerState::toPlayer).collect(toList());
        Attribute selectedAttribute = ((AIPlayer) activeAIPlayer).selectAttribute();
        List<Player> winners = gameEngine.getWinners(selectedAttribute, players);
        players.forEach(Player::removeTopCard);
        RoundOutcome roundOutcome = gameEngine.processRoundOutcome(winners, players);
        return new Outcome(roundOutcome, selectedAttribute);
    }

    @POST
    @Path("/api/statistics")
    @Produces(MediaType.APPLICATION_JSON)
    public void persistGameData(FinalGameState finalGameState) {
        GameStateCollector stateCollector = GameStateCollector.Builder.newInstance()
                .fromFinalGameState(finalGameState)
                .build();
        gameEngine.persistGameState(stateCollector);
    }

    @GET
    @Path("/api/statistics")
    @Produces(MediaType.APPLICATION_JSON)
    public StatisticsContent getStatisticsContent() {
        Statistics gameStatistics = new Statistics();
        return new StatisticsContent(gameStatistics);
    }

}
