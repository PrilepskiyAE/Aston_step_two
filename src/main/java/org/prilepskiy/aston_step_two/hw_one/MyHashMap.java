package org.prilepskiy.aston_step_two.hw_one;

import java.util.Objects;

/**
 * Простая реализация хеш-таблицы с использованием массива корзин и связных списков
 * для разрешения коллизий.
 * <p>
 * Поддерживает базовые операции: добавление, получение, удаление элемента,
 * проверку наличия ключа и получение текущего размера.
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */

public class MyHashMap<K, V> implements BaseMyHashMap <K,V>{
    /**
     * Массив корзин, в каждой из которых хранится цепочка узлов.
     */
    private Node<K, V>[] table;

    /**
     * Текущее количество элементов в таблице.
     */
    private int size;

    /**
     * Начальный размер таблицы по умолчанию.
     */
    private static final int DEFAULT_SIZE = 16;

    /**
     * Коэффициент загрузки, после превышения которого выполняется расширение таблицы.
     */
    private static final float LOAD_FACTOR = 0.75f;

    /**
     * Пороговое значение количества элементов, после которого таблица расширяется.
     */
    private int threshold;


    /**
     * Создает пустую хеш-таблицу с размером по умолчанию.
     */
    @SuppressWarnings("unchecked")
    public MyHashMap(){
        table = new Node[DEFAULT_SIZE];
        threshold = (int) (DEFAULT_SIZE * LOAD_FACTOR);
    }

    /**
     * Добавляет пару ключ-значение в хеш-таблицу.
     * <p>
     * Если ключ уже существует, его значение будет перезаписано.
     *
     * @param key ключ элемента
     * @param value значение элемента
     */
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

    /**
     * Возвращает значение по указанному ключу.
     *
     * @param key ключ, по которому выполняется поиск
     * @return найденное значение или {@code null}, если ключ отсутствует
     */
    @Override
    public V get(K key) {
        Node<K, V> node = findNode(key);
        return (node == null) ? null : node.value;
    }

    /**
     * Удаляет элемент по указанному ключу.
     *
     * @param key ключ удаляемого элемента
     * @return значение удаленного элемента или {@code null}, если ключ не найден
     */
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

    /**
     * Возвращает количество элементов в хеш-таблице.
     *
     * @return текущее количество пар ключ-значение
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Проверяет, содержится ли указанный ключ в таблице.
     *
     * @param key ключ для проверки
     * @return {@code true}, если ключ найден, иначе {@code false}
     */
    @Override
    public boolean containsKey(K key) {
        return findNode(key) != null;
    }

    /**
     * Вычисляет индекс корзины по хешу и длине массива.
     *
     * @param hash хеш-код ключа
     * @param length длина массива корзин
     * @return индекс корзины
     */
    private int indexFor(int hash, int length) {
        return Math.floorMod(hash, length);
    }

    /**
     * Добавляет новый узел в указанную корзину.
     *
     * @param hash хеш-код ключа
     * @param key ключ элемента
     * @param value значение элемента
     * @param bucketIndex индекс корзины, куда будет добавлен узел
     */
    private void addEntry(int hash, K key, V value, int bucketIndex) {
        Node<K, V> newNode = new Node<>(hash, key, value, table[bucketIndex]);
        table[bucketIndex] = newNode;
        size++;

        if (size > threshold) {
            resize();
        }
    }

    /**
     * Увеличивает размер таблицы в два раза и перераспределяет все элементы
     * по новым корзинам.
     */
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

    /**
     * Ищет узел по ключу в таблице.
     *
     * @param key ключ для поиска
     * @return найденный узел или {@code null}, если узел отсутствует
     */
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

    /**
     * Возвращает хеш-код ключа.
     *
     * @param key ключ
     * @return хеш-код ключа, либо {@code 0}, если ключ равен {@code null}
     */
    private int hash(K key) {
        return (key == null) ? 0 : key.hashCode();
    }
}
