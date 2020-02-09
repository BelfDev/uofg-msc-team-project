const Game = (($) => {
    /** VARIABLES AND CONSTANTS */
    const newGameButtonSelector = ".js-modal-new-game-button";
    const opponentsCountSelector = ".js-opponents-count";
    const modalIDs = {
        requestOpponents: "ASK_FOR_NUMBER_OF_OPPONENTS",
        draw: "DRAW",
        victory: "VICTORY",
        gameOver: "GAME_OVER"
    };

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
        /* After the interface is loaded and adjusted - show ui */
        DOMHelper.showUI();

        bindEvents();

        requestNumberOfOpponents();
    };

    const bindEvents = () => {
        $(newGameButtonSelector).on("click", () => {
            const numberOfOpponents = $(opponentsCountSelector).val();

            const request = NetworkHelper.makeRequest("api/game", { numberOfOpponents });
            request.then(response => {
                Modal.closeActiveModal();
                startNewGame(response);
            });
        });

        DOMHelper.bindEndTurnEvent(onEndTurn);
        DOMHelper.bindNextRoundEvent(onNextRound);
    };

    const onNextRound = () => {
        resetRoundData();
        setTimeout(() => {
            startNewRound();
        }, 2000);
    };

    const onEndTurn = () => {
        if (activeAttribute) {
            setChosenAttribute().then(response => {
                Countdown.run(() => {
                    startRoundConclusion(response)
                });
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
    };

    const requestNumberOfOpponents = () => {
        DOMHelper.showModal(modalIDs.requestOpponents, false, false)
    };

    const startNewGame = gameData => {
        // Set game data
        numberOfOpponents = gameData.numberOfOpponents;
        activePlayerID = gameData.activePlayerId;
        humanPlayerID = gameData.humanPlayer.id;

        createPlayers(gameData.humanPlayer, gameData.aiPlayers);
        const players = assignDecks(gameData.humanPlayer, gameData.aiPlayers);
        PlayerModel.init(players);
        StatsHelper.init(PlayerModel.getPlayersList());

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
            name: humanPlayer.name,
            deck: humanPlayer.deck
        };

        aiPlayers.forEach((player, i) => {
            players[i + 1] = {
                id: player.id,
                name: player.name,
                deck: player.deck
            };
        });

        return players;
    };

    const startNewRound = () => {
        StatsHelper.incrementRoundNumber();

        if (!isHumanPlayerDefeated) {
            const topCard = PlayerModel.getTopCard(humanPlayerID);
            DOMHelper.showCard(humanPlayerID, topCard);
            layCardOnTable(humanPlayerID, topCard);
        }

        const aiPlayers = PlayerModel.getAiPlayersTopCards();

        $.each(aiPlayers, (playerID, card) => {
            layCardOnTable(playerID, card);
        });


        setTimeout(() => {
            DOMHelper.displayActivePlayer(activePlayerID);
        }, 1000);

        startAttributeSelection();
    };

    const layCardOnTable = (playerID, card) => {
        cardsOnTable.push({ playerID, card });
    };

    const startAttributeSelection = () => {
        if (activePlayerID === 0) {
            DOMHelper.showMessage("It is your turn. Choose an attribute");
            DOMHelper.enableAttributeSelection(onAttributeSelected, humanPlayerID);
        } else {
            DOMHelper.showMessage(`It is AI turn. Active player - ${PlayerModel.getPlayerName(activePlayerID)}`);
            DOMHelper.disableAttributeSelection(humanPlayerID);
            setTimeout(() => {
                getChosenAttribute().then(response => {
                    onAttributeSelected(response.selectedAttribute.name, response.selectedAttribute.value);
                    Countdown.run(() => { startRoundConclusion(response) });
                });
            }, 2000);
        }
    };

    const onAttributeSelected = (name, value) => {
        activeAttribute = { name, value };
    };

    const startRoundConclusion = async response => {
        DOMHelper.showOpponentsCards(cardsOnTable);

        setTimeout(() => {
            DOMHelper.showMessage(`Attribute ${activeAttribute.name} selected`);
            DOMHelper.highlightAttribute(activeAttribute);

            if (response.result === "VICTORY") {
                DOMHelper.clearPlayerStates();
                DOMHelper.displayWinnerPlayer(response.winner.id);
                StatsHelper.addRoundToPlayerByID(response.winner.id);
            }

            setTimeout(() => {
                startRoundOutcome(response);
            }, 3000);
        }, 3000);

    };

    const startRoundOutcome = response => {
        if (response.result === "DRAW") {
            updateCommonPile();
        } else if (response.result === "VICTORY") {
            distributeCards(response.winner.id, cardsOnTable);
        }

        // const playersCardCount = PlayerModel.getPlayersCardCount();

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
        // commonPile = $.merge(commonPile, cardsOnTable);
        cardsOnTable = [];
        DOMHelper.updateCommonPileIndicator(commonPile.length);
    };

    const resetCommonPile = () => {
        commonPile = [];
        DOMHelper.updateCommonPileIndicator(0);
    };

    const isEndGame = () => {
        if (removedPlayerIDs.length === numberOfOpponents) return true;
    };

    const setChosenAttribute = () => {
        const dataset = PlayerModel.prepareDataset(cardsOnTable);
        return NetworkHelper.makeRequest('api/outcome/human', {
            selectedAttribute: activeAttribute,
            activePlayerId: activePlayerID,
            players: dataset
        })
    };

    const getChosenAttribute = () => {
        const dataset = PlayerModel.prepareDataset(cardsOnTable);
        return NetworkHelper.makeRequest('api/outcome/ai', {
            activePlayerId: activePlayerID,
            players: dataset
        })
    };

    const showRoundOutcome = response => {
        if (response.result === "DRAW") {
            DOMHelper.showMessage("It's a draw");
            displayDraw(response);
        } else if (response.result === "VICTORY") {
            DOMHelper.showMessage(`${response.winner.name} is a winner`);
            showWinner(response.winner.id);
            displayVictory(response);
        }
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

        PlayerModel.passCardsToPlayerByID(playerID, cards);

        resetCommonPile();
    };

    const showGameOutcome = response => {
        const title = "Game over!";
        let hint;
        if (response.result === "VICTORY") {
            hint = StatsHelper.outputStats(response.winner.id);
        } else {
            const players = PlayerModel.getPlayers();
            if (Object.keys(players).length > 0) {
                hint = StatsHelper.outputStats(Object.keys(players)[0]);
            } else {
                hint = StatsHelper.outputStats(null);
            }
        }

        DOMHelper.showModal(modalIDs["gameOver"], title, hint);
    };

    const outputRemovedPlayers = playerIDs => {
        const playerNames = playerIDs.map(playerID => PlayerModel.getPlayerName(playerID));
        return `These players were defeated: ${playerNames.join(", ")}`;
    };

    const displayDraw = response => {
        let hint = `There are ${commonPile.length} cards in common pile`;
        if (response.removedPlayerIds.length > 0) {
            hint = outputRemovedPlayers(response.removedPlayerIds);
        }
        DOMHelper.showModal(modalIDs["draw"], "It's a draw!", hint);
    };

    const displayVictory = response => {
        let hint;
        if (response.removedPlayerIds.length > 0) {
            hint = outputRemovedPlayers(response.removedPlayerIds);
        }
        const title = response.winner.isAIPlayer === true ? `${response.winner.name} has won!` : "You won the round!";

        DOMHelper.showModal(modalIDs["victory"], title, hint);
    };

    return {
        init
    }
})(jQuery);