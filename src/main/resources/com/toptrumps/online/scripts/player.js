const Player = (function() {
    const humanPlayerSelector = ".js-human-player";

    const getTopCard = function(playerID) {
        $.get(`${restAPIurl}/getTopCard`, { playerID }, function(response) {
            Card.update(playerID, response);
        });
    };

    const getHumanPlayerSelector = function() {
        return humanPlayerSelector;
    };

    const getPlayerSelectorByID = function(playerID) {
        let playerSelector;

        // Temporary approach to separate human player's box selector
        // from AI player's
        // TODO: refactor after backend implementation of player's identifier
        if (playerID === 0) {
            playerSelector = humanPlayerSelector;
        }

        return playerSelector;
    };

    return {
        getTopCard,
        getPlayerSelectorByID,
        getHumanPlayerSelector
    };
})();
