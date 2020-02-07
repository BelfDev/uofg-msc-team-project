const Dealer = (function() {
    const getPlayerTopCard = function(playerID, playerDeck) {
        const topCardData = playerDeck.shift();

        Card.update(playerID, topCardData);
    };

    const getOpponentsCards = function(players) {
        players.forEach(function(player, i) {
            setTimeout(function() {
                getPlayerTopCard(player.id, player.deck);
            }, 500 * i);
        });
        $(document).trigger("game.cardsShown");
    };

    return {
        getPlayerTopCard,
        getOpponentsCards
    };
})();
