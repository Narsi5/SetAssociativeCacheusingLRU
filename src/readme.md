### **K-Way Set-Associative Cache**

#### Description 

The K-Way Set-Associative Cache project implements a multi-way set-associative cache structure in Java, using an LRU (Least Recently Used) eviction policy. This type of cache is suitable for scenarios where efficient data retrieval and management are crucial, such as in-memory caching for databases, web applications, or systems requiring fast access to frequently used data.

#### The project consists of several key components:

##### KWaySetAssociativeCache: Manages multiple sets of caches (LRUCache instances), distributing key-value pairs across sets based on a simple hash function. Each set operates independently, allowing for parallel access and efficient synchronization using locks.

LRUCache: Implements a single cache instance using a doubly linked list and a hash map for O(1) access time. It supports operations like get, put, and maintains statistics such as hits, misses, and eviction metrics.

###### CacheFrontEnd: Provides a command-line interface (CLI) for users to interact with the cache system. Users can perform operations like retrieving values (get), storing key-value pairs (put), and inspecting cache contents and performance metrics.

#### Features:

K-way set-associative caching: Distributes key-value pairs across multiple cache sets for efficient data access and management.
LRU eviction policy: Removes the least recently used items when the cache reaches its capacity.
Thread-safe implementation: Uses locks (ReentrantLock) for synchronized access to cache sets, ensuring data integrity in multithreaded environments.
Interactive CLI: Provides a user-friendly command-line interface for easy interaction and monitoring of cache operations and metrics.