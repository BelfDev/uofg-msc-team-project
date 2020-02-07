const Player = (function() {
    const playerBoxTemplateSelector = $("#template-player-box");
    const anyPlayerSelector = ".js-player";

    // const getTopCard = function(playerID) {
    //     $.get(`${restAPIurl}/getTopCard`, { playerID }, function(response) {
    //         Card.update(playerID, response);
    //     });
    // };

    // const getOpponentsCards = function() {
    //     $.get(`${restAPIurl}/getOpponentsCards`, function(response) {
    //         $.each(response, function(i, card) {
    //             setTimeout(function() {
    //                 Card.update(i, card);
    //             }, 500 * i);
    //         });
    //         $(document).trigger("game.cardsShown");
    //     });
    // };
    //
    // const getPlayersCardsCount = function() {
    //     $.get(`${restAPIurl}/getPlayersCardsCount`, function(response) {
    //         $.each(response, function(playerID, data) {
    //             updateCardsCount(playerID, data.count);
    //         });
    //     });
    // };

    const updateCardsCount = function(playerID, count) {
        let playerSelector = Player.getPlayerSelectorByID(playerID);

        $(playerSelector)
            .find(".js-player-hand")
            .text(`${count} cards left`);
    };

    const getPlayerSelectorByID = function(playerID) {
        if (playerID === 0) return $(".js-human-player");
        return $(anyPlayerSelector).filter(function() {
            return $(this).data("playerId") === playerID
        });
    };

    const renderPlayer = function(player) {
        const playerTpl = $(playerBoxTemplateSelector).html();
        const playerNode = $(playerTpl).clone();
        playerNode.data("player-id", player.id);
        playerNode.find(".js-player-name").text(player.name);
        playerNode.find(".js-player-hand").text(`${player.deck.length} cards left`);

        $(".js-opponents-box").append(playerNode);
    };

    return {
        renderPlayer,
        getPlayerSelectorByID,
        updateCardsCount
    };
})();
