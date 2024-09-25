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

        assertEquals("A", lfuCache.get(1));
        assertEquals("B", lfuCache.get(2));
        assertEquals("C", lfuCache.get(3));

        lfuCache.put(4, "D");
        assertNull(lfuCache.get(1));
        assertEquals("B", lfuCache.get(2));
        assertEquals("C", lfuCache.get(3));
        assertEquals("D", lfuCache.get(4));
    }

    @Test
    public void testEvictionBasedOnFrequency() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");

        lfuCache.get(1);
        lfuCache.get(1);

        lfuCache.put(4, "D");
        assertNull(lfuCache.get(2));
        assertEquals("A", lfuCache.get(1));
        assertEquals("C", lfuCache.get(3));
        assertEquals("D", lfuCache.get(4));
    }

    @Test
    public void testFrequencyIncrease() {
        lfuCache.put(1, "A");
        lfuCache.get(1);

        lfuCache.put(2, "B");
        lfuCache.put(3, "C");
        lfuCache.put(4, "D");

        assertNull(lfuCache.get(2));
        assertEquals("A", lfuCache.get(1));
        assertEquals("C", lfuCache.get(3));
        assertEquals("D", lfuCache.get(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroCapacityCache() {
        new LFUCache<>(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCapacityCache() {
        new LFUCache<>(-1);
    }

    @Test
    public void testUpdateValue() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");

        lfuCache.put(1, "AA");
        assertEquals("AA", lfuCache.get(1));
        assertEquals("B", lfuCache.get(2));
    }

    @Test
    public void testEvictLeastFrequentlyUsedWithTie() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");

        lfuCache.get(1);
        lfuCache.get(1);


        lfuCache.put(4, "D");

        assertNull(lfuCache.get(2));
        assertEquals("A", lfuCache.get(1));
        assertEquals("C", lfuCache.get(3));
        assertEquals("D", lfuCache.get(4));
    }

    @Test
    public void testMultipleInsertsAndEvictions() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");
        lfuCache.put(4, "D");

        assertNull(lfuCache.get(1));
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
        lfuCache.put(1, "AA");

        lfuCache.put(4, "D");

        assertNull(lfuCache.get(2));
        assertEquals("AA", lfuCache.get(1));
        assertEquals("C", lfuCache.get(3));
        assertEquals("D", lfuCache.get(4));
    }

    @Test
    public void testEvictOldestWhenSameFrequency() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");

        lfuCache.get(1);
        lfuCache.get(2);

        lfuCache.put(4, "D");

        assertNull(lfuCache.get(3));
        assertEquals("A", lfuCache.get(1));
        assertEquals("B", lfuCache.get(2));
        assertEquals("D", lfuCache.get(4));
    }

    @Test
    public void testGetOnEmptyCache() {
        LFUCache<Integer, String> emptyCache = new LFUCache<>(3);
        assertNull(emptyCache.get(1));
    }

    @Test
    public void testCapacityOneCache() {
        LFUCache<Integer, String> singleCapacityCache = new LFUCache<>(1);

        singleCapacityCache.put(1, "A");
        assertEquals("A", singleCapacityCache.get(1));

        singleCapacityCache.put(2, "B");
        assertNull(singleCapacityCache.get(1));
        assertEquals("B", singleCapacityCache.get(2));
    }

    @Test
    public void testFrequencyIncreaseOnMultipleGets() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");

        lfuCache.get(1);
        lfuCache.get(1);

        lfuCache.put(3, "C");
        lfuCache.put(4, "D");

        assertNull(lfuCache.get(2));
        assertEquals("A", lfuCache.get(1));
        assertEquals("C", lfuCache.get(3));
        assertEquals("D", lfuCache.get(4));
    }

    @Test
    public void testKeyUpdateResetsFrequency() {
        lfuCache.put(1, "A");
        lfuCache.get(1);

        lfuCache.put(1, "AA");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");
        lfuCache.put(4, "D");

        assertNull(lfuCache.get(2));
        assertEquals("AA", lfuCache.get(1));
        assertEquals("C", lfuCache.get(3));
        assertEquals("D", lfuCache.get(4));
    }

    @Test
    public void testEvictionOrderWithDifferentFrequencies() {
        lfuCache.put(1, "A");
        lfuCache.put(2, "B");
        lfuCache.put(3, "C");

        lfuCache.get(1);
        lfuCache.get(1);
        lfuCache.get(2);

        lfuCache.put(4, "D");

        assertNull(lfuCache.get(3));
        assertEquals("A", lfuCache.get(1));
        assertEquals("B", lfuCache.get(2));
        assertEquals("D", lfuCache.get(4));
    }
}
