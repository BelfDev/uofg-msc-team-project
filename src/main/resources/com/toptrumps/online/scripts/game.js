const Game = (function() {
    const NUMBER_OF_AI_PLAYERS = 4;
    const endTurnButtonSelector = ".js-end-turn-button";

    let activeCategory = null;

    const init = function() {
        runStartPhase();
        bindEvents();
    };

    const bindEvents = function() {
        $(document).on("game:setActivePlayer", function(event, data) {
            runCategorySelectionPhase(data.playerID);
        });

        $(document).on("game:categorySelect", function(event, data) {
            activeCategory = data.category;
        });

        $(endTurnButtonSelector).on("click", function(e) {
            if (activeCategory !== null) {
                runRoundConclusionPhase();
            }

            e.preventDefault();
        });
    };

    const runStartPhase = function() {
        Player.getTopCard(0);
        getActivePlayer();
    };

    const runCategorySelectionPhase = function(playerID) {
        // Human player has id 0
        if (playerID === 0) {
            $(document).trigger("message:log", {
                content: "You are an active player. Choose a category"
            });

            Card.enableCategorySelection();
        } else {
            Card.disableCategorySelection();
            // TODO: send request to get chosen category
        }
    };

    const runRoundConclusionPhase = function() {
        console.log("runRoundConclusionPhase");
        // TODO: reveal other players cards and announce the winner
    };

    const getActivePlayer = function() {
        $.get(`${restAPIurl}/getActivePlayer`, function(response) {
            if (
                response.playerID < 0 ||
                response.playerID > NUMBER_OF_AI_PLAYERS
            ) {
                throw new Error(
                    `Player ID must be between 0 and ${NUMBER_OF_AI_PLAYERS}`
                );
                return false;
            }

            $(document).trigger("game:setActivePlayer", {
                playerID: response.playerID
            });
        });
    };

    return {
        init,
        NUMBER_OF_AI_PLAYERS
    };
})();

$(document).ready(Game.init);
