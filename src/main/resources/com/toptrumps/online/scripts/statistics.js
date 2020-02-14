$(function() {
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

    const templateStatsRowSelector = "#template-stats-row";

    const createRow = performanceHistory => {
        const rowNodes = [];
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

        rowNodes.push(rowNode);

        return rowNodes;
    }

    const getPlayerRoundWins = (roundWins, playerName) => {
        return Object.keys(roundWins).find(player => {
            if (player == playerName) {
                const val = roundWins[player];
                return val >= 0;
            }
        })
    }

    NetworkHelper.makeRequest("api/statistics", false, "GET").then(response => {
        $(statsGamesPlayedSelector).text(response.numberOfGames);
        $(statsAIWinsSelector).text(response.numberOfAIWins);
        $(statsHumanWinsSelector).text(response.numberOfHumanWins);
        $(statsAverageDrawsSelector).text(response.numberOfAverageDraws);
        $(statsRoundsRecordSelector).text(response.numberOfMaxRounds);

        $.each(response.performanceHistory, (index, record) => {
            const rowNodes = createRow(record);
            $(statsBodySelector).prepend(rowNodes);
        });
    });

    InputNumber.init(inputNumberSelector);
    $(newGameButtonSelector).on("click", () => {
        DOMHelper.showModal("ASK_FOR_NUMBER_OF_OPPONENTS", false, false)
    });
});