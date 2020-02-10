const StatsHelper = (($) => {
    let roundNumber = 0;
    const playerStats = {};

    const init = players => {
        $.each(players, (i, player) => {
            playerStats[player.id] = {
               rounds: 0,
               name: player.name
           };
        });
    };

    const incrementRoundNumber = () => {
        roundNumber++;
    };

    const addRoundToPlayerByID = playerID => {
        playerStats[playerID].rounds++;
    };

    const outputStats = winnerID => {
        let text = "";

        $.each(playerStats, (playerID, info) => {
            if (playerID == winnerID) {
                if (playerID == 0) {
                    text += `The winner is You!. You won ${info.rounds} rounds<br />`
                } else {
                    text += `The winner is ${info.name}. They won ${info.rounds} rounds<br />`;
                }
            } else {
                text += `${info.name} lost overall, but won ${info.rounds} rounds<br />`;
            }
        });

        return text;
    };

    return {
        init,
        addRoundToPlayerByID,
        incrementRoundNumber,
        outputStats
    }
})(jQuery);