package org.prilepskiy.aston_step_two.hw_one;

public class MyHashMap<K, V> implements BaseMyHashMap <K,V>{
    private Node<K, V>[] table;
    private int size;
    private static final int DEFAULT_SIZE = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private int threshold;

    public MyHashMap(){
        table = new Node[DEFAULT_SIZE];
        threshold = (int) (DEFAULT_SIZE * LOAD_FACTOR);// == 12
    }


    @Override
    public void put(K key, V value) {

    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }
}
