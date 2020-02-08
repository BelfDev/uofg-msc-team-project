package com.toptrumps.db;
import java.sql.ResultSet;

public interface PersistentDataDAO {
    void update();
    Statistics getStatistics();
}