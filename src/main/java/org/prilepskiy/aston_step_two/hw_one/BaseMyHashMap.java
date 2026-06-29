package org.prilepskiy.aston_step_two.hw_one;

public interface BaseMyHashMap<K,V> {
     void put(K key, V value);
     V get(K key);
     V remove(K key);
     int size();
     boolean containsKey(K key);
}
