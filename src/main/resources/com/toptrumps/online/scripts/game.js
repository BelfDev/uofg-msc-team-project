const Game = (function() {
    const NUMBER_OF_AI_PLAYERS = 4;
    const endTurnButtonSelector = ".js-end-turn-button";

    let activeCategory = null;
    let activePlayerID = null;

    const init = function() {
        runStartPhase();
        bindEvents();
    };

    const bindEvents = function() {
        $(document).on("game:setActivePlayer", function(event, data) {
            activePlayerID = data.playerID;
            runCategorySelectionPhase(data.playerID);
        });

        $(document).on("game:categorySelect", function(event, data) {
            if (activePlayerID === 0) {
                $(document).trigger("message:log", {
                    content: `You selected the "${data.category}" category`
                });
            } else {
                $(document).trigger("message:log", {
                    content: `Player ${activePlayerID} selected the "${data.category}" category`
                });
            }

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

            $(document).trigger("card:categorySelection", { active: true });
        } else {
            $(document).trigger("message:log", {
                content: `Player ${playerID} is an active player and choosing a category`
            });

            $(document).trigger("card:categorySelection", { active: false });

            setTimeout(function() {
                $(document).trigger("countdown:run", {
                    callback: getChosenCategory
                });
            }, 2000);
        }
    };

    const runRoundConclusionPhase = function() {
        console.log("runRoundConclusionPhase");
        // TODO: reveal other players cards and announce the winner
    };

    const getChosenCategory = function() {
        $.get(`${restAPIurl}/getChosenCategory`, function(response) {
            $(document).trigger("game:categorySelect", {
                category: response.category
            });
        });
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
