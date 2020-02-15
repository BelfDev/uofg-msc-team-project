const Game = (($) => {
    /** VARIABLES AND CONSTANTS */
    const quitGameButtonSelector = ".js-quit-button";
    const modalIDs = {
        draw: "DRAW",
        victory: "VICTORY",
        gameOver: "GAME_OVER",
        quit: "QUIT"
    };

    let timerBase = window.APP.TIMER_BASE;
    let nextRoundTimer = window.APP.NEXT_ROUND_TIMER;
    let activePlayerTimer = window.APP.ACTIVE_PLAYER_TIMER;

    // Game state variables
    let numberOfOpponents;
    let activePlayerID;
    let humanPlayerID;
    let cardsOnTable = [];
    let activeAttribute;
    let commonPile = [];
    let removedPlayerIDs = [];
    let isHumanPlayerDefeated = false;

    /** METHODS */

    const init = () => {
        bindEvents();

        const numberOfOpponents = getNumberOfOpponents();

        const request = NetworkHelper.makeRequest("api/game", { numberOfOpponents });
        Logger.output("Number of opponents", "init", numberOfOpponents);
        request.then(response => {
            startNewGame(response);
        });
    };

    const bindEvents = () => {
        $(quitGameButtonSelector).on("click", () => {
            DOMHelper.showModal(modalIDs.quit, false, false)
        });
    };

    const onNextRound = async () => {
        resetRoundData();
        await DOMHelper.delay(nextRoundTimer);
        startNewRound();
    };

    const onEndTurn = async () => {
        if (activeAttribute) {
            const response = await setChosenAttribute();
            DOMHelper.unBindEndTurnEvent(onEndTurn);
            Countdown.run(() => {
                startRoundConclusion(response)
            });
        }
    };

    const resetRoundData = () => {
        const players = PlayerModel.getPlayersList();
        $.each(players, (i, player) => {
            DOMHelper.resetCard(player.id);
        });

        DOMHelper.resetAttributeHighlight();
        DOMHelper.clearPlayerStates();
        Modal.closeActiveModal();

        cardsOnTable = [];

        Logger.output("Cards on table after reset", "resetRoundData", cardsOnTable);
    };

    const getNumberOfOpponents = () => {
        const numberOfOpponents = NetworkHelper.getCurrentURLParameterValue("numberOfOpponents");

        if (!numberOfOpponents) {
            window.location.replace("/toptrumps/");
            return;
        }

        return numberOfOpponents
    };

    const startNewGame = async gameData => {
        /* After the interface is loaded and adjusted - show ui */
        DOMHelper.showUI();

        // Set game data
        numberOfOpponents = gameData.numberOfOpponents;
        activePlayerID = gameData.activePlayerId;
        humanPlayerID = gameData.humanPlayer.id;

        Logger.output("Active player ID", "startNewGame", activePlayerID);
        Logger.output("Human player ID", "startNewGame", humanPlayerID);

        createPlayers(gameData.humanPlayer, gameData.aiPlayers);
        const players = assignDecks(gameData.humanPlayer, gameData.aiPlayers);

        Logger.output("Players list", "startNewGame", players);
        PlayerModel.init(players);
        StatsHelper.init(PlayerModel.getPlayersList());

        await DOMHelper.delay(timerBase * 3);
        startNewRound();
    };

    const createPlayers = (humanPlayer, aiPlayers) => {
        DOMHelper.updateDeckCount(humanPlayer.id, humanPlayer.deck.length);

        aiPlayers.forEach(player => {
            DOMHelper.renderPlayer(player);
        });
    };

    const assignDecks = (humanPlayer, aiPlayers) => {
        const players = [];

        players[humanPlayerID] = {
            id: humanPlayer.id,
            name: humanPlayer.name.replace("_", " "),
            deck: humanPlayer.deck
        };

        aiPlayers.forEach((player, i) => {
            players[i + 1] = {
                id: player.id,
                name: player.name.replace("_", " "),
                deck: player.deck
            };
        });

        return players;
    };

    const startNewRound = async () => {
        Logger.output("Cards before starting new round", "startNewRound", PlayerModel.getPlayersCardCount());

        if (!isHumanPlayerDefeated) {
            const topCard = PlayerModel.getTopCard(humanPlayerID);
            DOMHelper.showCard(humanPlayerID, topCard);
            layCardOnTable(humanPlayerID, topCard);
        }

        const aiPlayers = PlayerModel.getAiPlayersTopCards();

        $.each(aiPlayers, (playerID, card) => {
            layCardOnTable(playerID, card);
        });

        Logger.output("Cards on table", "startNewRound", cardsOnTable);

        DOMHelper.displayActivePlayer(activePlayerID);
        await DOMHelper.delay(activePlayerTimer);

        await startAttributeSelection();
    };

    const layCardOnTable = (playerID, card) => {
        cardsOnTable.push({ playerID, card });
    };

    const startAttributeSelection = async () => {
        if (activePlayerID === 0) {
            DOMHelper.showMessage("&nbsp;&nbsp;&nbsp;Your turn. Choose an attribute from&nbsp;&nbsp;&nbsp;&nbsp;your card");
            DOMHelper.enableAttributeSelection(onAttributeSelected, humanPlayerID, onEndTurn);
        } else {
            DOMHelper.showMessage(`It is AI turn. Active player - ${PlayerModel.getPlayerName(activePlayerID)}`);
            DOMHelper.disableAttributeSelection(humanPlayerID);

            if (!window.APP.TEST_MODE) {
                await DOMHelper.delay(timerBase * 2);
            }

            const response = await getChosenAttribute();
            Logger.output("Received AI attribute", "startAttributeSelection", response.selectedAttribute);
            onAttributeSelected(response.selectedAttribute.name, response.selectedAttribute.value);

            if (window.APP.TEST_MODE) {
                startRoundConclusion(response)
            } else {
                Countdown.run(() => {
                    startRoundConclusion(response)
                });
            }
        }
    };

    const onAttributeSelected = (name, value) => {
        activeAttribute = { name, value };
    };

    const startRoundConclusion = async response => {
        DOMHelper.showOpponentsCards(cardsOnTable);

        if (window.APP.TEST_MODE) {
            showWinningConditions(response);
            startRoundOutcome(response);
        } else {
            await DOMHelper.delay(timerBase * 3);
            showWinningConditions(response);

            await DOMHelper.delay(timerBase * 3);
            startRoundOutcome(response);
        }

    };

    const showWinningConditions = response => {
        DOMHelper.showMessage(`Attribute ${activeAttribute.name} selected`);
        DOMHelper.highlightAttribute(activeAttribute);

        if (response.result === "VICTORY") {
            DOMHelper.clearPlayerStates();
            DOMHelper.displayWinnerPlayer(response.winner.id);
        }
    }

    const startRoundOutcome = response => {
        Logger.output("Round outcome", "startRoundOutcome", response.result);
        if (response.result === "DRAW") {
            StatsHelper.incrementRoundNumber(null);
            updateCommonPile();
        } else if (response.result === "VICTORY") {
            StatsHelper.incrementRoundNumber(response.winner);
            distributeCards(response.winner.id, cardsOnTable);
        }

        const players = PlayerModel.getPlayers();
        $.each(players, (i, player) => {
            DOMHelper.updateDeckCount(player.id, player.deck.length);
        });

        if (response.removedPlayerIds.length > 0) {
            updateRemovedPlayers(response.removedPlayerIds);
            displayRemovedPlayers();
        }

        if (!isEndGame()) {
            showRoundOutcome(response);
        } else {
            showGameOutcome(response);
        }
    };

    const updateRemovedPlayers = playerIDs => {
        playerIDs.forEach(playerID => {
            if (removedPlayerIDs.indexOf(playerID) === -1)
                removedPlayerIDs.push(playerID);
        })
        Logger.output("List of removed players updated", "updateRemovedPlayers", removedPlayerIDs);

    };

    const displayRemovedPlayers = () => {
        removedPlayerIDs.forEach(playerID => {
            DOMHelper.setPlayerStateToDefeated(playerID);
            PlayerModel.removePlayer(playerID);
            if (playerID === 0) isHumanPlayerDefeated = true;
        });
    };

    const updateCommonPile = () => {
        cardsOnTable.forEach(data => {
            commonPile.push(data.card);
        });
        cardsOnTable = [];
        Logger.output("Cards on table", "updateCommonPile", cardsOnTable);
        Logger.output("New common pile count", "updateCommonPile", commonPile.length);
        DOMHelper.updateCommonPileIndicator(commonPile.length);
    };

    const resetCommonPile = () => {
        commonPile = [];
        Logger.output("New common pile count", "resetCommonPile", commonPile.length);
        DOMHelper.updateCommonPileIndicator(0);
    };

    const isEndGame = () => {
        if (removedPlayerIDs.length === numberOfOpponents) return true;
    };

    const setChosenAttribute = async () => {
        const dataset = PlayerModel.prepareDataset(cardsOnTable);
        return NetworkHelper.makeRequest('api/outcome/human', {
            selectedAttribute: activeAttribute,
            activePlayerId: activePlayerID,
            players: dataset
        })
    };

    const getChosenAttribute = async () => {
        const dataset = PlayerModel.prepareDataset(cardsOnTable);
        return NetworkHelper.makeRequest('api/outcome/ai', {
            activePlayerId: activePlayerID,
            players: dataset
        })
    };

    const showRoundOutcome = async response => {
        if (response.result === "DRAW") {
            DOMHelper.showMessage("It is a draw");
            DOMHelper.displayDraw();
        } else if (response.result === "VICTORY") {
            DOMHelper.showMessage(`${response.winner.name} is the winner`);
            showWinner(response.winner.id);
        }

        await DOMHelper.delay(nextRoundTimer);
        onNextRound();
    };

    const showWinner = playerID => {
        activePlayerID = playerID;
        DOMHelper.displayWinnerPlayer(playerID);
    };

    const distributeCards = (playerID, cardsOnTable) => {
        const cards = commonPile;
        cardsOnTable.forEach(data => {
            cards.push(data.card);
        });

        Logger.output("Cards for distribution:", "distributeCards", cards);
        Logger.output("Player receiving cards", "distributeCards", playerID);

        PlayerModel.passCardsToPlayerByID(playerID, cards);

        resetCommonPile();
    };

    const showGameOutcome = () => {
        const title = "Game over!";

        const stats = StatsHelper.getGameStats();
        const hint = DOMHelper.getStatsMarkup(stats);

        Logger.output("End of game stats", "showGameOutcome", stats);

        DOMHelper.showModal(modalIDs["gameOver"], title, hint);
        saveGameStats();
    };

    const saveGameStats = () => {
        const gameData = StatsHelper.getGameStats();
        NetworkHelper.makeRequest(`api/statistics`, gameData);
    };

    return {
        init
    }
})(jQuery);