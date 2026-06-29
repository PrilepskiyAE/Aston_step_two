package org.prilepskiy.aston_step_two.hw_one;

import java.util.Objects;

public class MyHashMap<K, V> implements BaseMyHashMap <K,V>{
    private Node<K, V>[] table;
    private int size;
    private static final int DEFAULT_SIZE = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private int threshold;

    public MyHashMap(){
        table = new Node[DEFAULT_SIZE];
        threshold = (int) (DEFAULT_SIZE * LOAD_FACTOR);
    }


    @Override
    public void put(K key, V value) {
        int hash = (key==null) ? 0 : key.hashCode();
        int i = indexFor(hash, table.length);

        for (Node<K, V> e = table[i]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                e.value = value;
                return;
            }
        }
        addEntry(hash, key, value, i);
    }


    @Override
    public V get(K key) {
        Node<K, V> node = findNode(key);
        return (node == null) ? null : node.value;
    }

    @Override
    public V remove(K key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        Node<K, V> head = table[index];

        Node<K, V> prev = null;
        Node<K, V> current = head;

        while (current != null) {
            if (current.hash == hash &&
                    (Objects.equals(key, current.key))) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(K key) {
        return findNode(key) != null;
    }


    private int indexFor(int hash, int length) {
        return Math.floorMod(hash, length);
    }

    private void addEntry(int hash, K key, V value, int bucketIndex) {
        Node<K, V> newNode = new Node<>(hash, key, value, table[bucketIndex]);
        table[bucketIndex] = newNode;
        size++;

        if (size > threshold) {
            resize();
        }
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCap = table.length;
        int newCap = oldCap * 2;
        threshold = (int) (newCap * LOAD_FACTOR);

        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCap];
        Node<K, V>[] oldTable = table;

        for (int i = 0; i < oldCap; i++) {
            Node<K, V> node = oldTable[i];
            while (node != null) {
                Node<K, V> next = node.next;

                int newIndex = indexFor(node.hash, newCap);
                node.next = newTable[newIndex];
                newTable[newIndex] = node;

                node = next;
            }
        }
        table = newTable;
    }

    private Node<K, V> findNode(K key) {
        int hash = (key == null) ? 0 : key.hashCode();
        int index = indexFor(hash, table.length);
        Node<K, V> node = table[index];
        while (node != null) {
            if (node.hash == hash && (node.key == key || (key != null && key.equals(node.key)))) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    private int hash(K key) {
        return (key == null) ? 0 : key.hashCode();
    }
}
