package ru.bratchin.collection;

import ru.bratchin.collection.api.MyList;

public class MyLinkedListTest extends AbstractListTest {

    @Override
    protected MyList<String> createList() {
        return new MyLinkedList<>();
    }

}