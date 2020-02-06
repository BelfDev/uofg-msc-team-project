package com.toptrumps.core.engine;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.player.Player;

import java.util.ArrayList;

public interface GameLifeCycle {

    void onRoundStart(final Player activePlayer, final Card humanPlayerCard, final int roundNumber);
    Attribute onRequestSelection(final Card card);
    void onAttributeSelected(final Player activePlayer);
    void onRoundEnd(final RoundOutcome outcome);
    void onGameOver(Player winner);

}
