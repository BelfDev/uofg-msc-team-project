const Game = (function() {
    const init = function() {
        Player.getTopCard(0);
    };

    return {
        init
    };
})();

$(document).ready(Game.init);
