package com.toptrumps.core.engine;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;

public interface GameEventListener {

    Attribute onRequestSelection(Card card);
    void onRoundEnd(RoundOutcome outcome);

}
