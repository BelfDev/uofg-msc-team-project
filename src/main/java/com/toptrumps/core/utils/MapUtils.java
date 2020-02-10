package com.toptrumps.core.utils;

import java.util.Map;

public class MapUtils {

    private MapUtils() {
    }

    // Increment a Map Integer value
    public static <K> void increment(Map<K, Integer> map, K key) {
        map.merge(key, 1, Integer::sum);
    }

}
