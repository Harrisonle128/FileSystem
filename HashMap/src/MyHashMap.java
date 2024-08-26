/**
 * This file implements the Default map interface for the hash map data structure.
 */
import java.util.ArrayList;
import java.util.List;

public class MyHashMap<K, V> implements DefaultMap<K, V> {
    public static final double DEFAULT_LOAD_FACTOR = 0.75;
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
    public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
    public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";

    private double loadFactor;
    private int capacity;
    private int size;

    // Use this instance variable for Separate Chaining conflict resolution
    private List<HashMapEntry<K, V>>[] buckets;  

    // Use this instance variable for Linear Probing
    //private HashMapEntry<K, V>[] entries; 	

    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 
     * @param initialCapacity the initial capacity of this MyHashMap
     * @param loadFactor the load factor for rehashing this MyHashMap
     * @throws IllegalArgumentException if initialCapacity or loadfactor is negative
     */
    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity, double loadFactor) throws IllegalArgumentException {
        // TODO Finish initializing instance fields
        if(initialCapacity < 0) { throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);}
        if(loadFactor <= 0) { throw new IllegalArgumentException(ILLEGAL_ARG_LOAD_FACTOR);}

        this.capacity = initialCapacity;
        this.loadFactor = loadFactor;

        // if you use Separate Chaining
        this.buckets = (List<HashMapEntry<K, V>>[]) new ArrayList[capacity];
     
    }

    /**
	 * Adds the specified key, value pair to this DefaultMap
	 * Note: duplicate keys are not allowed
	 * 
	 * @return true if the key value pair was added to this DefaultMap
	 * @throws IllegalArgument exception if the key is null
	 */
    @Override
    public boolean put(K key, V value) throws IllegalArgumentException {
    	if (key == null) {
    	    throw new IllegalArgumentException("Key is null");
    	}

    	if (this.loadFactor > DEFAULT_LOAD_FACTOR) {
    	    expandCapacity();
    	}

    	HashMapEntry<K, V> temp = new HashMapEntry<>(key, value);
    	int hashCode = Math.abs(key.hashCode());
    	int index = hashCode % this.capacity;

    	if (containsKey(key)) {
    	    return false;
    	}

    	if (this.buckets[index] != null) {
    	    this.buckets[index].add(temp);
    	    this.size++;
    	    return true;
    	} else if (this.buckets[index] == null) {
    	    this.buckets[index] = new ArrayList<>();
    	    this.buckets[index].add(temp);
    	    this.size++;
    	    return true;
    	}

    	return false;
    }

    /**
	 * Replaces the value that maps to the key if it is present
	 * @param key The key whose mapped value is being replaced
	 * @param newValue The value to replace the existing value with
	 * @return true if the key was in this DefaultMap
	 * @throws IllegalArgument exception if the key is null
	 */
    @Override
    public boolean replace(K key, V newValue) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        if(key == null) {throw new IllegalArgumentException("Key is null");}
        int hashCode = Math.abs(key.hashCode());
        int index = hashCode % this.capacity;

        if(containsKey(key) == false) { return false; }
        for(HashMapEntry<K, V> h: this.buckets[index])
        {
            if(h.key.equals(key)) 
            { 
                h.value = newValue;
                return true;
            }
        }
        return false;
    }


	/**
	 * Remove the entry corresponding to the given key
	 * 
	 * @return true if an entry for the given key was removed
	 * @throws IllegalArgument exception if the key is null
	 */
    @Override
    public boolean remove(K key) throws IllegalArgumentException {
    	 if (key == null) {
    	        throw new IllegalArgumentException("Key is null");
    	    }

    	    int hashCode = Math.abs(key.hashCode());
    	    int index = hashCode % this.capacity;

    	    List<HashMapEntry<K, V>> bucket = this.buckets[index];
    	    if (bucket == null) {
    	        return false;
    	    }

    	    for (HashMapEntry<K, V> entry : bucket) {
    	        if (entry.key.equals(key)) {
    	            bucket.remove(entry);
    	            this.size--;

    	            if (bucket.isEmpty()) {
    	                this.buckets[index] = null;
    	            }

    	            return true;
    	        }
    	    }

    	    return false;
    }
    
    /**
	 * Adds the key, value pair to this DefaultMap if it is not present,
	 * otherwise, replaces the value with the given value
	 * @throws IllegalArgument exception if the key is null
	 */
    @Override
    public void set(K key, V value) throws IllegalArgumentException {
    	
    	if (key == null) {
    	    throw new IllegalArgumentException("Key is null");
    	}

    	if (this.loadFactor > DEFAULT_LOAD_FACTOR) {
    	    expandCapacity();
    	}
 
    	int hashCode = Math.abs(key.hashCode());
    	int index = hashCode % this.capacity;

    	if (this.buckets[index] == null) {
    	    this.buckets[index] = new ArrayList<>();
    	    this.buckets[index].add(new HashMapEntry<>(key, value));
    	} else {
    	    for (HashMapEntry<K, V> entry : this.buckets[index]) {
    	        if (entry.key.equals(key)) {
    	            entry.value = value;
    	            return;
    	        }
    	    }
    	    this.buckets[index].add(new HashMapEntry<>(key, value));
    	}

    	this.size++;
    }

    /**
	 * @return the value corresponding to the specified key, null if key doesn't 
	 * exist in hash map
	 * @throws IllegalArgument exception if the key is null
	 */
    @Override
    public V get(K key) throws IllegalArgumentException {
    	if (key == null) {
    	    throw new IllegalArgumentException("Key is null");
    	}

    	int hashCode = Math.abs(key.hashCode());
    	int index = hashCode % this.capacity;

    	if (this.buckets[index] == null) {
    	    return null;
    	} else {
    	    for (HashMapEntry<K, V> entry : this.buckets[index]) {
    	        if (entry.key.equals(key)) {
    	            return entry.getValue();
    	        }
    	    }
    	    return null;
    	}
    }

    /**
	 * 
	 * @return The number of (key, value) pairs in this DefaultMap
	 */
    @Override
    public int size() {
        // TODO Auto-generated method stub
        return this.size;
    }

    /**
	 * 
	 * @return true iff this.size() == 0 is true
	 */
    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        if(this.size == 0) {
        	return true;
        }
        return false;
    }

    /**
	 * @return true if the specified key is in this DefaultMap
	 * @throws IllegalArgument exception if the key is null
	 */
    @Override
    public boolean containsKey(K key) throws IllegalArgumentException {
    	if (key == null) {
    	    throw new IllegalArgumentException("Key is null");
    	}

    	int hashCode = Math.abs(key.hashCode());
    	int index = hashCode % this.capacity;

    	if (this.buckets[index] == null) {
    	    return false;
    	} else {
    	    for (HashMapEntry<K, V> entry : this.buckets[index]) {
    	        if (entry.key.equals(key)) {
    	            return true;
    	        }
    	    }
    	}

    	return false;
    }
    
    /**
	 * 
	 * @return an array containing the keys of this DefaultMap. If this DefaultMap is 
	 * empty, returns array of length zero. 
	 */
    @SuppressWarnings("unchecked")
    @Override
    public List<K> keys() {
        // TODO Auto-generated method stub
    	List<K> key = new ArrayList<>();

    	for (List<HashMapEntry<K, V>> bucket : this.buckets) {
    	    if (bucket != null) {
    	        for (HashMapEntry<K, V> entry : bucket) {
    	            key.add(entry.getKey());
    	        }
    	    }
    	}

    	return key;
    }

    /**
     * Double the capacity if the number of keys in a list is greater than the
     * value of the load factor.
     */
    @SuppressWarnings("unchecked")
    public void expandCapacity() {
    	 List<HashMapEntry<K, V>>[] newEntries = new List[this.capacity * 2];
    	 List<HashMapEntry<K, V>>[] oldEntries = this.buckets;

    	    this.buckets = newEntries;
    	    this.size = 0;

    	    for (List<HashMapEntry<K, V>> bucket : oldEntries) {
    	        if (bucket == null) {
    	            continue;
    	        }

    	        for (HashMapEntry<K, V> entry : bucket) {
    	            this.set(entry.key, entry.value);
    	        }
    	    }
    }

    private static class HashMapEntry<K, V> implements DefaultMap.Entry<K, V> {

        K key;
        V value;

      
        private HashMapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        
        @Override
        public V getValue() {
            return value;
        }

        
        @Override
        public void setValue(V value) {
            this.value = value;
        }
    }
}
