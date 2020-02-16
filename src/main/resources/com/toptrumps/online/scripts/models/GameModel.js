/**
 * Game Model
 * Manipulates game state and variables
 */

const GameModel = (($) => {
    // Model variables
    let players = {};
    let playersList = {};
    let numberOfOpponents;
    let activePlayerID;
    let humanPlayerID;
    let activeAttribute;
    let cardsOnTable = [];
    let commonPile = [];
    let removedPlayerIDs = [];
    let isHumanPlayerDefeated = false;


    /** METHODS **/

    /**
     * Initialization
     * Sets players for game
     * @param humanPlayer
     * @param aiPlayers
     */
    const init = data => {
        numberOfOpponents = data.numberOfOpponents;
        activePlayerID = data.activePlayerId;
        humanPlayerID = data.humanPlayer.id;

        players[humanPlayerID] = {
            id: data.humanPlayer.id,
            name: data.humanPlayer.name.replace("_", " "),
            deck: data.humanPlayer.deck
        };
        playersList[humanPlayerID] = { id: data.humanPlayer.id, name: data.humanPlayer.name };

        data.aiPlayers.forEach((player, i) => {
            players[player.id] = {
                id: player.id,
                name: player.name.replace("_", " "),
                deck: player.deck
            };
            playersList[player.id] = { id: player.id, name: player.name };
        });
    };

    /**
     * Puts the card in the game
     * @param playerID
     * @param card
     */
    const layCardOnTable = (playerID, card) => {
        cardsOnTable.push({ playerID, card });
    };

    /**
     *
     */
    const putOpponentsCardsOnTable = () => {
        const playersCards = {};

        $.each(players, (i, player) => {
            if (player.id === 0) return;

            const card = getTopCard(player.id);
            layCardOnTable(player.id, card);
        });

        return playersCards;
    };

    /**
     * Cards on table getter
     * @returns {Array}
     */
    const getCardsOnTable = () => {
        return cardsOnTable;
    }

    /**
     * Players getter
     * @returns {Object}
     */
    const getPlayers = () => {
        return players;
    };

    /**
     * Players list getter
     * @returns {Object}
     */
    const getPlayersList = () => {
        return playersList;
    };

    /**
     * Checks if there are players in game
     * @returns {boolean}
     */
    const isEndGame = () => {
        if (removedPlayerIDs.length === numberOfOpponents) return true;
    };

    /**
     * Top card getter
     * Mutates players deck (removes top element)
     * @param playerID
     * @returns {Object} - card
     */
    const getTopCard = playerID => {
        return players[playerID].deck.shift();
    };

    /**
     * Sets active attribute
     * @param name
     * @param value
     */
    const setActiveAttribute = (name, value) => {
        activeAttribute = { name, value };
    };

    /**
     * Creates a data array ready for sending to the server
     * @param cardsOnTable
     * @returns {Array}
     */
    const prepareDataset = cardsOnTable => {
        let dataset = [];

        cardsOnTable.forEach(data => {
            dataset.push({
                id: data.playerID,
                isAIPlayer: data.playerID !== humanPlayerID,
                name: players[data.playerID].name,
                deckCount: players[data.playerID].deck.length + 1, // one card is already on the table
                topCard: data.card
            });
        });

        return dataset;
    };

    /**
     * Return player with the most cards
     */
    const getPlayerWithMostCards = () => {
        let targetPlayer = players[0];
        let max = 0;
        $.each(players, (i, player) => {
            if (player.deck.length > max) {
                targetPlayer = {
                    id: player.id,
                    name: player.name,
                    isAIPlayer: player.playerID !== humanPlayerID
                };
            }
        });

        return targetPlayer;
    }

    /**
     * Removes player from active players
     * @param playerID
     */
    const removePlayer = playerID => {
        delete players[playerID];
    };

    /**
     * Shuffles cards
     * @param cardsArray
     * @returns {Array}
     */
    const shuffleCards = cardsArray => {
        return cardsArray.sort(() => Math.random() - 0.5);
    };

    /**
     * Distributes cards to the target player after round conclusion
     * @param playerID
     * @param cardsOnTable
     */
    const distributeCards = playerID => {
        const cards = commonPile;
        cardsOnTable.forEach(data => {
            cards.push(data.card);
        });

        shuffleCards(cards);

        GameModel.passCardsToPlayerByID(playerID, cards);
    };

    /**
     * Updates removed players list
     * @param playerIDs
     */
    const updateRemovedPlayers = playerIDs => {
        playerIDs.forEach(playerID => {
            if (removedPlayerIDs.indexOf(playerID) === -1) {
                removedPlayerIDs.push(playerID);
                GameModel.removePlayer(playerID);
                if (playerID === 0) isHumanPlayerDefeated = true;
            }
        })

    };

    /**
     * Active player setter
     * @param playerID
     */
    const setActivePlayer = playerID => {
        activePlayerID = playerID;
    }

    /**
     * Passes all provided cards to the target player's deck
     * @param playerID
     * @param cards
     */
    const passCardsToPlayerByID = (playerID, cards) => {
        cards.forEach((card) => {
            players[playerID].deck.push(card);
        });
    };

    /**
     * Player name getter
     * @param playerID
     * @returns {String}
     */
    const getPlayerName = playerID => {
        return playersList[playerID].name;
    };

    /**
     * Is human player defeated boolean getter
     * @returns {boolean}
     */
    const getIsHumanPlayerDefeated = () => {
        return isHumanPlayerDefeated;
    };

    /**
     * Human player id getter
     * @returns {Integer}
     */
    const getHumanPlayerID = () => {
        return humanPlayerID;
    };

    /**
     * Active attribute getter
     * @returns {Object}
     */
    const getActiveAttribute = () => {
        return activeAttribute;
    };

    /**
     * Active player id getter
     * @returns {Integer}
     */
    const getActivePlayerID = () => {
        return activePlayerID;
    };

    /**
     * Sets cards on table to empty state
     */
    const resetCardsOnTable = () => {
        cardsOnTable = [];
    };

    /**
     * Updates common pile count
     * @returns {Integer}
     */
    const updateCommonPile = () => {
        cardsOnTable.forEach(data => {
            commonPile.push(data.card);
        });
        cardsOnTable = [];

        return commonPile.length;
    };

    /**
     * Sets common pile count to zero
     */
    const resetCommonPile = () => {
        commonPile = [];
    };


    /** EXPOSE PUBLIC METHODS **/

    return {
        distributeCards,
        getActiveAttribute,
        getActivePlayerID,
        getCardsOnTable,
        getHumanPlayerID,
        getIsHumanPlayerDefeated,
        getPlayerName,
        getPlayers,
        getPlayersList,
        getTopCard,
        getPlayerWithMostCards,
        init,
        isEndGame,
        layCardOnTable,
        passCardsToPlayerByID,
        prepareDataset,
        putOpponentsCardsOnTable,
        removePlayer,
        resetCardsOnTable,
        resetCommonPile,
        setActiveAttribute,
        setActivePlayer,
        updateCommonPile,
        updateRemovedPlayers,
    }
})(jQuery);