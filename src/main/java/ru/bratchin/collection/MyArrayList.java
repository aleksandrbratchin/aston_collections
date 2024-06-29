package ru.bratchin.collection;

import ru.bratchin.collection.api.MyList;

import java.util.Arrays;

/**
 * MyArrayList - реализация пользовательского списка на основе массива.
 * Этот класс предоставляет методы для добавления, получения, удаления элементов, очистки и сортировки списка.
 *
 * @param <E> тип элементов в этом списке
 */
public class MyArrayList<E> extends MyAbstractList<E> {

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 2;
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;

    /**
     * Конструктор для создания пустого списка с начальной емкостью по умолчанию.
     */
    public MyArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

     /**
     * Добавляет элемент в конец списка.
     *
     * @param element элемент для добавления в список
     * @throws OutOfMemoryError при достижении максимально возможного размера массива
     */
    public void add(E element) {
        ensureCapacity(size + 1);
        elements[size++] = element;
    }

    /**
     * Вставляет элемент в указанную позицию в списке.
     *
     * @param index   индекс, по которому следует вставить элемент
     * @param element элемент для добавления в список
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     * @throws OutOfMemoryError при достижении максимально возможного размера массива
     */
    public void add(int index, E element) {
        checkIndex(index);
        ensureCapacity(size + 1);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Возвращает элемент, находящийся в указанной позиции в списке.
     *
     * @param index индекс элемента для получения
     * @return элемент в указанной позиции
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    public E get(int index) {
        checkIndex(index);
        return (E) elements[index];
    }

    /**
     * Удаляет элемент в указанной позиции в списке.
     *
     * @param index индекс элемента для удаления
     * @return удаленный элемент
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    public E remove(int index) {
        checkIndex(index);
        Object oldValue = elements[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null;
        return (E) oldValue;
    }

    /**
     * Удаляет все элементы из списка. После вызова этого метода список будет пустым.
     */
    public void clear() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Сортирует список, используя естественный порядок его элементов.
     *
     * @return отсортированный список
     * @throws ClassCastException если элементы списка не реализуют интерфейс Comparable
     */
    public MyList<E> sort() {
        Arrays.sort((E[]) elements, 0, size, getComparator());
        return this;
    }

    /**
     * Обеспечивает наличие достаточной емкости для хранения указанного количества элементов.
     * Увеличивает емкость массива, если необходимо.
     *
     * @param minCapacity минимальная необходимая емкость
     * @throws OutOfMemoryError при достижении максимально возможного размера массива
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity;
            if (elements.length == MAX_ARRAY_SIZE) {
                throw new OutOfMemoryError("Достигнут максимально возможный размер массива");
            }
            try {
                newCapacity = Math.multiplyExact(elements.length, 2);
            } catch (ArithmeticException e) {
                newCapacity = MAX_ARRAY_SIZE;
            }
            Object[] newArray = new Object[newCapacity];
            System.arraycopy(elements, 0, newArray, 0, elements.length);
            elements = newArray;
        }
    }

}
