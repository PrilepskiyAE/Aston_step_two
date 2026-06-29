package hw_one_test;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prilepskiy.aston_step_two.hw_one.MyHashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Epic("Структуры данных")
@Feature("MyHashMap")
public class MyHashMapTest {

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Проверка вставки и получения одиночного элемента")
    @Description("Тестируем базовый контракт put/get: закидываем ключ-значение и проверяем, что они на месте, а счетчик size тикнул вверх.")
    @Story("Базовые операции с мапой")
    @Owner("Prilepskiy AE")
    void putAndGet_singleEntry() {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("key1", 100);

        assertThat(map.get("key1")).isEqualTo(100);
        assertThat(map.size()).isEqualTo(1);

    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Успешное удаление существующего ключа")
    @Description("Проверяем, что метод remove корректно удаляет пару ключ-значение, возвращает удалённое значение и уменьшает размер коллекции.")
    @Story("Удаление элементов")
    @Owner("Prilepskiy AE")
    void remove_existingKey() {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("key1", 100);
        map.put("key2", 200);

        Integer removed = map.remove("key1");
        assertThat(removed).isEqualTo(100);
        assertThat(map.get("key1")).isNull();
        assertThat(map.size()).isEqualTo(1);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Удаление несуществующего ключа")
    @Description("Проверяем, что при удалении отсутствующего ключа метод возвращает null и размер мапы не изменяется.")
    @Story("Удаление элементов")
    @Owner("Prilepskiy AE")
    void remove_nonExistingKey() {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        Integer removed = map.remove("missing");
        assertThat(removed).isNull();
        assertThat(map.size()).isEqualTo(0);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка containsKey для существующего и несуществующего ключа")
    @Description("Проверяем, что метод containsKey возвращает true для существующего ключа и false для отсутствующего.")
    @Story("Проверка наличия ключа")
    @Owner("Prilepskiy AE")
    void containsKey_trueAndFalse() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("a", "A");

        assertThat(map.containsKey("a")).isTrue();
        assertThat(map.containsKey("b")).isFalse();
    }


}
