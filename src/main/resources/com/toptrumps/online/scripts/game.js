const Game = (function() {
    const init = function() {
        Player.getTopCard(0);
        getActivePlayer();
    };

    const getActivePlayer = function() {
        $.get(`${restAPIurl}/getActivePlayer`, function(response) {
            // TODO: use player id to check if human player can choose category
            // or send another request to get AI player's choice
            console.log(response.playerID);
        });
    };

    return {
        init
    };
})();

$(document).ready(Game.init);
