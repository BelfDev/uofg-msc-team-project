const Player = (function() {
    const anyPlayerSelector = ".js-player";

    const getTopCard = function(playerID) {
        $.get(`${restAPIurl}/getTopCard`, { playerID }, function(response) {
            Card.update(playerID, response);
        });
    };

    const getOpponentsCards = function() {
        $.get(`${restAPIurl}/getOpponentsCards`, function(response) {
            $.each(response, function(i, card) {
                Card.update(i, card);
            });
        });
    };

    const getPlayersCardsCount = function() {
        $.get(`${restAPIurl}/getPlayersCardsCount`, function(response) {
            $.each(response, function(playerID, data) {
                updateCardsCount(playerID, data.count);
            });
        });
    };

    const updateCardsCount = function(playerID, count) {
        let playerSelector = Player.getPlayerSelectorByID(playerID);

        $(playerSelector)
            .find(".js-player-hand-size")
            .text(count);
    };

    const getPlayerSelectorByID = function(playerID) {
        return $(anyPlayerSelector).filter(
            `[data-player-id="Player ${playerID}"]`
        );
    };

    return {
        getTopCard,
        getPlayerSelectorByID,
        getOpponentsCards,
        getPlayersCardsCount
    };
})();
