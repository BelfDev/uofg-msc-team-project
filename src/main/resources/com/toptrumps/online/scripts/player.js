const Player = (function() {
    const humanPlayerSelector = ".js-human-player";
    const AIPlayerSelector = ".js-ai-player";

    const getTopCard = function(playerID) {
        $.get(`${restAPIurl}/getTopCard`, { playerID }, function(response) {
            Card.update(playerID, response);
        });
    };

    const getHumanPlayerSelector = function() {
        return humanPlayerSelector;
    };

    const getOpponentsCards = function() {
        $.get(`${restAPIurl}/getOpponentsCards`, function(response) {
            $.each(response, function(i, card) {
                Card.update(i, card);
            });
        });
    };

    const getPlayerSelectorByID = function(playerID) {
        let playerSelector;

        // Temporary approach to separate human player's box selector
        // from AI player's
        // TODO: refactor after backend implementation of player's identifier
        if (playerID === 0) {
            $playerSelector = humanPlayerSelector;
        } else {
            $playerSelector = $(AIPlayerSelector).filter(
                `[data-player-id="Player ${playerID}"]`
            );
        }

        return $playerSelector;
    };

    return {
        getTopCard,
        getPlayerSelectorByID,
        getHumanPlayerSelector,
        getOpponentsCards
    };
})();
