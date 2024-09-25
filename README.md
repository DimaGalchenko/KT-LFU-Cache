# LFU Cache Implementation

## Task

Your task is to implement a **Least Frequently Used (LFU)** cache in Java. The LFU cache should efficiently store and retrieve data based on the frequency of use. When the cache reaches its capacity, it should evict the least frequently used key-value pair. If multiple keys have the same frequency, the least recently used key should be evicted.

### Requirements:
- The LFU Cache should support the following operations:
    - `get(K key)`: Retrieve the value of the key if the key exists in the cache; otherwise, return `null`.
    - `put(K key, V value)`: Add a key-value pair to the cache. If the cache reaches its capacity, evict the least frequently used item. If multiple items are least frequently used, evict the least recently used one.

### Constraints:
- The cache should have a fixed capacity, specified during initialization.
- All operations (`get` and `put`) should have a time complexity of **O(1)**.

