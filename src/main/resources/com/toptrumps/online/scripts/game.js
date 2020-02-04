const Game = (function() {
    const NUMBER_OF_AI_PLAYERS = 4; // TODO: to be obtained dynamically later
    const endTurnButtonSelector = ".js-end-turn-button";

    let activeAttribute = null;
    let activePlayerID = null;

    const init = function() {
        runStartPhase();
        bindEvents();
    };

    const bindEvents = function() {
        $(document).on("game:setActivePlayer", function(event, data) {
            activePlayerID = data.playerID;
            runAttributeSelectionPhase(data.playerID);
        });

        $(document).on("game:attributeSelect", function(event, data) {
            if (activePlayerID === 0) {
                $(document).trigger("message:log", {
                    content: `You selected the "${data.attribute}" attribute`
                });
            } else {
                $(document).trigger("message:log", {
                    content: `Player ${activePlayerID} selected the "${data.attribute}" attribute`
                });
                Card.highlightAttribute(data.attribute);
            }

            activeAttribute = data.attribute;
        });

        $(document).on("game.cardsShown", function() {
            Card.highlightAttribute(activeAttribute);
        });

        $(endTurnButtonSelector).on("click", function(e) {
            if (activeAttribute !== null) {
                runRoundConclusionPhase();
            }

            e.preventDefault();
        });
    };

    const runStartPhase = function() {
        Player.getPlayersCardsCount();
        Player.getTopCard(0);
        getActivePlayer();
    };

    const runAttributeSelectionPhase = function(playerID) {
        // Human player has id 0
        if (playerID === 0) {
            $(document).trigger("message:log", {
                content: "You are an active player. Choose an attribute"
            });

            $(document).trigger("card:attributeSelection", { active: true });
        } else {
            $(document).trigger("message:log", {
                content: `Player ${playerID} is an active player and choosing an attribute`
            });

            $(document).trigger("card:attributeSelection", { active: false });

            setTimeout(function() {
                $(document).trigger("countdown:run", {
                    callback: getChosenAttribute
                });
            }, 2000);
        }
    };

    const runRoundConclusionPhase = function() {
        Player.getOpponentsCards();
    };

    const getChosenAttribute = function() {
        $.get(`${restAPIurl}/getChosenAttribute`, function(response) {
            $(document).trigger("game:attributeSelect", {
                attribute: response.attribute
            });

            runRoundConclusionPhase();
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
