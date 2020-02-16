/**
 * View for selection screen
 */

const StatisticsView = (($) => {
    /** VARIABLES AND CONSTANTS */
    const statsPage = ".js-statistics-box";
    const statsGamesPlayedSelector = ".js-stats-games-played-value";
    const statsAIWinsSelector = ".js-stats-ai-wins-value";
    const statsHumanWinsSelector = ".js-stats-human-wins-value";
    const statsAverageDrawsSelector = ".js-stats-average-draws-value";
    const statsRoundsRecordSelector = ".js-stats-rounds-record-value";
    const statsBodySelector = ".js-stats-body";
    const rowGameIDValueSelector = ".js-game-id-value";
    const rowWinnerNameSelector = ".js-winner-name";
    const rowRoundsValueSelector = ".js-total-rounds-value";
    const rowAverageDrawsValueSelector = ".js-stats-average-draws-value";
    const rowHumanWinsSelector = ".js-human-wins";
    const rowAIOneWinsSelector = ".js-aione-wins";
    const rowAITwoWinsSelector = ".js-aitwo-wins";
    const rowAIThreeWinsSelector = ".js-aithree-wins";
    const rowAIFourWinsSelector = ".js-aifour-wins";
    const inputNumberSelector = ".js-opponents-quantity";
    const newGameButtonSelector = ".js-modal-new-game-button";

    // Templates
    const templateStatsRowSelector = "#template-stats-row";

    // Modals
    const modalSelectors = {
        ASK_FOR_NUMBER_OF_OPPONENTS: ".js-new-game-modal",
    };

    /** METHODS */

    /**
     * Initialization
     */
    const init = () => {
       bindEvents();
    };

    /**
     * Event binding
     */
    const bindEvents = () => {
        $(newGameButtonSelector).on("click", () => {
            showModal("ASK_FOR_NUMBER_OF_OPPONENTS", false, false);
        });
    }

    /**
     * Render stats based on provided data
     * @param data
     */
    const renderStats = data => {
        $(statsGamesPlayedSelector).text(data.numberOfGames);
        $(statsAIWinsSelector).text(data.numberOfAIWins);
        $(statsHumanWinsSelector).text(data.numberOfHumanWins);
        $(statsAverageDrawsSelector).text(data.numberOfAverageDraws);
        $(statsRoundsRecordSelector).text(data.numberOfMaxRounds);

        $.each(data.performanceHistory, (index, record) => {
            const rowNodes = createRow(record);
            $(statsBodySelector).prepend(rowNodes);
        });
    }

    /**
     * Create row node for data item
     * @param performanceHistory
     * @returns {[]}
     */
    const createRow = performanceHistory => {
        const rowTpl = $(templateStatsRowSelector).html();
        const rowNode = $(rowTpl).clone();
        const roundWins = performanceHistory.roundWins;

        $(rowNode).find(rowGameIDValueSelector).text(performanceHistory.gameID);
        $(rowNode).find(rowWinnerNameSelector).text(performanceHistory.winnerID);
        $(rowNode).find(rowRoundsValueSelector).text(performanceHistory.roundsPlayed);
        $(rowNode).find(rowAverageDrawsValueSelector).text(performanceHistory.draws);

        if (Object.keys(roundWins).length > 0) {
            $(rowNode).find(rowHumanWinsSelector).text(getPlayerRoundWins(roundWins, "Human"));
            $(rowNode).find(rowAIOneWinsSelector).text(getPlayerRoundWins(roundWins, "AiOne"));
            $(rowNode).find(rowAITwoWinsSelector).text(getPlayerRoundWins(roundWins, "AiTwo"));
            $(rowNode).find(rowAIThreeWinsSelector).text(getPlayerRoundWins(roundWins, "AiThree"));
            $(rowNode).find(rowAIFourWinsSelector).text(getPlayerRoundWins(roundWins, "AiFour"));
        }

        return rowNode;
    }

    /**
     * Get round wins number based on player name
     * @param roundWins
     * @param playerName
     * @returns {Integer}
     */
    const getPlayerRoundWins = (roundWins, playerName) => {
        const key = Object.keys(roundWins).find(player => {
            if (player == playerName) {
                const val = roundWins[player];
                return val >= 0;
            }
        });
        return roundWins[key];
    }

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
     * Show loader for statistics box
     */
    const showLoader = () => {
        const $target = $(statsPage);
        Loader.showLoader($target, "Loading statistics. Please wait...");
    };

    /**
     * Removes active loader
     */
    const removeLoader = () => {
        const $target = $(statsPage);
        Loader.removeLoader($target);
    };

    /** EXPOSE PUBLIC METHODS **/
    return {
        init,
        removeLoader,
        renderStats,
        showLoader,
    }
})(jQuery);