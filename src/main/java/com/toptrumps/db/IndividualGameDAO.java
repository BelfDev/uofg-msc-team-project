package com.toptrumps.db;

import com.toptrumps.core.statistics.GameStateCollector;

public interface IndividualGameDAO {
    boolean create(GameStateCollector gameState);
}