const StatsHelper = (($) => {
    let numberOfRounds = 0;
    let numberOfDraws = 0;
    let finalWinner = {};
    const roundWins = [];

    const init = players => {
        $.each(players, (i, player) => {
            roundWins.push({
                id: player.id,
                name: player.name,
                numberOfWins: 0,
           });
        });
    };

    const incrementRoundNumber = winner => {
        if (winner === null) {
            finalWinner = null;
            numberOfDraws++;
        } else {
            finalWinner = winner;
            const winnerIndex = roundWins.findIndex(rw => {
                return rw.id === winner.id
            })
            roundWins[winnerIndex].numberOfWins++;
        }

        numberOfRounds++;
    };

    const getGameStats = () => {
        return {
            numberOfDraws,
            numberOfRounds,
            finalWinner,
            roundWins
        }
    };

    return {
        init,
        getGameStats,
        incrementRoundNumber,
    }
})(jQuery);