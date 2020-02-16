package com.toptrumps.core.engine;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.player.Player;
import com.toptrumps.core.utils.ResourceLoader;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.toptrumps.core.engine.RoundOutcome.Result.*;
import static java.util.stream.Collectors.toCollection;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class GameEngineTest {
    
    private GameEngine gameEngine;
    private int numberOfOpponents;
    private ArrayList<Player> players;

    @BeforeAll
    void oneTimeSetUp() {
        String fileName = ResourceLoader.getDeckFileName();
        this.gameEngine = new GameEngine(fileName);
        numberOfOpponents = 4;
    }

    @BeforeEach
    void setUp() {
        this.players = gameEngine.startUp(numberOfOpponents);
    }

    @Test
    @DisplayName("assignActivePlayer should return an active player")
    void assignActivePlayer_PlayerList_RandomActivePlayer() {
        Player activePlayer = gameEngine.assignActivePlayer(players);
        assertTrue(activePlayer.isActive());
    }

    @Test
    @DisplayName("startUp creates opponent AI players")
    void startUp_NumberOfOpponents_CreateAIPlayers() {
        List<Player> aiPlayers = players.subList(1, players.size());
        assertTrue(aiPlayers.stream().allMatch(Player::isAIPlayer));
    }

    @Test
    @DisplayName("startUp creates one human player")
    void startUp_NumberOfOpponents_CreateHumanPlayer() {
        List<Player> humanPlayers = players.stream().filter(p -> !p.isAIPlayer()).collect(Collectors.toList());
        assertEquals(1, humanPlayers.size());
    }

    @Test
    @DisplayName("startUp creates opponent players plus one human player")
    void startUp_NumberOfOpponents_CreatePlayers() {
        assertEquals(numberOfOpponents + 1, players.size());
    }

    @Test
    @DisplayName("startUp distributes decks to all players")
    void startUp_NumberOfOpponents_AssignDecks() {
        assertTrue(players.stream().noneMatch(p -> p.getDeck().isEmpty()));
    }

    @Test
    @DisplayName("getWinners should return a list of players who possess the highest selected attribute value")
    void getWinners_SelectedAttributeAndPlayerList_WinningPlayersList() {
        ArrayList<Attribute> commonAttributes = new ArrayList<Attribute>() {{
            add(new Attribute("X", 1));
            add(new Attribute("Y", 5));
        }};

        ArrayList<Attribute> winningAttributes = new ArrayList<Attribute>() {{
            add(new Attribute("X", 9));
            add(new Attribute("Y", 5));
        }};

        ArrayList<Card> losingDeck = new ArrayList<Card>() {{
            add(new Card("Loser", commonAttributes));
        }};

        ArrayList<Card> winningDeck = new ArrayList<Card>() {{
            add(new Card("Winner", winningAttributes));
        }};

        Player alyson = new Player(0, "Alyson");
        Player barbara = new Player(1, "Barbara");
        Player rex = new Player(2, "Rex");
        Player strogonoff = new Player(3, "Strogonoff");

        alyson.setDeck(losingDeck);
        barbara.setDeck(losingDeck);
        rex.setDeck(winningDeck);
        strogonoff.setDeck(winningDeck);

        ArrayList<Player> roundPlayers = new ArrayList<Player>() {{
            add(alyson);
            add(barbara);
            add(rex);
            add(strogonoff);
        }};

        Attribute selectedAttribute = winningAttributes.get(0);

        assertEquals(2, gameEngine.getWinners(selectedAttribute, roundPlayers).size());
    }

    @Test
    @DisplayName("getShuffledRoundCards should return a shuffled list of top cards")
    void getShuffledRoundCards_RoundPlayers_ShuffledCardList() {
        List<Card> roundCards = players.stream().map(Player::getTopCard).collect(toCollection(ArrayList::new));
        List<Card> intactRoundCards = new ArrayList<>(roundCards);
        roundCards = gameEngine.getShuffledRoundCards(players);
        assertNotEquals(intactRoundCards, roundCards);
    }

    @Test
    @DisplayName("processRoundOutcome should return round outcome VICTORY result")
    void processRoundOutcome_WinningPlayersListAndRoundPlayerList_RoundOutcomeResultVictory() {
        ArrayList<Player> testPlayers = new ArrayList<>(players);
        ArrayList<Player> winners = new ArrayList<Player>() {{
            add(testPlayers.get(0));
        }};

        RoundOutcome outcome = gameEngine.processRoundOutcome(winners, testPlayers);
        assertEquals(VICTORY, outcome.getResult());
    }

    @Test
    @DisplayName("processRoundOutcome should return round outcome DRAW result")
    void processRoundOutcome_WinningPlayersListAndRoundPlayerList_RoundOutcomeResultDraw() {
        ArrayList<Player> testPlayers = new ArrayList<>(players);
        ArrayList<Player> winners = new ArrayList<Player>() {{
            add(testPlayers.get(0));
            add(testPlayers.get(1));
        }};

        RoundOutcome outcome = gameEngine.processRoundOutcome(winners, testPlayers);
        assertEquals(DRAW, outcome.getResult());
    }

    @Test
    @DisplayName("processRoundOutcome should return round outcome GAME_OVER result")
    void processRoundOutcome_WinningPlayersListAndRoundPlayerList_RoundOutcomeResultGameOver() {
        ArrayList<Player> testPlayers = new ArrayList<Player>() {{
            add(players.get(0));
        }};

        ArrayList<Player> winners = new ArrayList<Player>() {{
            add(testPlayers.get(0));
        }};

        RoundOutcome outcome = gameEngine.processRoundOutcome(winners, testPlayers);
        assertEquals(GAME_OVER, outcome.getResult());
    }

}
