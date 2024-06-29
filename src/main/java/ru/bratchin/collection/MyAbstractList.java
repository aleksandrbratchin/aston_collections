package ru.bratchin.collection;

import ru.bratchin.collection.api.MyList;

import java.util.Comparator;

/**
 * MyAbstractList - абстрактный класс, который реализует базовую функциональность для списков.
 * Этот класс предоставляет методы для работы с размером списка и проверки индексов.
 *
 * @param <E> тип элементов в этом списке
 */
public abstract class MyAbstractList<E> implements MyList<E> {

    protected int size;

    /**
     * Возвращает количество элементов в списке.
     *
     * @return количество элементов в списке
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает true, если список не содержит элементов.
     *
     * @return true, если список пуст; false в противном случае
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Проверка допустимости индекса.
     *
     * @param index индекс для проверки
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    protected void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * Возвращает компаратор для сравнения элементов списка.
     *
     * @return компаратор для сравнения элементов
     * @throws ClassCastException если элементы списка не реализуют интерфейс Comparable
     */
    protected Comparator<E> getComparator() {
        return (o1, o2) -> {
            if (o1 == null && o2 == null) return 0;
            if (o1 == null) return 1;
            if (o2 == null) return -1;
            return ((Comparable<E>) o1).compareTo(o2);
        };
    }

}
