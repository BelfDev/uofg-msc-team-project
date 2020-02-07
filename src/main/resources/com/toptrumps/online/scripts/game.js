const Game = (function() {
    const endTurnButtonSelector = ".js-end-turn-button";

    let numberOfOpponents = null;
    let roundNumber = 0;
    let activeAttribute = null;
    let activePlayerID = null;
    let humanPlayerID = null;
    const players = [];

    const init = function() {
        console.log("Working...");
        askForNumberOfOpponents();
        bindEvents();
    };

    const bindEvents = function() {
        // $(document).on("game:setActivePlayer", function(event, data) {
        //     activePlayerID = data.playerID;
        //     runAttributeSelectionPhase(data.playerID);
        // });

        $(document).on("game:newGameStarted", function(event, data) {
            startNewGame(data);
        });

        $(document).on("game:newRoundStarted", function() {
            setTimeout(function() {
                runStartPhase();
            }, 2000);
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

            setTimeout(function() {
                getRoundOutcome();
            }, 3000);
        });

        $(endTurnButtonSelector).on("click", function(e) {
            if (activeAttribute !== null) {
                runRoundConclusionPhase();
            }

            e.preventDefault();
        });
    };

    const askForNumberOfOpponents = function() {
        let targetModalSelector = ".js-new-game-modal";

        Modal.openModal(targetModalSelector);

        $(".js-modal-new-game-button").on("click", function() {
            const numberOfOpponents = $(".js-opponents-count").val();

            // TODO: change to POST
            $.get(
                // `${restAPIurl}/api/game`,
                `https://api.myjson.com/bins/1dnak2`,
                { numberOfOpponents },
                function(response) {
                    $(document).trigger("game:newGameStarted", response);
                    Modal.closeActiveModal();
                }
            );
        });
    };

    const startNewGame = function(data) {
        numberOfOpponents = data.numberOfOpponents;
        activePlayerID = data.activePlayerId;
        roundNumber = data.roundNumber;
        humanPlayerID = data.humanPlayer.id;

        createPlayers(data.humanPlayer, data.aiPlayers);

        $(document).trigger("game:newRoundStarted");
    };

    const createPlayers = function(human, ais) {
        players[0] = {
            id: human.id,
            name: human.name,
            deck: human.deck
        };

        ais.forEach((ai, i) => {
            players[i + 1] = {
                id: ai.id,
                name: ai.name,
                deck: ai.deck
            };
            Player.renderPlayer(ai);
        });
    };

    const runStartPhase = function() {
        // Player.getPlayersCardsCount();
        players.forEach(player => {
            Player.updateCardsCount(player.id, player.deck.length);
        });

        Dealer.getPlayerTopCard(humanPlayerID, players[humanPlayerID].deck);
        // Player.getTopCard(0);
        // getCommonPileCount();
        runAttributeSelectionPhase();
        // getActivePlayer();
    };

    const runAttributeSelectionPhase = function() {
        // Human player has id 0
        if (activePlayerID === 0) {
            $(document).trigger("message:log", {
                content: "You are an active player. Choose an attribute"
            });

            $(document).trigger("card:attributeSelection", { active: true });
        } else {
            $(document).trigger("message:log", {
                content: `Player ${activePlayerID} is an active player and choosing an attribute`
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
        Dealer.getOpponentsCards(players.slice(1));
        console.log(players);
    };

    const getRoundOutcome = function() {
        $.get(`${restAPIurl}/getRoundOutcome`, function(response) {
            let targetModalSelector;
            let modalTitle = null;
            let modalHint = null;

            if (response.outcome === 0) {
                targetModalSelector = ".js-round-draw-modal";
                modalTitle = "It's a draw";
                modalHint = `Common pile is now - ${response.commonPile} cards`;
            } else {
                targetModalSelector = ".js-round-win-modal";
                modalTitle =
                    response.playerID === 0
                        ? "You won!"
                        : `Player ${response.playerID} won!`;
            }

            Modal.openModal(targetModalSelector, modalTitle, modalHint);
        });
    };

    const getChosenAttribute = function() {
        $.get(`${restAPIurl}/getChosenAttribute`, function(response) {
            $(document).trigger("game:attributeSelect", {
                attribute: response.attribute
            });

            runRoundConclusionPhase();
        });
    };

    // const getCommonPileCount = function() {
    //     $.get(`${restAPIurl}/getCommonPileCount`, function(response) {
    //         if (response.count > 0) {
    //             $(".js-common-pile").addClass(
    //                 "game-status__common-pile--active"
    //             );
    //             $(".js-common-pile-value").text(response.count);
    //         } else {
    //             $(".js-common-pile").removeClass(
    //                 "game-status__common-pile--active"
    //             );
    //         }
    //     });
    // };

    const updateCommonPile = function(count) {
        if (response.count > 0) {
            $(".js-common-pile").addClass("game-status__common-pile--active");
            $(".js-common-pile-value").text(count);
        } else {
            $(".js-common-pile").removeClass(
                "game-status__common-pile--active"
            );
        }
    };

    // const getActivePlayer = function() {
    //     $.get(`${restAPIurl}/getActivePlayer`, function(response) {
    //         if (
    //             response.playerID < 0 ||
    //             response.playerID > numberOfOpponents
    //         ) {
    //             throw new Error(
    //                 `Player ID must be between 0 and ${numberOfOpponents}`
    //             );
    //             return false;
    //         }

    //         $(document).trigger("game:setActivePlayer", {
    //             playerID: response.playerID
    //         });
    //     });
    // };

    return {
        init,
        numberOfOpponents
    };
})();

$(document).ready(Game.init);
