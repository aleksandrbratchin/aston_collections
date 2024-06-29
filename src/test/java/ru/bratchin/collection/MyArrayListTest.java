package ru.bratchin.collection;

import ru.bratchin.collection.api.MyList;

public class MyArrayListTest extends AbstractListTest {

    @Override
    protected MyList<String> createList() {
        return new MyArrayList<>();
    }

}