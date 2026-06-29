package org.prilepskiy.aston_step_two.hw_one;

import java.util.Objects;

/**
 * Узел (Node), используемый в структурах данных на основе хеш-таблиц.
 * <p>
 * Этот класс инкапсулирует данные одного элемента: хеш ключа, сам ключ,
 * соответствующее ему значение и ссылку на следующий узел в цепочке.
 *
 * @param <K> тип ключа, поддерживаемого этим узлом
 * @param <V> тип сопоставленного значения
 */
public class Node<K, V> {

    /**
     * Предварительно вычисленный хеш-код ключа.
     */
    final int hash;

    /**
     * Ключ, связанный с данным узлом.
     */
    final K key;

    /**
     * Текущее значение, хранящееся в узле.
     */
    V value;

    /**
     * Ссылка на следующий узел в цепочке (для разрешения коллизий).
     */
    Node<K,V> next;

    /**
     * Создает новый экземпляр узла.
     *
     * @param hash хеш ключа
     * @param key ключ
     * @param value значение
     * @param next ссылка на следующий узел (может быть null)
     */
    public Node(int hash, K key, V value, Node<K, V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    /**
     * Сравнивает текущий узел с указанным объектом на равенство.
     *
     * @param o объект для сравнения
     * @return true, если объекты идентичны по хешу, ключу, значению и следующему узлу
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Node<?, ?> node = (Node<?, ?>) o;
        return hash == node.hash && Objects.equals(key, node.key) && Objects.equals(value, node.value) && Objects.equals(next, node.next);
    }

    /**
     * Вычисляет хеш-код для текущего узла.
     *
     * @return целое число — хеш-код узла
     */
    @Override
    public int hashCode() {
        return Objects.hash(hash, key, value);
    }
}