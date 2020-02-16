/**
 * Statistics model
 * Manipulates statistic data
 */

const StatsModel = (($) => {
    // Model variables
    let numberOfRounds = 0;
    let numberOfDraws = 0;
    let finalWinner = {};
    const roundWins = [];


    /** METHODS **/

    /**
     * Initiate model data
     * @param players
     */
    const init = players => {
        $.each(players, (i, player) => {
            roundWins.push({
                id: player.id,
                name: player.name,
                numberOfWins: 0,
           });
        });
    };

    /**
     * Increments round number
     * @param winner
     */
    const incrementRoundNumber = () => {
        numberOfRounds++;
    };

    /**
     * Sets current winner and increments his number of wins
     * @param winner
     */
    const setWinner = winner => {
        incrementRoundNumber();

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
    }

    /**
     * Return game stats
     * @returns {{numberOfDraws: number, roundWins: [], numberOfRounds: number, finalWinner: {}}}
     */
    const getGameStats = () => {
        // If no final winner after last turn, but there is a player left with cards
        // set him as a game winner
        if (finalWinner == null) {
            finalWinner = GameModel.getPlayerWithMostCards();
        }

        return {
            numberOfDraws,
            numberOfRounds,
            finalWinner,
            roundWins
        }
    };


    /** EXPOSE PUBLIC METHODS **/

    return {
        init,
        getGameStats,
        setWinner,
    }
})(jQuery);