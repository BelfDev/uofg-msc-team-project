const DOMHelper = (($) => {
    /** VARIABLES AND CONSTANTS */
    // Selectors variables
    const pageCurtainSelector = ".js-page-curtain";
    const inputNumberSelector = ".js-opponents-quantity";
    const playerNameSelector = ".js-player-name";
    const playerDeckCountSelector = ".js-player-deck-count";
    const opponentsBoxSelector = ".js-opponents-box";
    const playerSelector = ".js-player";
    const humanPlayerSelector = ".js-human-player";
    const cardSelector = ".js-card";
    const cardAttributeSelector = ".js-card-char";
    const cardAttributeValueSelector = ".js-card-char-value";
    const cardAttributesWrapperSelector = ".js-card-chars";
    const endTurnButtonSelector = ".js-end-turn-button";
    const countdownSelector = ".js-countdown";
    const cardImageSelector = ".js-card-image";
    const cardTitleSelector = ".js-card-title";
    const commonPileValueSelector = ".js-common-pile-value";
    const commonPileSelector = ".js-common-pile";
    const messageLogSelector = ".js-game-log";
    const gameOverWinnerSelector = ".js-game-over-winner";
    const gameOverWinnerNameSelector = ".js-game-over-winner-name";
    const gameOverRoundsWrapperSelector = ".js-game-over-rounds-wrapper";
    const gameOverRoundsName = ".js-game-over-rounds-name";
    const gameOverRoundsNumber = ".js-game-over-rounds-number";

    // Modals
    const modalSelectors = {
        ASK_FOR_NUMBER_OF_OPPONENTS: ".js-new-game-modal",
        DRAW: ".js-round-draw-modal",
        VICTORY: ".js-round-win-modal",
        GAME_OVER: ".js-end-game-modal",
        QUIT: ".js-quit-modal"
    };

    // CSS classes to represent visual state
    const cardAttributeActiveClass = "pcard__char--active";
    const cardActiveClass = "pcard--shown";
    const cardAttributesWrapperActiveClass = "pcard__chars--active";
    const defeatedPlayerClass = "defeated-player";
    const activePlayerClass = "active-player";
    const winnerPlayerClass = "winner-player";
    const attributeSelectedClass = "human-player--attr-selected"
    const endTurnButtonActiveClass = "end-turn-button-active";
    const commonPileActiveClass = "heartbeat";

    // Templates
    const playerBoxTemplateSelector = "#template-player-box";
    const cardAttributeTemplateSelector = "#template-attribute-item";
    const gameOverTemplateSelector = "#template-game-over";
    const roundsBoxTemplateSelector = "#template-rounds-box";

    // Timer and duration variables
    const curtainDelay = 1000;

    // Easings
    const easeInQuart = 'cubicBezier(0.895, 0.030, 0.685, 0.220)';


    /** METHODS */

    const showUI = () => {
        setTimeout(() => {
            initializePlugins();

            anime({
                targets: pageCurtainSelector,
                opacity: '0',
                easing: easeInQuart, /* easeInQuart */
                complete: (anim) => {
                    $(anim.animatables[0].target).remove();
                }
            });
        }, curtainDelay);

    };

    const showMessage = message => {
        $(messageLogSelector).fadeOut("fast", () => {
            $(messageLogSelector).html(message);
            $(messageLogSelector).show();
            Message.animateLog(messageLogSelector);
        });
    };

    const initializePlugins = () => {
        Countdown.init(countdownSelector);
    };

    const showModal = (selectorID, title, hint) => {
        const targetModalSelector = modalSelectors[selectorID];

        Modal.openModal(targetModalSelector, title, hint);
    };

    const renderPlayer = player => {
        const playerTpl = $(playerBoxTemplateSelector).html();
        const playerNode = $(playerTpl).clone();

        playerNode.data("player-id", player.id);
        playerNode.find(playerNameSelector).text(player.name.replace("_", " "));
        playerNode.find(playerDeckCountSelector).text(getDeckCountMessage(player.deck.length));

        $(opponentsBoxSelector).append(playerNode);
    };

    const getDeckCountMessage = deckCount => {
        return `${deckCount} cards left`
    };

    const updateDeckCount = (playerID, deckCount) => {
        let playerSelector = getPlayerSelectorByID(playerID);

        $(playerSelector)
            .find(playerDeckCountSelector)
            .text(getDeckCountMessage(deckCount));
    };

    const getPlayerSelectorByID = (playerID) => {
        if (playerID === 0) return $(humanPlayerSelector);

        return $(playerSelector).filter(function() {
            return $(this).data("playerId") === playerID
        });
    };

    const showCard = (playerID, card) => {
        let playerSelector = getPlayerSelectorByID(parseInt(playerID));

        setCardImage(playerSelector, card.name);
        setCardTitle(playerSelector, card.name);
        setCardAttributes(playerSelector, card.attributes);

        const $card = $(playerSelector).find(cardSelector);
        animateCard($card);
    };

    const setCardImage = (playerSelector, cardName) => {
        const request = getImagePath(cardName);

        request.then((imagePath) => {
            $(playerSelector)
                .find(cardImageSelector)
                .attr("src", imagePath)
                .attr("title", cardName);
        });
    };

    const getImagePath = async cardName => {
        const imageName = cardName.replace(" ", "_");
        const imageUrl = `/assets/images/cards/${imageName}.png`;
        const result = await checkResource(imageUrl);
        let finalUrl = `/assets/images/cards/Default.png`;

        if (result) finalUrl = imageUrl;

        return finalUrl;
    };

    const checkResource = url => {
        return NetworkHelper.checkIfResourceExists(url);
    };

    const setCardTitle = (playerSelector, cardName) => {
        $(playerSelector)
            .find(cardTitleSelector)
            .html(cardName.replace("_", " "));
    };

    const setCardAttributes = (playerSelector, attributes) => {
        const attrNodeCollection = createAttributesNodes(playerSelector, attributes);

        $(playerSelector)
            .find(cardAttributesWrapperSelector)
            .html(attrNodeCollection);
    };

    const createAttributesNodes = (playerSelector, attributes) => {
        let attrNodeCollection = [];
        const attrTpl = $(cardAttributeTemplateSelector).html();

        $.each(attributes, (i, attr) => {
            const attrNode = $(attrTpl).clone();

            attrNode.data("attribute", attr.name);
            attrNode.prepend(attr.name);
            attrNode.find(cardAttributeValueSelector).html(attr.value);
            attrNodeCollection.push(attrNode);
        });

        return attrNodeCollection;
    };

    const animateCard = ($card) => {
        $card.addClass(cardActiveClass);

        const options = {
            targets: $card[0],
            scale: [{ value: 1 }, { value: 1.3 }, { value: 1, delay: 250 }],
            rotateY: { value: "+=180", delay: 200 },
            easing: "easeInOutSine",
            duration: 400
        };

        anime(options);
    };

    const enableAttributeSelection = (attributeCallback, humanPlayerID, endTurnCallback) => {
        let playerSelector = getPlayerSelectorByID(humanPlayerID);

        const $attributesWrapper = getAttributesWrapper(playerSelector);
        $attributesWrapper.addClass(cardAttributesWrapperActiveClass);

        const $attributes = getAttributes(playerSelector);

        $($attributes)
            .off("click")
            .on("click", event => {
                const $target = $(event.currentTarget);
                $(humanPlayerSelector).addClass(attributeSelectedClass);
                $(endTurnButtonSelector).addClass(endTurnButtonActiveClass);
                $($attributes).removeClass(cardAttributeActiveClass);
                $target.addClass(cardAttributeActiveClass);

                const attrName = $target.data("attribute");
                const attrValue = $target.find(".js-card-char-value").text();

                bindEndTurnEvent(endTurnCallback);

                attributeCallback(attrName, attrValue);
            });
    };

    const getAttributes = (playerSelector) => {
        return $(playerSelector).find(cardAttributeSelector);
    };

    const getAttributesWrapper = (playerSelector) => {
        return $(playerSelector).find(cardAttributesWrapperSelector);
    };

    const disableAttributeSelection = humanPlayerID => {
        let playerSelector = getPlayerSelectorByID(humanPlayerID);

        const $attributesWrapper = getAttributesWrapper(playerSelector);
        $attributesWrapper.removeClass(cardAttributesWrapperActiveClass);

        const $attributes = getAttributes(playerSelector);
        $($attributes).off("click");
    };

    const unBindEndTurnEvent = () => {
        $(endTurnButtonSelector).off("click");
    };

    const bindEndTurnEvent = callback => {
        $(endTurnButtonSelector).on("click", function(e) {
            $(endTurnButtonSelector).removeClass(endTurnButtonActiveClass);
            callback();

            e.preventDefault();
        });
    };

    const setPlayerStateToDefeated = playerID => {
        let playerSelector = getPlayerSelectorByID(playerID);

        $(playerSelector).addClass(defeatedPlayerClass);
        resetCard(playerID);
    };

    const resetCard = playerID => {
        let playerSelector = getPlayerSelectorByID(playerID);

        const $card = $(playerSelector).find(cardSelector);

        animateCard($card, playerID);
        $card.removeClass(cardActiveClass);
    };

    const updateCommonPileIndicator = count => {
        $(commonPileValueSelector).text(count);
        $(commonPileSelector).addClass(commonPileActiveClass);
        setTimeout(() => {
            $(commonPileSelector).removeClass(commonPileActiveClass);
        }, 2000);
    };

    const clearPlayerStates = () => {
        $(humanPlayerSelector).removeClass(attributeSelectedClass);
        $(endTurnButtonSelector).removeClass(endTurnButtonActiveClass);
        $(playerSelector).removeClass(activePlayerClass);
        $(playerSelector).removeClass(winnerPlayerClass);
    };

    const displayActivePlayer = playerID => {
        const targetPlayerSelector = getPlayerSelectorByID(playerID);

        $(targetPlayerSelector).addClass(activePlayerClass);
    };

    const displayWinnerPlayer = playerID => {
        const targetPlayerSelector = getPlayerSelectorByID(playerID);

        $(targetPlayerSelector).addClass(winnerPlayerClass);
    };

    const showOpponentsCards = cardsOnTable => {
        cardsOnTable.forEach((data, index) => {
            if (data.playerID === 0) return;

            setTimeout(() => {
                showCard(data.playerID, data.card);
            }, 500 * index);
        });
    };

    const highlightAttribute = attribute => {
        $(cardAttributeSelector).filter(function() {
            return $(this).data("attribute") === attribute.name
        }).addClass(cardAttributeActiveClass);
    };

    const resetAttributeHighlight = () => {
        $(cardAttributeSelector).removeClass(cardAttributeActiveClass);
    };

    const getStatsMarkup = stats => {
        const gameStatsTpl = $(gameOverTemplateSelector).html()
        const $gameStats = $(gameStatsTpl).clone();

        if (stats.finalWinner !== null) {
            $gameStats.find(gameOverWinnerNameSelector).text(stats.finalWinner.name);
        } else {
            $gameStats.find(gameOverWinnerSelector).hide();
        }

        const $roundsWrapper = $gameStats.find(gameOverRoundsWrapperSelector);
        $roundsWrapper.html(createRoundsBoxNodes(stats.roundWins));

        return $gameStats;
    };

    const createRoundsBoxNodes = roundWins => {
        const boxNodeCollection = [];
        const roundsBoxTpl = $(roundsBoxTemplateSelector).html();

        roundWins.forEach(player => {
            const $box = $(roundsBoxTpl).clone();
            $box.find(gameOverRoundsName).text(player.name);
            $box.find(gameOverRoundsNumber).text(player.numberOfWins);
            boxNodeCollection.push($box);
        });

        return boxNodeCollection;
    }

    return {
        showUI,
        showModal,
        renderPlayer,
        updateDeckCount,
        showCard,
        resetCard,
        enableAttributeSelection,
        disableAttributeSelection,
        bindEndTurnEvent,
        unBindEndTurnEvent,
        setPlayerStateToDefeated,
        updateCommonPileIndicator,
        showMessage,
        displayActivePlayer,
        displayWinnerPlayer,
        clearPlayerStates,
        showOpponentsCards,
        highlightAttribute,
        resetAttributeHighlight,
        getStatsMarkup
    }
})(jQuery);