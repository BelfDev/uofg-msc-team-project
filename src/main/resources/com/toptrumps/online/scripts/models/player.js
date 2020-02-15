const PlayerModel = (($) => {
    let players = {};
    let playersList = {};
    let humanPlayerID = 0;

    const init = playersData => {
        playersData.forEach(player => {
            players[player.id] = player;
            playersList[player.id] = { id: player.id, name: player.name };
        });
    };

    const getPlayers = () => {
        return players;
    };

    const getPlayersList = () => {
        return playersList;
    };

    const getTopCard = playerID => {
        return players[playerID].deck.shift();
    };

    const getAiPlayersTopCards = () => {
        const playersCards = {};

        $.each(players, (i, player) => {
            if (player.id === 0) return;

            playersCards[player.id] = getTopCard(player.id);
        });

        return playersCards;
    };

    const prepareDataset = cardsOnTable => {
        let dataset = [];

        cardsOnTable.forEach(data => {
            dataset.push({
                id: data.playerID,
                isAIPlayer: data.playerID !== humanPlayerID,
                name: players[data.playerID].name,
                deckCount: players[data.playerID].deck.length + 1,
                topCard: data.card
            });
        });

        return dataset;
    };

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

    const setHumanPlayerID = ID => {
        humanPlayerID = ID;
    };

    const getPlayersCardCount = () => {
        const playersCardsCount = [];

        $.each(players, (i, player) => {
            playersCardsCount[i] = player.deck.length;
        });

        return playersCardsCount;
    };

    const removePlayer = playerID => {
        delete players[playerID];
    };

    const passCardsToPlayerByID = (playerID, cards) => {
        cards.forEach((card) => {
            players[playerID].deck.push(card);
        });
    };

    const getPlayerName = playerID => {
        return playersList[playerID].name;
    };

    return {
        init,
        getPlayers,
        getPlayersList,
        getTopCard,
        getAiPlayersTopCards,
        getPlayerWithMostCards,
        setHumanPlayerID,
        prepareDataset,
        getPlayersCardCount,
        removePlayer,
        passCardsToPlayerByID,
        getPlayerName
    }
})(jQuery);