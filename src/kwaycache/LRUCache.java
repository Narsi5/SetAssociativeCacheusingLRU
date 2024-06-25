package kwaycache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LRUCache<K, V> implements Cache<K, V> {

    private final Node head;
    private final Node tail;
    private final Map<K, Node> map;
    private final int capacity;
    private int hits;
    private int misses;
    private final long cacheAccessTime = 10; // in nanoseconds
    private final long mainMemoryAccessTime = 1000; // in nanoseconds (1 microsecond)

    private final Lock lock = new ReentrantLock();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node(null, null); // Dummy head
        this.tail = new Node(null, null); // Dummy tail
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public V get(K key) {
        lock.lock();
        try {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                remove(node);
                insert(node);
                hits++;
                return node.value;
            } else {
                misses++;
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void put(K key, V value) {
        lock.lock();
        try {
            if (map.containsKey(key)) {
                remove(map.get(key));
            }
            if (map.size() == capacity) {
                remove(tail.prev); // Remove the least recently used node (LRU eviction)
            }
            insert(new Node(key, value));
        } finally {
            lock.unlock();
        }
    }

    private void remove(Node node) {
        map.remove(node.key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insert(Node node) {
        map.put(node.key, node);
        Node headNext = head.next;
        head.next = node;
        node.prev = head;
        headNext.prev = node;
        node.next = headNext;
    }

    public void printCacheContents() {
        Node current = head.next;
        System.out.println("Cache Contents:");
        while (current != tail) {
            System.out.println("Key: " + current.key + ", Value: " + current.value);
            current = current.next;
        }
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }

    private class Node {
        Node prev;
        Node next;
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
