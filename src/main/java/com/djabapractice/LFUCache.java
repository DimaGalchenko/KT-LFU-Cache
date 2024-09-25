package com.djabapractice;

import java.util.HashMap;
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

    private final int capacity;
    private final Map<Integer, DoublyLinkedList> countToValues;  // Map to hold nodes for each frequency count
    private final Map<K, Integer> keyToCount;  // Map to track frequency count of each key
    private final Map<K, Node> keyToValue;  // Map to track the key-value pair nodes
    private int lfuCount;  // Tracks the current least frequency count in the cache

    /**
     * Constructor to initialize the LFU cache with a given capacity.
     *
     * @param capacity the maximum number of elements that can be stored in the cache
     */
    public LFUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive non-zero value");
        }
        this.capacity = capacity;
        this.countToValues = new HashMap<>();
        this.keyToCount = new HashMap<>();
        this.keyToValue = new HashMap<>();
        this.lfuCount = 0;
    }

    /**
     * Retrieves the value associated with the given key from the cache.
     * If the key exists, its frequency count is incremented.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or null if the key does not exist in the cache
     */
    public V get(K key) {
        if (!keyToValue.containsKey(key)) {
            return null;
        }
        Node node = keyToValue.get(key);
        int count = keyToCount.get(key);

        // Move node to the higher frequency list
        updateNodeFrequency(node, count);

        return node.value;
    }

    /**
     * Adds a new key-value pair to the cache. If the cache is full, it evicts the least frequently used key.
     * If the key already exists, its value is updated and its frequency count is incremented.
     *
     * @param key the key to be added or updated
     * @param value the value to be associated with the key
     */
    public void put(K key, V value) {
        if (capacity == 0) return;  // Handle edge case of zero capacity cache

        if (keyToValue.containsKey(key)) {
            // Update existing node and its frequency
            Node node = keyToValue.get(key);
            node.value = value;
            get(key);  // Reuse the get() method to update frequency
        } else {
            if (keyToValue.size() == capacity) {
                // Evict least frequently used node
                evictLFUNode();
            }
            // Add new node with frequency 1
            addNewNode(key, value);
        }
    }

    /**
     * Updates the frequency of a node by moving it from its current frequency list to the next higher one.
     *
     * @param node the node to be updated
     * @param currentCount the current frequency count of the node
     */
    private void updateNodeFrequency(Node node, int currentCount) {
        // Remove node from the current frequency list
        DoublyLinkedList currentList = countToValues.get(currentCount);
        currentList.removeNode(node);

        // Increment frequency and update data structures
        int newCount = currentCount + 1;
        countToValues.computeIfAbsent(newCount, k -> new DoublyLinkedList()).addToFront(node);
        keyToCount.put(node.key, newCount);

        // Update the least frequently used count if necessary
        if (currentCount == lfuCount && currentList.isEmpty()) {
            lfuCount++;
        }
    }

    /**
     * Adds a new key-value node to the cache with an initial frequency of 1.
     *
     * @param key the key of the new node
     * @param value the value of the new node
     */
    private void addNewNode(K key, V value) {
        Node node = new Node(key, value);

        // Add node to frequency list 1
        countToValues.computeIfAbsent(1, k -> new DoublyLinkedList()).addToFront(node);
        keyToCount.put(key, 1);
        keyToValue.put(key, node);
        lfuCount = 1;  // Reset LFU count to 1 for new items
    }

    /**
     * Evicts the least frequently used node from the cache.
     * In case of a tie, it evicts the least recently used node within the least frequent nodes.
     */
    private void evictLFUNode() {
        // Get the list of the least frequently used nodes
        DoublyLinkedList lfuList = countToValues.get(lfuCount);

        // Remove the least recently used node in that list
        Node nodeToEvict = lfuList.removeLast();

        if (nodeToEvict != null) {
            keyToValue.remove(nodeToEvict.key);
            keyToCount.remove(nodeToEvict.key);
        }
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
            this.key = key;
            this.value = value;
        }

        /**
         * Default constructor for creating head or tail dummy nodes.
         */
        public Node() {
        }
    }

    /**
     * DoublyLinkedList is a helper class used to store nodes in order of usage.
     * It supports efficient addition and removal of nodes from both ends.
     */
    class DoublyLinkedList {
        private final Node head;
        private final Node tail;

        /**
         * Constructs an empty DoublyLinkedList with dummy head and tail nodes.
         */
        public DoublyLinkedList() {
            head = new Node();
            tail = new Node();
            head.next = tail;
            tail.prev = head;
        }

        /**
         * Removes a node from the linked list.
         *
         * @param node the node to be removed
         */
        public void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        /**
         * Adds a node to the front of the linked list (right after the head).
         *
         * @param node the node to be added
         */
        public void addToFront(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        /**
         * Checks if the linked list is empty (i.e., only contains the dummy head and tail).
         *
         * @return true if the list is empty, false otherwise
         */
        public boolean isEmpty() {
            return head.next == tail;
        }

        /**
         * Removes and returns the last node in the list (right before the tail).
         *
         * @return the last node in the list, or null if the list is empty
         */
        public Node removeLast() {
            if (isEmpty()) return null;

            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }
    }
}

