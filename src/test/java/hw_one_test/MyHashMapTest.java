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

}
