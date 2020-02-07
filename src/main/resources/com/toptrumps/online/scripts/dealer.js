const Dealer = (function() {
    const getPlayerTopCard = function(playerID, playerDeck) {
        console.log("Method: getPlayerTopCard");
        const topCardData = playerDeck.shift();

        return topCardData;
    };

    const getOpponentsCards = function(players) {
        console.log("Method: getOpponentsCards");
        const playerData = [];
        players.forEach(function(player, i) {
            const pd = player;
            pd.topCard = getPlayerTopCard(player.id, player.deck);
            playerData.push(pd);
        });
        return playerData;
    };

    return {
        getPlayerTopCard,
        getOpponentsCards
    };
})();
