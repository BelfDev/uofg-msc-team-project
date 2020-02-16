package com.toptrumps.db;
import com.toptrumps.core.statistics.GameStateCollector;
import java.sql.*;

interface GameDAO {
    boolean create(GameStateCollector gameState);
    ResultSet retrieve();
}