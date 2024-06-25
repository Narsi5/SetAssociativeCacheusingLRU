// Cache.java

package kwaycache;

/**
 * An interface for a cache
 */
public interface Cache<K, V> {
    V get(K key);
    void put(K key, V value);
}
