/**
 * Controller for game screen
 */

const GameController = (($) => {
    /** VARIABLES AND CONSTANTS */

    let model;
    let view;

    // Selectors
    const quitGameButtonSelector = ".js-quit-button";

    // Modals
    const modalIDs = {
        draw: "DRAW",
        victory: "VICTORY",
        gameOver: "GAME_OVER",
        quit: "QUIT"
    };

    // Animations
    let timerBase = window.APP.TIMER_BASE;
    let activePlayerTimer = window.APP.ACTIVE_PLAYER_TIMER;


    /** METHODS */

    /**
     * Game initialization
     */
    const init = (m, v) => {
        model = m;
        view = v;

        bindEvents();

        const numberOfOpponents = getNumberOfOpponents();

        // Send request to get game data
        const request = NetworkHelper.makeRequest("game", { numberOfOpponents });
        request.then(response => {
            startNewGame(response);
        });
    };

    /**
     * Events binding
     */
    const bindEvents = () => {
        $(quitGameButtonSelector).on("click", () => {
            view.showModal(modalIDs.quit, false, false)
        });
    };

    /**
     * Get number of opponents from the URL parameter
     * @returns {string}
     */
    const getNumberOfOpponents = () => {
        const numberOfOpponents = NetworkHelper.getCurrentURLParameterValue("numberOfOpponents");

        // If no value provided - redirect to the selection page
        if (!numberOfOpponents) {
            window.location.replace(window.APP.BASE_URL);
            return;
        }

        return numberOfOpponents
    };

    /**
     * Starts new game
     * @param gameData
     */
    const startNewGame = async gameData => {
        // Set game data
        model.init(gameData);
        StatsModel.init(model.getPlayersList());

        // After the interface is loaded and adjusted - show ui
        view.createPlayers(gameData.humanPlayer, gameData.aiPlayers);
        view.showUI();

        await view.delay(timerBase * 3);

        startNewRound(true);
    };

    /**
     * Starts new round
     * @param newGame boolean
     */
    const startNewRound = async newGame => {
        if (!newGame) {
            model.resetCardsOnTable();
            view.resetRound(model.getPlayersList());
        }

        await view.delay(timerBase * 2);

        // No need to run that if human player is defeated
        if (!model.getIsHumanPlayerDefeated()) {
            const humanPlayerID = model.getHumanPlayerID();
            const topCard = model.getTopCard(humanPlayerID);
            view.showCard(humanPlayerID, topCard);
            model.layCardOnTable(humanPlayerID, topCard);
        }

        model.putOpponentsCardsOnTable();

        view.displayActivePlayer(model.getActivePlayerID());

        await view.delay(activePlayerTimer);

        await startAttributeSelection();
    };

    /**
     * Starts attribute selection phase
     */
    const startAttributeSelection = async () => {
        if (model.getActivePlayerID() === 0) {
            view.showMessage("&nbsp;&nbsp;&nbsp;Your turn. Choose an attribute from&nbsp;&nbsp;&nbsp;&nbsp;your card");
            view.enableAttributeSelection(model.setActiveAttribute, model.getHumanPlayerID(), onEndTurn);
        } else {
            view.showMessage(`It is AI turn. Active player - ${model.getPlayerName(model.getActivePlayerID())}`);
            view.disableAttributeSelection(model.getHumanPlayerID());

            if (!window.APP.TEST_MODE) {
                await view.delay(timerBase * 2);
            }

            // Get chosen attribute from the server
            const response = await getChosenAttribute();

            model.setActiveAttribute(response.selectedAttribute.name, response.selectedAttribute.value);

            if (window.APP.TEST_MODE) {
                startRoundConclusion(response)
            } else {
                Countdown.run(() => {
                    startRoundConclusion(response)
                });
            }
        }
    };

    /**
     * Prepares end of round
     */
    const onEndTurn = async () => {
        if (model.getActiveAttribute()) {
            const response = await setChosenAttribute();

            // Disable end turn button
            view.unBindEndTurnEvent(onEndTurn);

            Countdown.run(() => {
                startRoundConclusion(response)
            });
        }
    };

    /**
     * Sends request to set active attribute and get response from server with
     * calculated winner
     * @returns {Promise<jqXHR>}
     */
    const setChosenAttribute = async () => {
        const dataset = model.prepareDataset(model.getCardsOnTable());
        return NetworkHelper.makeRequest('outcome/human', {
            selectedAttribute: model.getActiveAttribute(),
            activePlayerId: model.getActivePlayerID(),
            players: dataset
        })
    };

    /**
     * Sends request to get active attribute and winner
     * @returns {Promise<jqXHR>}
     */
    const getChosenAttribute = async () => {
        const dataset = model.prepareDataset(model.getCardsOnTable());
        return NetworkHelper.makeRequest('outcome/ai', {
            activePlayerId: model.getActivePlayerID(),
            players: dataset
        })
    };

    /**
     * Starts round conclusion phase
     * @param response
     */
    const startRoundConclusion = async response => {
        view.showOpponentsCards(model.getCardsOnTable());
        view.showMessage(`Attribute ${model.getActiveAttribute().name} selected`);

        if (window.APP.TEST_MODE) {
            startRoundOutcome(response);
        } else {
            await view.delay(timerBase * 3);

            startRoundOutcome(response);
        }

    };

    /**
     * Starts round outcome phase
     * @param response
     */
    const startRoundOutcome = response => {
        if (response.result === "DRAW") {
            StatsModel.setWinner(null);
        } else if (response.result === "VICTORY") {
            StatsModel.setWinner(response.winner);
            model.distributeCards(response.winner.id);
        }

        // If there are defeated players - display them
        const removedPlayerIds = response.removedPlayerIds;
        if (removedPlayerIds.length > 0) {
            model.updateRemovedPlayers(removedPlayerIds);
            view.displayRemovedPlayers(removedPlayerIds);
        }

        showRoundOutcome(response);

    };

    /**
     * Shows round outcome
     * @param response
     */
    const showRoundOutcome = async response => {
        view.highlightAttribute(model.getActiveAttribute());
        view.clearPlayerStates();
        await view.delay(timerBase * 1);

        if (response.result === "DRAW") {
            view.showMessage("It is a draw");
            const commonPileCount = model.updateCommonPile();
            view.updateCommonPileIndicator(commonPileCount);
            view.displayDraw();
        } else if (response.result === "VICTORY") {
            view.showMessage(`${response.winner.name} is the winner`);
            model.resetCommonPile();
            view.updateCommonPileIndicator(0);
            model.setActivePlayer(response.winner.id);
            view.displayWinnerPlayer(response.winner.id);
        }

        // Update deck count for every player
        const players = model.getPlayers();
        $.each(players, (i, player) => {
            view.updateDeckCount(player.id, player.deck.length);
        });

        await view.delay(timerBase * 2);

        if (!model.isEndGame()) {
            startNewRound(false);
        } else {
            showGameOutcome(response);
        }
    };

    /**
     * Shows game outcome with stats
     */
    const showGameOutcome = () => {
        const title = "Game over!";

        const stats = StatsModel.getGameStats();
        const hint = view.getStatsMarkup(stats);

        view.showModal(modalIDs["gameOver"], title, hint);
        saveGameStats();
    };

    /**
     * Sends request to save game statistics
     */
    const saveGameStats = () => {
        GameView.showLoader();
        const gameData = StatsModel.getGameStats();
        NetworkHelper.makeRequest(`statistics`, gameData).then(() => {
            GameView.removeLoader();
        });
    };


    /** RUN AFTER PAGE IS LOADED **/
    $(function() {
        GameController.init(GameModel, GameView);
    });


    /** EXPOSE PUBLIC METHODS **/

    return {
        init
    }
})(jQuery);
