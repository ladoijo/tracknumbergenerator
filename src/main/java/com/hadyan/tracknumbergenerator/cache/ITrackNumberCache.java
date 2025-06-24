package com.hadyan.tracknumbergenerator.cache;

public interface ITrackNumberCache {
    void put(String key, String value);

    String get(String key);

    void delete(String key);

    boolean exists(String key);
}
