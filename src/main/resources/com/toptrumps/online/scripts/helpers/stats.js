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

    const outputStats = winnerID => {
        let text = "";

        roundWins.forEach(player => {
            if (player.id == winnerID) {
                if (player.id == 0) {
                    text += `The winner is You!. You won ${player.numberOfWins} rounds<br />`
                } else {
                    text += `The winner is ${player.name}. They won ${player.numberOfWins} rounds<br />`;
                }
            } else {
                text += `${player.name} lost overall, but won ${player.numberOfWins} rounds<br />`;
            }
        });

        return text;
    };

    return {
        init,
        getGameStats,
        incrementRoundNumber,
        outputStats
    }
})(jQuery);