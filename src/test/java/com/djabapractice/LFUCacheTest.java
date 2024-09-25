package com.djabapractice;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class LFUCacheTest {

    private LFUCache<Integer, String> lfuCache;

    @Before
    public void setUp() {
        lfuCache = new LFUCache<>(3);
    }

    @Test
    public void testPutAndGetSingleItem() {
        lfuCache.put(1, "A");
        assertEquals("A", lfuCache.get(1));
    }

    @Test
    public void testGetNonExistentKey() {
        assertNull(lfuCache.get(100));
    }

    @Test
    public void testEvictionWhenFull() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");

        // All should be present
        assertEquals("A", lfuCache.get(1));
        assertEquals("B", lfuCache.get(2));
        assertEquals("C", lfuCache.get(3));

        // Add new element, should evict least frequently used (key 1, 2, or 3)
        lfuCache.put(4, "D");
        assertNull(lfuCache.get(1)); // Key 1 should be evicted since all have freq=1
        assertEquals("B", lfuCache.get(2)); // Key 2 should be present
        assertEquals("C", lfuCache.get(3)); // Key 3 should be present
        assertEquals("D", lfuCache.get(4)); // Key 4 should be present
    }

    @Test
    public void testEvictionBasedOnFrequency() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");

        lfuCache.get(1);  // Increase freq of key 1
        lfuCache.get(1);  // Increase freq of key 1

        // Add new element, should evict the least frequently used (key 2 or 3)
        lfuCache.put(4, "D");
        assertNull(lfuCache.get(2)); // Key 2 should be evicted
        assertEquals("A", lfuCache.get(1)); // Key 1 should still be present
        assertEquals("C", lfuCache.get(3)); // Key 3 should still be present
        assertEquals("D", lfuCache.get(4)); // Key 4 should be present
    }

    @Test
    public void testFrequencyIncrease() {
        lfuCache.put(1, "A");
        lfuCache.get(1);  // Freq of key 1 should now be 2

        lfuCache.put(2, "B");
        lfuCache.put(3, "C");
        lfuCache.put(4, "D");  // This should evict key 2 (lowest frequency)

        assertNull(lfuCache.get(2)); // Key 2 should be evicted
        assertEquals("A", lfuCache.get(1)); // Key 1 should still be present
        assertEquals("C", lfuCache.get(3)); // Key 3 should still be present
        assertEquals("D", lfuCache.get(4)); // Key 4 should be present
    }

    @Test
    public void testZeroCapacityCache() {
        LFUCache<Integer, String> zeroCapacityCache = new LFUCache<>(0);

        zeroCapacityCache.put(1, "A");
        assertNull(zeroCapacityCache.get(1)); // Nothing should be stored
    }

    @Test
    public void testUpdateValue() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");

        lfuCache.put(1, "AA");  // Update key 1
        assertEquals("AA", lfuCache.get(1)); // Value should be updated
        assertEquals("B", lfuCache.get(2));  // Key 2 should still be present
    }

    @Test
    public void testEvictLeastFrequentlyUsedWithTie() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");

        lfuCache.get(1);  // Increase freq of key 1
        lfuCache.get(1);

        // Now all other keys have the same frequency, add another key
        lfuCache.put(4, "D");

        assertNull(lfuCache.get(2)); // Key 2 should be evicted
        assertEquals("A", lfuCache.get(1)); // Key 1 should be present
        assertEquals("C", lfuCache.get(3)); // Key 3 should be present
        assertEquals("D", lfuCache.get(4)); // Key 4 should be present
    }

    @Test
    public void testMultipleInsertsAndEvictions() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");
        lfuCache.put(4, "D");  // Should evict least frequently used

        assertNull(lfuCache.get(1)); // Key 1 should be evicted
        assertEquals("B", lfuCache.get(2));
        assertEquals("C", lfuCache.get(3));
        assertEquals("D", lfuCache.get(4));
    }

    @Test
    public void testEvictionAfterUpdate() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");

        lfuCache.get(1);
        lfuCache.put(1, "AA");  // Update key 1

        lfuCache.put(4, "D");  // Should evict least frequently used

        assertNull(lfuCache.get(2)); // Key 2 should be evicted
        assertEquals("AA", lfuCache.get(1)); // Key 1 should still be present
        assertEquals("C", lfuCache.get(3)); // Key 3 should still be present
        assertEquals("D", lfuCache.get(4)); // Key 4 should be present
    }

    @Test
    public void testEvictOldestWhenSameFrequency() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");

        lfuCache.get(1);  // Freq of key 1 becomes 2
        lfuCache.get(2);  // Freq of key 2 becomes 2

        lfuCache.put(4, "D");  // Should evict key 3 (oldest among freq=1)

        assertNull(lfuCache.get(3)); // Key 3 should be evicted
        assertEquals("A", lfuCache.get(1));
        assertEquals("B", lfuCache.get(2));
        assertEquals("D", lfuCache.get(4));
    }

    @Test
    public void testGetOnEmptyCache() {
        LFUCache<Integer, String> emptyCache = new LFUCache<>(3);
        assertNull(emptyCache.get(1)); // Empty cache should return null
    }

    @Test
    public void testCapacityOneCache() {
        LFUCache<Integer, String> singleCapacityCache = new LFUCache<>(1);

        singleCapacityCache.put(1, "A");
        assertEquals("A", singleCapacityCache.get(1));

        singleCapacityCache.put(2, "B");  // Should evict key 1
        assertNull(singleCapacityCache.get(1));
        assertEquals("B", singleCapacityCache.get(2));
    }

    @Test
    public void testFrequencyIncreaseOnMultipleGets() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");

        lfuCache.get(1);  // Freq of key 1 increases
        lfuCache.get(1);  // Freq of key 1 increases

        lfuCache.put(3, "C");
        lfuCache.put(4, "D");  // Should evict key 2

        assertNull(lfuCache.get(2)); // Key 2 should be evicted
        assertEquals("A", lfuCache.get(1)); // Key 1 should still be present
        assertEquals("C", lfuCache.get(3)); // Key 3 should still be present
        assertEquals("D", lfuCache.get(4)); // Key 4 should be present
    }

    @Test
    public void testKeyUpdateResetsFrequency() {
        lfuCache.put(1, "A");
        lfuCache.get(1);  // Freq of key 1 increases

        lfuCache.put(1, "AA");  // Update key 1's value
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");
        lfuCache.put(4, "D");  // Should evict key 2

        assertNull(lfuCache.get(2)); // Key 2 should be evicted
        assertEquals("AA", lfuCache.get(1)); // Updated key 1 should still be present
        assertEquals("C", lfuCache.get(3)); // Key 3 should still be present
        assertEquals("D", lfuCache.get(4)); // Key 4 should be present
    }

    @Test
    public void testEdgeCaseForCapacityZero() {
        LFUCache<Integer, String> zeroCapacityCache = new LFUCache<>(0);
        zeroCapacityCache.put(1, "A");

        assertNull(zeroCapacityCache.get(1)); // No items should be present
    }

    @Test
    public void testEvictionOrderWithDifferentFrequencies() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");

        lfuCache.get(1);  // Freq of key 1 increases
        lfuCache.get(1);  // Freq of key 1 increases
        lfuCache.get(2);  // Freq of key 2 increases

        lfuCache.put(4, "D");  // Should evict key 3

        assertNull(lfuCache.get(3)); // Key 3 should be evicted
        assertEquals("A", lfuCache.get(1)); // Key 1 should be present
        assertEquals("B", lfuCache.get(2)); // Key 2 should be present
        assertEquals("D", lfuCache.get(4)); // Key 4 should be present
    }
}
