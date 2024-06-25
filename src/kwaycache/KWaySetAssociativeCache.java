package kwaycache;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class KWaySetAssociativeCache<K, V> implements Cache<K, V> {

    private final int setSize; // Number of sets (K)
    private final int entrySize; // Number of entries per set
    private final LRUCache<K, V>[] caches; // Array of LRUCache instances
    private final Lock[] locks; // Array of locks for synchronization

    public KWaySetAssociativeCache(int setSize, int entrySize) {
        this.setSize = setSize;
        this.entrySize = entrySize;
        this.caches = new LRUCache[setSize];
        this.locks = new ReentrantLock[setSize];

        for (int i = 0; i < setSize; i++) {
            caches[i] = new LRUCache<>(entrySize);
            locks[i] = new ReentrantLock();
        }
    }

    @Override
    public V get(K key) {
        int setIndex = getSetIndex(key);

        locks[setIndex].lock();
        try {
            return caches[setIndex].get(key);
        } finally {
            locks[setIndex].unlock();
        }
    }

    @Override
    public void put(K key, V value) {
        int setIndex = getSetIndex(key);

        locks[setIndex].lock();
        try {
            caches[setIndex].put(key, value);
        } finally {
            locks[setIndex].unlock();
        }
    }

    private int getSetIndex(K key) {
        // Simple hash function to distribute keys among sets
        return Math.floorMod(key.hashCode(), setSize);
    }

    public void printCacheContents() {
        System.out.println("Cache Contents:");
        for (int i = 0; i < setSize; i++) {
            System.out.println("Set " + i + ":");
            caches[i].printCacheContents();
        }
    }

    public void printMetrics() {
        int totalHits = 0;
        int totalMisses = 0;

        for (int i = 0; i < setSize; i++) {
            totalHits += caches[i].getHits();
            totalMisses += caches[i].getMisses();
        }

        System.out.println("Total Hits: " + totalHits);
        System.out.println("Total Misses: " + totalMisses);

        int totalAccesses = totalHits + totalMisses;
        double hitRatio = totalAccesses == 0 ? 0.0 : (double) totalHits / totalAccesses;
        double missRatio = totalAccesses == 0 ? 0.0 : (double) totalMisses / totalAccesses;

        // Assuming a uniform cache access time (for simplicity)
        long averageAccessTime = (long) (hitRatio * 10 + missRatio * 1000); // Adjust based on your actual timings

        System.out.println("Average Memory Access Time (AMAT): " + averageAccessTime + " nanoseconds");
    }
}
