package org.prilepskiy.aston_step_two.hw_one;

public class MyHashMap<K, V> {
    private Node<K, V>[] table;
    private int size;
    private static final int DEFAULT_SIZE = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private int threshold;

    public MyHashMap(){
        table = new Node[DEFAULT_SIZE];
        threshold = (int) (DEFAULT_SIZE * LOAD_FACTOR);// == 12
    }

}
