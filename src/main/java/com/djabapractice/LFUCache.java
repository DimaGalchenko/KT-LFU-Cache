package com.djabapractice;

/**
 * LFUCache is an implementation of the Least Frequently Used (LFU) cache eviction policy.
 * It tracks the frequency of usage for each key and evicts the least frequently used key
 * when the cache reaches its capacity. In case of a tie in frequency, the least recently used
 * key is evicted.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
public class LFUCache<K, V> {

    /**
     * Constructor to initialize the LFU cache with a given capacity.
     *
     * @param capacity the maximum number of elements that can be stored in the cache
     */
    public LFUCache(int capacity) {

    }

    /**
     * Retrieves the value associated with the given key from the cache.
     * If the key exists, its frequency count is incremented.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or null if the key does not exist in the cache
     */
    public V get(K key) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Adds a new key-value pair to the cache. If the cache is full, it evicts the least frequently used key.
     * If the key already exists, its value is updated and its frequency count is incremented.
     *
     * @param key the key to be added or updated
     * @param value the value to be associated with the key
     */
    public void put(K key, V value) {
        throw new RuntimeException("Not implemented");
    }
}
