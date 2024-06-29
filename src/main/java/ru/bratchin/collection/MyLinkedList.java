package ru.bratchin.collection;

import ru.bratchin.collection.api.MyList;

/**
 * MyLinkedList - реализация пользовательского списка на основе двусвязного списка.
 * Этот класс предоставляет методы для добавления, получения, удаления элементов, очистки и сортировки списка.
 *
 * @param <E> тип элементов в этом списке
 */
public class MyLinkedList<E> extends MyAbstractList<E> {

    private Node<E> first;
    private Node<E> last;

    /**
     * Добавляет элемент в конец списка.
     *
     * @param element элемент для добавления в список
     * @throws OutOfMemoryError при достижении максимально возможного размера списка
     */
    @Override
    public void add(E element) {
        checkSize();
        Node<E> oldElement = last;
        last = new Node<>(element, null, null);
        if (oldElement == null) {
            first = last;
        } else {
            oldElement.next = last;
            last.prev = oldElement;
        }
        size++;
    }

    /**
     * Вставляет элемент в указанную позицию в списке.
     *
     * @param index   индекс, по которому следует вставить элемент
     * @param element элемент для добавления в список
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     * @throws OutOfMemoryError при достижении максимально возможного размера списка
     */
    @Override
    public void add(int index, E element) {
        checkSize();
        checkIndex(index);
        Node<E> node = getNode(index);
        node.prev.next = new Node<>(element, node, node.prev);
        size++;
    }

    /**
     * Возвращает элемент, находящийся в указанной позиции в списке.
     *
     * @param index индекс элемента для получения
     * @return элемент в указанной позиции
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    @Override
    public E get(int index) {
        checkIndex(index);
        return getNode(index).val;
    }

    /**
     * Удаляет элемент в указанной позиции в списке.
     *
     * @param index индекс элемента для удаления
     * @return удаленный элемент
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    @Override
    public E remove(int index) {
        checkIndex(index);
        Node<E> node = getNode(index);
        if (node.prev == null) {
            clear();
        } else {
            node.prev.next = node.next;
            size--;
        }
        return node.val;
    }

    /**
     * Удаляет все элементы из списка. После вызова этого метода список будет пустым.
     */
    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;
    }

    /**
     * Сортирует список (пузырьковая сортировка), используя естественный порядок его элементов.
     *
     * @return отсортированный список
     */
    @Override
    public MyList<E> sort() {
        int n = size;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            Node<E> node = first;
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (getComparator().compare(node.val, node.next.val) > 0) {
                    E temp = node.val;
                    node.val = node.next.val;
                    node.next.val = temp;
                    swapped = true;
                }
                node = node.next;
            }
            if (!swapped) {
                break;
            }
        }
        return this;
    }

    /**
     * Возвращает узел, находящийся в указанной позиции в списке.
     *
     * @param index индекс узла для получения
     * @return узел в указанной позиции
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    private Node<E> getNode(int index) {
        Node<E> element = first;
        for (int i = 0; i < index; i++) {
            element = element.next;
        }
        return element;
    }

    /**
     * Проверяет, достигнут ли максимально возможный размер списка.
     *
     * @throws OutOfMemoryError если достигнут максимально возможный размер списка
     */
    private void checkSize() {
        if (size == Integer.MAX_VALUE) {
            throw new OutOfMemoryError("Достигнут максимально возможный размер списка");
        }
    }

    /**
     * Внутренний класс, представляющий узел двусвязного списка.
     *
     * @param <E> тип элемента в узле
     */
    private static class Node<E> {
        E val;
        Node<E> next;
        Node<E> prev;

        public Node(E val, Node<E> next, Node<E> prev) {
            this.val = val;
            this.next = next;
            this.prev = prev;
        }
    }

}
