const Game = (function() {
    const endTurnButtonSelector = ".js-end-turn-button";

    let numberOfOpponents = null;
    let roundNumber = 0;
    let activeAttribute = null;
    let activePlayerID = null;
    let humanPlayerID = null;
    let cardsShown = [];
    let commonPileCount = 0;
    const players = [];

    const init = function() {
        askForNumberOfOpponents();
        bindEvents();
    };

    const bindEvents = function() {
        // $(document).on("game:setActivePlayer", function(event, data) {
        //     activePlayerID = data.playerID;
        //     runAttributeSelectionPhase(data.playerID);
        // });

        $(document).on("game:newGameStarted", function(event, data) {
            console.log("Event: game:newGameStarted");
            $(document).trigger("message:log", {
                content: `New game started. Good luck!`
            });
            startNewGame(data);
        });

        $(document).on("game:newRoundStarted", function() {
            $(document).trigger("message:log", {
                content: `New round started`
            });
            prepareNewRound();
            setTimeout(function() {
                runStartPhase();
            }, 2000);
        });

        $(document).on("game:attributeSelect", function(event, attrData) {
            console.log("Event: game:attributeSelect");
            // console.log(data);
            if (activePlayerID === 0) {
                $(document).trigger("message:log", {
                    content: `You selected the "${attrData.name}" attribute`
                });
            } else {
                $(document).trigger("message:log", {
                    content: `Player ${activePlayerID} selected the "${attrData.name}" attribute`
                });
            }

            activeAttribute = attrData;
        });

        $(document).on("game:cardsShown", function(event, data) {
            console.log("Event: game:cardsShown");
            console.log($(".js-card-char"));
            // cardsShown = data.cardsShown;

            setTimeout(function() {
                showRoundOutcome(data);
            }, 5000);
        });

        $('.js-modal-next-round-button').on("click", function() {
           $(document).trigger("game:newRoundStarted")
        });

        $(endTurnButtonSelector).on("click", function(e) {
            console.log("end turn button clicked");
            // if (activeAttribute !== null) {
                runRoundConclusionPhase();
            // }

            e.preventDefault();
        });
    };

    const prepareNewRound = function() {
        Modal.closeActiveModal();
        players.forEach(function(player) {
            Card.reset(player.id);
        });
        resetPayLoad();
    };

    const askForNumberOfOpponents = function() {
        console.log("Method: askForNumberOfOpponents");
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
        console.log("Method: startNewGame");
        numberOfOpponents = 3;
        // numberOfOpponents = data.numberOfOpponents;
        // activePlayerID = data.activePlayerId;
        roundNumber = data.roundNumber;
        activePlayerID = 1;
        humanPlayerID = data.humanPlayer.id;

        createPlayers(data.humanPlayer, data.aiPlayers);

        setTimeout(function() {
            runStartPhase();
        }, 2000);
    };

    const createPlayers = function(human, ais) {
        console.log("Method: createPlayers");
        players[0] = {
            id: human.id,
            name: human.name,
            deck: human.deck
        };


        ais.pop();

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
        console.log("Method: runStartPhase");
        // Player.getPlayersCardsCount();
        players.forEach(player => {
            Player.updateCardsCount(player.id, player.deck.length);
        });

        const topCard = Dealer.getPlayerTopCard(humanPlayerID, players[humanPlayerID].deck);
        Card.update(humanPlayerID, topCard);
        preparePayLoad(humanPlayerID, topCard);
        // Player.getTopCard(0);
        // getCommonPileCount();
        runAttributeSelectionPhase();
        // getActivePlayer();
    };

    const resetPayLoad = function() {
        cardsShown = [];
    }

    const preparePayLoad = function(playerID, topCard) {
        console.log("Method: preparePayLoad");
        let payload = players[playerID];
        payload.topCard = topCard;
        payload.deckCount = payload.deck.length;
        cardsShown[playerID] = payload;
    };

    const runAttributeSelectionPhase = function() {
        console.log("Method: runAttributeSelectionPhase");
        // Human player has id 0
        if (activePlayerID === 0) {
            $(document).trigger("message:log", {
                content: "You are an active player. Choose an attribute"
            });

            $(document).trigger("card:attributeSelection", { active: true });
        } else {
            $(document).trigger("message:log", {
                content: `Player ${activePlayerID} is an active player. Click End Turn to see the selected attribute`
            });

            $(document).trigger("card:attributeSelection", { active: false });
        }
    };

    const doConclusion = function() {
        const playersData = Dealer.getOpponentsCards(players.slice(1));
        playersData.forEach(function(player, i) {
            setTimeout(function() {
                Card.update(player.id, player.topCard);
            }, 500 * i);
            preparePayLoad(player.id, player.topCard);
        });

        if (activePlayerID === 0) {
            setChosenAttribute(activeAttribute, cardsShown);
        } else {
            getChosenAttribute();
        }

        console.log($(".js-card-char"));
        setTimeout(function() {
            Card.highlightAttribute(activeAttribute);
        }, 2000);
    };

    const runRoundConclusionPhase = function() {
        console.log("Method: runRoundConclusionPhase");

        $(document).trigger("countdown:run", {
            callback: doConclusion
        });
        // $(document).trigger("game:cardsShown", { cardsShown });
    };

    // const getRoundOutcome = function() {
    //     console.log("Method: getRoundOutcome");
    //     $.get(`${restAPIurl}/getRoundOutcome`, function(response) {
    //         let targetModalSelector;
    //         let modalTitle = null;
    //         let modalHint = null;
    //
    //         if (response.outcome === 0) {
    //             targetModalSelector = ".js-round-draw-modal";
    //             modalTitle = "It's a draw";
    //             modalHint = `Common pile is now - ${response.commonPile} cards`;
    //         } else {
    //             targetModalSelector = ".js-round-win-modal";
    //             modalTitle =
    //                 response.playerID === 0
    //                     ? "You won!"
    //                     : `Player ${response.playerID} won!`;
    //         }
    //
    //         Modal.openModal(targetModalSelector, modalTitle, modalHint);
    //     });
    // };

    const showRoundOutcome = function(data) {
        console.log($(".js-card-char"));
        if (data.result === "DRAW") {
            updateCommonPile(data.communalPileAddition);

            Modal.openModal(".js-round-draw-modal", "It's a draw", `There are ${commonPileCount} cards in common pile`);
        } else if (data.result === "VICTORY") {
            activePlayerID = data.winner.id;

            let hint;
            const title = data.winner.isAIPlayer === false ? `${data.winner.name} has won!` : "You won the round!";
            if (data.defeatedPlayerIds.length > 0) {
                hint = `These players were defeated: ${data.defeatedPlayerIds}`;
            }
            Modal.openModal(".js-round-win-modal", title, hint)

        } else if (data.result === "END GAME") {
            const title = data.winner.isAIPlayer === false ? `Game over!` : "You won the game!";
            const hint = data.winner.isAIPlayer === false ? `${data.winner.name} has won!` : "You won!";
            Modal.openModal(".js-end-game-modal", title, hint);
        } else {
            console.log("Something wrong...");
        }
        console.log($(".js-card-char"));
        console.log(data);
    }

    const setChosenAttribute = function(attribute, cardsShown) {
        console.log("Method: setChosenAttribute");
        const passedData = {
            selectedAttribute: {
                attribute: attribute.name,
                value: attribute.value
            },
            players: cardsShown
        };

        // $.post(`https://api.myjson.com/bins/18z334`, passedData, function(response) {
        const response = {
            "result": "VICTORY",
            "winner":   {
                "id": 1,
                "name": "AI Player 1",
                "isAIPlayer": false
            },
            "communalPileAddition": 5,
            "defeatedPlayerIds": [
                1,
                2
            ]
        };
            $(document).trigger("game:cardsShown", response);
        // });
    };

    const getChosenAttribute = function() {
        console.log("Method: getChosenAttribute");

        // $.get(`${restAPIurl}/outcome/ai`, function(response) {
            const passedData = {
                players: cardsShown
            };
            console.log(passedData);
            const response = {
                "result": "VICTORY",
                "winner":   {
                    "id": 1,
                    "name": "AI Player 1",
                    "isAIPlayer": false
                },
                "defeatedPlayerIds": [
                    1,
                    2
                ],
                "selectedAttribute": {
                    "name": "Some Attribute",
                    "value": 8
                }
            };
            $(document).trigger("game:attributeSelect", response.selectedAttribute);
        $(document).trigger("game:cardsShown", response);
            // $(document).trigger("game:cardsShown", passedData);

            // runRoundConclusionPhase();
        // });
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
        console.log("Method: updateCommonPile");

        commonPileCount = commonPileCount + count;

        if (commonPileCount > 0) {
            $(".js-common-pile").addClass("game-status__common-pile--active");
            $(".js-common-pile-value").text(commonPileCount);
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
