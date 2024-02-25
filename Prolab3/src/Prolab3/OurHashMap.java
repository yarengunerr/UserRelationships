package prolab3;

import java.util.HashSet;
import java.util.Set;

class OurHashMap<K, V> {
    private static final int TABLE_SIZE = 16;
    private final Entry<K, V>[] table;

    public OurHashMap() {
        this.table = new Entry[TABLE_SIZE];
    }

    public void put(K key, V value) 
    {
        int index = hash(key) % TABLE_SIZE;

        if (table[index] == null) {
            table[index] = new Entry<>(key, value);
        } else {
            Entry<K, V> current = table[index];
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                current = current.next;
            }
            current.next = new Entry<>(key, value);
        }
    }

    public V get(K key) 
    {
        int index = hash(key) % TABLE_SIZE;
        Entry<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    public V getOrDefault(K key, V defaultValue) 
    {
        int index = hash(key) % TABLE_SIZE;
        Entry<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }

        return defaultValue;
    }

    public Set<K> getAllKeys() 
    {
        Set<K> keys = new HashSet<>();
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                keys.add(entry.key);
                entry = entry.next;
            }
        }
        return keys;
    }

    public boolean containsKey(K key) 
    {
        int index = hash(key) % TABLE_SIZE;
        Entry<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    private int hash(K key) 
    {
        return key.hashCode() & 0x7FFFFFFF;
    }

    private static class Entry<K, V> 
    {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
}
