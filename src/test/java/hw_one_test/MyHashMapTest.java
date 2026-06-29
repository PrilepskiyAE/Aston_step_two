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


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка перезаписи значения по существующему ключу")
    @Description("Если добавить один и тот же ключ повторно, значение должно обновиться, а размер мапы остаться неизменным.")
    @Story("Обновление существующего ключа")
    @Owner("Prilepskiy AE")
    void put_overwriteExistingKey() {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("key1", 100);
        map.put("key1", 200);

        assertThat(map.get("key1")).isEqualTo(200);
        assertThat(map.size()).isEqualTo(1);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Получение значения по несуществующему ключу")
    @Description("Проверяем, что при попытке достать значение по ключу, которого нет в мапе, метод возвращает null.")
    @Story("Безопасное извлечение данных")
    @Owner("Prilepskiy AE")
    void get_nonExistingKey() {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        assertThat(map.get("missing")).isNull();
    }

    @Test
    @Story("Изменение размера таблицы (Resize)")
    @DisplayName("Проверка автоматического расширения при достижении порога (Threshold)")
    @Description("Тест проверяет, что при добавлении 13-го элемента (при дефолтной емкости 16 и load factor 0.75) " +
            "данные не теряются, и механизм resize отрабатывает корректно.")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Prilepskiy AE")
    void resize_triggeredOnThreshold() {
        MyHashMap<Integer, String> map = new MyHashMap<>();

        for (int i = 0; i < 13; i++) {
            map.put(i, "value" + i);
        }

        assertThat(map.size()).isEqualTo(13);
        for (int i = 0; i < 13; i++) {
            assertThat(map.get(i)).isEqualTo("value" + i);
        }
    }

    @Test
    @Story("Разные ключи попадают в одну корзину и сохраняются корректно")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Должен корректно хранить элементы при коллизии в одном bucket")
    @Description("Проверяет, что при коллизии в одном bucket оба значения сохраняются и доступны по своим ключам")
    @Owner("Prilepskiy AE")
    void collisionHandling_sameBucket() {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        String k1 = "A";
        String k2 = "a";
        map.put(k1, 1);
        map.put(k2, 2);

        assertThat(map.get(k1)).isEqualTo(1);
        assertThat(map.get(k2)).isEqualTo(2);
        assertThat(map.size()).isEqualTo(2);
    }

    @Test
    @Story("Поддержка null ключей")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Обработка null в качестве ключа")
    @Description("Проверяем корректность работы мапы при использовании null ключа: добавление, получение, удаление и влияние на размер.")
    @Owner("Prilepskiy AE")
    void nullKey_behavior() {
        MyHashMap<Object, String> map = new MyHashMap<>();
        map.put(null, "null-value");
        map.put("k1", "value1");

        assertThat(map.get(null)).isEqualTo("null-value");
        assertThat(map.get("k1")).isEqualTo("value1");
        assertThat(map.remove(null)).isEqualTo("null-value");
        assertThat(map.get(null)).isNull();
        assertThat(map.size()).isEqualTo(1);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Последовательность комплексных операций")
    @Description("Проверка цепочки действий: вставка нескольких элементов, удаление, проверка наличия и перезапись существующего ключа.")
    @Story("Комплексное взаимодействие с данными")
    @Owner("Prilepskiy AE")
    void multipleOperations_sequence() {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        map.put("x", 1);
        map.put("y", 2);
        map.put("z", 3);

        assertThat(map.get("y")).isEqualTo(2);
        assertThat(map.remove("x")).isEqualTo(1);
        assertThat(map.containsKey("x")).isFalse();

        map.put("y", 99);

        assertThat(map.get("y")).isEqualTo(99);
        assertThat(map.size()).isEqualTo(2);
    }

}
