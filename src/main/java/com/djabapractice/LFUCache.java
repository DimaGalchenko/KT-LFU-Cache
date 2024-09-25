package com.djabapractice;

import java.util.Map;

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

    private /*final*/ int capacity;
    private /*final*/ Map<Integer, DoublyLinkedList> countToValues;  // Map to hold nodes for each frequency count
    private /*final*/ Map<K, Integer> keyToCount;  // Map to track frequency count of each key
    private /*final*/ Map<K, Node> keyToValue;  // Map to track the key-value pair nodes
    private int lfuCount;  // Tracks the current least frequency count in the cache

    /**
     * Constructor to initialize the LFU cache with a given capacity.
     *
     * @param capacity the maximum number of elements that can be stored in the cache
     * @throws IllegalArgumentException when capacity less or equal zero
     */
    public LFUCache(int capacity) {
        // TODO: Validate capacity
        // TODO: Initialize capacity and maps
    }

    /**
     * Retrieves the value associated with the given key from the cache.
     * If the key exists, its frequency count is incremented.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or null if the key does not exist in the cache
     */
    public V get(K key) {
        // TODO: Check if the key exists in the cache
        // TODO: Update the node's frequency count
        // TODO: Return the value associated with the key
        return null;
    }

    /**
     * Adds a new key-value pair to the cache. If the cache is full, it evicts the least frequently used key.
     * If the key already exists, its value is updated and its frequency count is incremented.
     *
     * @param key the key to be added or updated
     * @param value the value to be associated with the key
     */
    public void put(K key, V value) {
        // TODO: Check if the cache capacity is 0
        // TODO: Check if the key already exists and update it
        // TODO: Evict the least frequently used node if the cache is full
        // TODO: Add a new node to the cache with frequency 1
    }

    /**
     * Updates the frequency of a node by moving it from its current frequency list to the next higher one.
     *
     * @param node the node to be updated
     * @param currentCount the current frequency count of the node
     */
    private void updateNodeFrequency(Node node, int currentCount) {
        // TODO: Remove the node from the current frequency list
        // TODO: Increment the node's frequency and move it to the next list
        // TODO: Update the least frequently used count if necessary
    }

    /**
     * Adds a new key-value node to the cache with an initial frequency of 1.
     *
     * @param key the key of the new node
     * @param value the value of the new node
     */
    private void addNewNode(K key, V value) {
        // TODO: Create a new node
        // TODO: Add the node to the frequency list with count 1
        // TODO: Update the LFU count to 1 if this is the first node
    }

    /**
     * Evicts the least frequently used node from the cache.
     * In case of a tie, it evicts the least recently used node within the least frequent nodes.
     */
    private void evictLFUNode() {
        // TODO: Get the list of least frequently used nodes
        // TODO: Remove the least recently used node from the list
        // TODO: Update the keyToValue and keyToCount maps
    }

    /**
     * Node represents a single entry in the cache, holding a key-value pair and
     * pointers to the previous and next nodes in the linked list.
     */
    class Node {
        private K key;
        private V value;
        private Node next;
        private Node prev;

        /**
         * Constructor to create a new node with a given key and value.
         *
         * @param key the key of the node
         * @param value the value of the node
         */
        public Node(K key, V value) {
            // TODO: Initialize key and value
        }

        /**
         * Default constructor for creating head or tail dummy nodes.
         */
        public Node() {
            // TODO: Initialize dummy node
        }
    }

    /**
     * DoublyLinkedList is a helper class used to store nodes in order of usage.
     * It supports efficient addition and removal of nodes from both ends.
     */
    class DoublyLinkedList {
        private Node head; // should be final
        private Node tail; // should be final

        /**
         * Constructs an empty DoublyLinkedList with dummy head and tail nodes.
         */
        public DoublyLinkedList() {
            // TODO: Initialize the linked list with head and tail dummy nodes
        }

        /**
         * Removes a node from the linked list.
         *
         * @param node the node to be removed
         */
        public void removeNode(Node node) {
            // TODO: Remove the specified node from the list
        }

        /**
         * Adds a node to the front of the linked list (right after the head).
         *
         * @param node the node to be added
         */
        public void addToFront(Node node) {
            // TODO: Add the node right after the head
        }

        /**
         * Checks if the linked list is empty (i.e., only contains the dummy head and tail).
         *
         * @return true if the list is empty, false otherwise
         */
        public boolean isEmpty() {
            // TODO: Check if the list is empty
            return false;
        }

        /**
         * Removes and returns the last node in the list (right before the tail).
         *
         * @return the last node in the list, or null if the list is empty
         */
        public Node removeLast() {
            // TODO: Remove and return the last node in the list
            return null;
        }
    }
}
