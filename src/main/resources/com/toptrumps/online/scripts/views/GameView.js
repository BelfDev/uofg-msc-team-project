/**
 * View for game screen
 */

const GameView = (($) => {
    /** VARIABLES AND CONSTANTS */

    // Selectors variables
    const pageCurtainSelector = ".js-page-curtain";
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
    const attributeSelectedClass = "human-player--attr-selected";
    const endTurnButtonActiveClass = "end-turn-button-active";
    const commonPileActiveClass = "heartbeat";

    // Templates
    const playerBoxTemplateSelector = "#template-player-box";
    const cardAttributeTemplateSelector = "#template-attribute-item";
    const gameOverTemplateSelector = "#template-game-over";
    const roundsBoxTemplateSelector = "#template-rounds-box";

    // Timer and duration variables
    const timerBase = window.APP.TIMER_BASE;
    const curtainDelay = window.APP.STARTUP_DELAY;
    const cardAnimation = {
        scaleDelay: timerBase / 4,
        rotateDelay: timerBase / 5,
        duration: timerBase / 2.5
    };


    /** METHODS */

    /**
     * Initializes plugins required for UI and removes initial black screen
     */
    const showUI = async () => {
        // Delay before showing UI
        await delay(curtainDelay);

        // Animation to remove black screen
        anime({
            targets: pageCurtainSelector,
            opacity: '0',
            easing: 'cubicBezier(0.895, 0.030, 0.685, 0.220)',
            complete: (anim) => {
                $(anim.animatables[0].target).remove();
            }
        });

    };

    /**
     * Passes text to the Message plugin and animates text reveal
     * @param message
     */
    const showMessage = message => {
        $(messageLogSelector).fadeOut("fast", () => {
            $(messageLogSelector).html(replaceUnderscore(message));
            $(messageLogSelector).show();
            Message.animateLog(messageLogSelector);
        });
    };

    /**
     * Creates a pause in code execution
     * @param n milliseconds
     * @returns {Promise}
     */
    const delay = n => {
        n = n || 2000;
        return new Promise(done => {
            setTimeout(() => {
                done();
            }, n);
        });
    };

    /**
     * Reveals modal window with specific ID and configurable title and text
     * @param selectorID - modal selector, matches one of the modalSelectors properties
     * @param title - modal title
     * @param hint - additional text
     */
    const showModal = (selectorID, title, hint) => {
        const targetModalSelector = modalSelectors[selectorID];

        Modal.openModal(targetModalSelector, title, hint);
    };

    /**
     * Creates player box and adds name and deck count
     * @param player - player object
     */
    const renderPlayer = player => {
        const playerTpl = $(playerBoxTemplateSelector).html();
        const playerNode = $(playerTpl).clone();

        playerNode.data("player-id", player.id);
        playerNode.find(playerNameSelector).text(replaceUnderscore(player.name));
        playerNode.find(playerDeckCountSelector).text(getDeckCountMessage(player.deck.length));

        $(opponentsBoxSelector).append(playerNode);
    };

    /**
     * Returns a string with count of cards left
     * @param deckCount
     * @returns {string}
     */
    const getDeckCountMessage = deckCount => {
        return `${deckCount} cards left`
    };

    /**
     * Changes deck count for target player
     * @param playerID
     * @param deckCount
     */
    const updateDeckCount = (playerID, deckCount) => {
        const $playerObj = getPlayerByID(playerID);

        $playerObj
            .find(playerDeckCountSelector)
            .text(getDeckCountMessage(deckCount));
    };

    /**
     * Finds player element by ID
     * @param playerID
     * @returns {jQuery|HTMLElement} player
     */
    const getPlayerByID = (playerID) => {
        if (playerID === 0) return $(humanPlayerSelector);

        return $(playerSelector).filter(function() {
            return $(this).data("playerId") === playerID
        });
    };

    /**
     * Reveal card of player selected by ID
     * @param playerID
     * @param card
     */
    const showCard = (playerID, card) => {
        const $playerObj = getPlayerByID(parseInt(playerID));
        const $card = $playerObj.find(cardSelector);

        setCardImage($playerObj, card.name);
        setCardTitle($playerObj, card.name);
        setCardAttributes($playerObj, card.attributes);

        animateCard($card, false);
    };

    /**
     * Sets card image
     * @param $playerObj
     * @param cardName
     */
    const setCardImage = ($playerObj, cardName) => {
        const request = getImagePath(cardName);
        request.then((imagePath) => {
            $playerObj
                .find(cardImageSelector)
                .attr("src", imagePath)
                .attr("title", cardName);
        });
    };

    /**
     * Replaces underscore in given string
     * @param string
     * @returns string
     */
    const replaceUnderscore = string => {
        return string.replace(/_/g, ' ');
    };

    /**
     * Makes request to the image path and if it's not available
     * returns placeholder image. Otherwise return requested image
     * Assumes that every image corresponds to the initial card name
     * @param cardName
     * @returns string - relative path to the image
     */
    const getImagePath = async cardName => {
        const imagePath = `/assets/images/cards/${cardName}.png`;
        const result = await NetworkHelper.checkIfResourceExists(imagePath);
        let finalPath = `/assets/images/cards/Default.png`;

        if (result) finalPath = imagePath;

        return finalPath;
    };

    /**
     * Sets card title
     * @param $playerObj
     * @param cardName
     */
    const setCardTitle = ($playerObj, cardName) => {
        $playerObj
            .find(cardTitleSelector)
            .html(replaceUnderscore(cardName));
    };

    /**
     * Sets card attributes
     * @param $playerObj
     * @param attributes - object with a list of attributes
     */
    const setCardAttributes = ($playerObj, attributes) => {
        const attrNodeCollection = createAttributesNodes(attributes);

        $playerObj
            .find(cardAttributesWrapperSelector)
            .html(attrNodeCollection);
    };

    /**
     * Gets the template for attributes and creates a node for each element in the given
     * attributes object. All nodes added to the collection array and returned
     * @param attributes
     * @returns {[jQuery|HTMLElement]} array of nodes
     */
    const createAttributesNodes = attributes => {
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

    /**
     * Flips the card
     * @param $card
     * @param hide - boolean
     */
    const animateCard = ($card, hide) => {
        $card.toggleClass(cardActiveClass, hide === true);

        // If test mode enabled - no animation required
        if (window.APP.TEST_MODE) {
            const transformValue = 'rotateY(-180deg)';
            $card.css('transform', transformValue);
        } else {
            const transformValue = hide === true ? '0deg' : '-180deg';
            anime({
                targets: $card[0],
                scale: [{value: 1}, {value: 1.3}, {value: 1, delay: cardAnimation.scaleDelay}],
                rotateY: {value: transformValue, delay: cardAnimation.rotateDelay},
                easing: "easeInOutSine",
                duration: cardAnimation.duration
            });
        }
    };

    /**
     * Adds event handlers for attributes when human player is active
     * @param attributeCallback - callback to run after attribute selection
     * @param humanPlayerID
     * @param endTurnCallback - callback to run after end turn button click
     */
    const enableAttributeSelection = (attributeCallback, humanPlayerID, endTurnCallback) => {
        const $playerObj = getPlayerByID(humanPlayerID);
        const $attributes = getAttributes($playerObj);
        const $attributesWrapper = getAttributesWrapper($playerObj);

        $attributesWrapper.addClass(cardAttributesWrapperActiveClass);

        $($attributes)
            .off("click") // remove previous click events
            .on("click", event => {
                const $target = $(event.currentTarget);

                // Apply classes to indicate that player selected an attribute
                $(humanPlayerSelector).addClass(attributeSelectedClass);
                $(endTurnButtonSelector).addClass(endTurnButtonActiveClass);
                $($attributes).removeClass(cardAttributeActiveClass);
                $target.addClass(cardAttributeActiveClass);

                const attrName = $target.data("attribute");
                const attrValue = $target.find(".js-card-char-value").text();

                // Enable end turn button
                bindEndTurnEvent(endTurnCallback);

                attributeCallback(attrName, attrValue);
            });
    };

    /**
     * Changes state of defeated players
     */
    const displayRemovedPlayers = removedPlayerIDs => {
        removedPlayerIDs.forEach(playerID => {
            GameView.setPlayerStateToDefeated(playerID);
            GameView.updateDeckCount(playerID, 0);
        });
    };

    /**
     * Finds attributes element for player
     * @param $playerObj
     * @returns {jQuery|HTMLElement}
     */
    const getAttributes = $playerObj => {
        return $playerObj.find(cardAttributeSelector);
    };

    /**
     * Find attributes box for player
     * @param $playerObj
     * @returns {jQuery|HTMLElement}
     */
    const getAttributesWrapper = $playerObj => {
        return $playerObj.find(cardAttributesWrapperSelector);
    };

    /**
     * Disables attribute selection when human player is active
     * @param humanPlayerID
     */
    const disableAttributeSelection = humanPlayerID => {
        const $playerObj = getPlayerByID(humanPlayerID);
        const $attributes = getAttributes($playerObj);
        const $attributesWrapper = getAttributesWrapper($playerObj);

        $attributesWrapper.removeClass(cardAttributesWrapperActiveClass);

        $($attributes).off("click");
    };

    /**
     * Disables end turn button by removing event handler
     */
    const unBindEndTurnEvent = () => {
        $(endTurnButtonSelector).off("click");
    };

    /**
     * Enables end turn button by adding event handler and runs
     * callback after click on button
     * @param callback
     */
    const bindEndTurnEvent = callback => {
        $(endTurnButtonSelector).off("click").on("click", e => {
            $(endTurnButtonSelector).removeClass(endTurnButtonActiveClass);

            callback();

            // Prevents default button behaviour
            e.preventDefault();
        });
    };

    /**
     * Changes visual appearance of the player to defeated
     * @param playerID
     */
    const setPlayerStateToDefeated = playerID => {
        const $playerObj = getPlayerByID(playerID);

        $playerObj.addClass(defeatedPlayerClass);

        resetCard(playerID);
    };

    /**
     * Flips card back to the "hidden" state
     * @param playerID
     */
    const resetCard = playerID => {
        const $playerObj = getPlayerByID(playerID);
        const $card = $playerObj.find(cardSelector);

        animateCard($card,true);
    };

    /**
     * Updates card count in common pile
     * @param count
     */
    const updateCommonPileIndicator = count => {
        const prevCount = parseInt($(commonPileValueSelector).text());

        // If count is changed - animate
        if (prevCount !== count) {
            $(commonPileValueSelector).text(count);

            animateCommonPile();
        }
    };

    /**
     * Animates common pile text
     */
    const animateCommonPile = async () => {
        $(commonPileSelector).addClass(commonPileActiveClass);
        await delay(timerBase * 2);
        $(commonPileSelector).removeClass(commonPileActiveClass);
    };

    /**
     * Sets every player to initial state
     */
    const clearPlayerStates = () => {
        $(humanPlayerSelector).removeClass(attributeSelectedClass);
        $(endTurnButtonSelector).removeClass(endTurnButtonActiveClass);
        $(playerSelector).removeClass(activePlayerClass);
        $(playerSelector).removeClass(winnerPlayerClass);
    };

    /**
     * Sets active state for target player
     * @param playerID
     */
    const displayActivePlayer = playerID => {
        const $playerObj = getPlayerByID(playerID);

        $playerObj.addClass(activePlayerClass);
    };

    /**
     * Sets winner state for target player
     * @param playerID
     */
    const displayWinnerPlayer = playerID => {
        const $playerObj = getPlayerByID(playerID);

        $playerObj.addClass(winnerPlayerClass);
    };

    /**
     * Reveals cards for every opponent with a small delay
     * @param cardsOnTable - array with all cards currently in play
     */
    const showOpponentsCards = cardsOnTable => {
        cardsOnTable.forEach((data, index) => {
            if (data.playerID === 0) return; // skip human player's card

            // For each loops work unstable with promises
            // Switch to default timeouts, as it does not
            // affect gameplay
            setTimeout(() => {
                showCard(data.playerID, data.card);
            }, (timerBase / 2) * index);
        });
    };

    /**
     * Highlights winning attribute
     * @param attribute
     */
    const highlightAttribute = attribute => {
        $(cardAttributeSelector).filter(function() {
            return $(this).data("attribute") === attribute.name
        }).addClass(cardAttributeActiveClass);
    };

    /**
     * Removes attribute highlighting
     */
    const resetAttributeHighlight = () => {
        $(cardAttributeSelector).removeClass(cardAttributeActiveClass);
    };

    /**
     * Preparse markup for stats display at the end of the game
     * @param stats
     * @returns {[jQuery|HTMLElement]}
     */
    const getStatsMarkup = stats => {
        const gameStatsTpl = $(gameOverTemplateSelector).html();
        const $gameStats = $(gameStatsTpl).clone();

        // If there's a winner - shows his name. Otherwise hides the winner block
        if (stats.finalWinner !== null) {
            const winnerName = replaceUnderscore(stats.finalWinner.name);
            $gameStats.find(gameOverWinnerNameSelector).text(winnerName);
        } else {
            $gameStats.find(gameOverWinnerSelector).hide();
        }

        // Gets rounds details markup
        const $roundsWrapper = $gameStats.find(gameOverRoundsWrapperSelector);
        $roundsWrapper.html(createRoundsBoxNodes(stats.roundWins));

        return $gameStats;
    };

    /**
     * Creates nodes for stats (rounds won)
     * @param roundWins - array with each player's stats
     * @returns {[jQuery|HTMLElement]}
     */
    const createRoundsBoxNodes = roundWins => {
        const boxNodeCollection = [];
        const roundsBoxTpl = $(roundsBoxTemplateSelector).html();

        roundWins.forEach(player => {
            const $box = $(roundsBoxTpl).clone();
            $box.find(gameOverRoundsName).text(replaceUnderscore(player.name));
            $box.find(gameOverRoundsNumber).text(player.numberOfWins);
            boxNodeCollection.push($box);
        });

        return boxNodeCollection;
    };

    /**
     * Displays draw animation
     */
    const displayDraw = () => {
        Modal.showBackdrop();

        anime({
            targets: '.draw-indicator',
            keyframes: [
                { scale: 0, translateX: '-50%', translateY: '-50%', opacity: 0 },
                { scale: 1, translateX: '-50%', translateY: '-50%', opacity: 1 }
            ],
            duration: timerBase / 2,
            easing: 'easeOutElastic(1, .8)',
            loop: false,
            complete: async () => {
                await delay(timerBase);
                anime({
                    targets: '.draw-indicator',
                    opacity: 0,
                    duration: timerBase
                });
                Modal.removeBackdrop();
            }
        });
    };

    /**
     * Creates markup for every player
     * @param humanPlayer
     * @param aiPlayers
     */
    const createPlayers = (humanPlayer, aiPlayers) => {
        // Human player markup is already created, just update the card count
        GameView.updateDeckCount(humanPlayer.id, humanPlayer.deck.length);

        aiPlayers.forEach(player => {
            GameView.renderPlayer(player);
        });
    };

    /**
     * Resets round visual state
     */
    const resetRound = players => {
        $.each(players, (i, player) => {
            resetCard(player.id);
        });

        resetAttributeHighlight();
        clearPlayerStates();
        Modal.closeActiveModal();
    };


    /** EXPOSE PUBLIC METHODS **/

    return {
        bindEndTurnEvent,
        clearPlayerStates,
        createPlayers,
        delay,
        disableAttributeSelection,
        displayActivePlayer,
        displayDraw,
        displayWinnerPlayer,
        displayRemovedPlayers,
        enableAttributeSelection,
        getStatsMarkup,
        highlightAttribute,
        renderPlayer,
        resetAttributeHighlight,
        resetCard,
        resetRound,
        setPlayerStateToDefeated,
        showCard,
        showMessage,
        showModal,
        showOpponentsCards,
        showUI,
        unBindEndTurnEvent,
        updateCommonPileIndicator,
        updateDeckCount,
    }
})(jQuery);