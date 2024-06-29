package ru.bratchin.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.bratchin.collection.api.MyList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class AbstractListTest {

    protected MyList<String> list;

    @BeforeEach
    void setup() {
        list = createList();
    }

    protected abstract MyList<String> createList();

    @Nested
    class AddTests {
        @Test
        void shouldAddElementToEmptyList() {
            list.add("one");

            assertThat(list.size()).isEqualTo(1);
            assertThat(list.get(0)).isEqualTo("one");
        }

        @Test
        void shouldAddNullElement() {
            list.add(null);

            assertThat(list.size()).isEqualTo(1);
            assertThat(list.get(0)).isNull();
        }

        @Test
        void shouldAddElementsToListWithMultipleElements() {
            list.add("one");
            list.add("two");
            list.add("three");

            assertThat(list.size()).isEqualTo(3);
            assertThat(list.get(0)).isEqualTo("one");
            assertThat(list.get(1)).isEqualTo("two");
            assertThat(list.get(2)).isEqualTo("three");
        }

        @Test
        void shouldInsertElementAtGivenIndex() {
            list.add("one");
            list.add("three");

            list.add(1, "two");

            assertThat(list.size()).isEqualTo(3);
            assertThat(list.get(1)).isEqualTo("two");
            assertThat(list.get(2)).isEqualTo("three");
        }

        @Test
        void shouldThrowExceptionWhenInvalidIndex() {

            list.add("one");

            assertThatThrownBy(() -> list.add(2, "InvalidIndex"))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessageContaining("Index: 2, Size: 1");
        }

        @Test
        void shouldThrowExceptionWhenNegativeIndex() {

            list.add("one");

            assertThatThrownBy(() -> list.add(-1, "negative"))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessageContaining("Index: -1, Size: 1");
        }

        /**
         * Для прохождения теста нужно увеличить размер heap
         * укажите в VM -Xms512m -Xmx14g
         */
        @Disabled
        @Test
        void shouldThrowExceptionWhenAddingElementToFullList() {
            for (int i = 0; i < Integer.MAX_VALUE - 2; i++) {
                list.add(null);
            }

            assertThatThrownBy(() -> list.add(null))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Достигнут максимально возможный размер массива");
        }
    }

    @Nested
    class RemoveTests {
        @Test
        void shouldRemoveElementAtGivenIndex() {
            list.add("one");
            list.add("two");
            list.add("three");

            list.remove(1);

            assertThat(list.size()).isEqualTo(2);
            assertThat(list.get(1)).isEqualTo("three");
        }

        @Test
        void shouldThrowExceptionWhenNegativeIndex() {
            list.add("one");

            assertThatThrownBy(() -> list.remove(-1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessageContaining("Index: -1");
        }

        @Test
        void shouldThrowExceptionWhenInvalidIndex() {
            list.add("one");

            assertThatThrownBy(() -> list.remove(6))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessageContaining("Index: 6, Size: 1");
        }

        @Test
        void shouldThrowExceptionWhenRemovingFromEmptyList() {
            assertThatThrownBy(() -> list.remove(0))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessageContaining("Index: 0, Size: 0");
        }

    }

    @Nested
    class GetTests {

        @Test
        void shouldReturnElementAtGivenIndex() {
            list.add("one");
            list.add("two");

            assertThat(list.get(1)).isEqualTo("two");
        }

        @Test
        void shouldThrowExceptionWhenNegativeIndex() {
            list.add("one");

            assertThatThrownBy(() -> list.get(-1))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessageContaining("Index: -1");
        }

        @Test
        void shouldThrowExceptionWhenInvalidIndex() {
            list.add("one");

            assertThatThrownBy(() -> list.get(6))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessageContaining("Index: 6, Size: 1");
        }

        @Test
        void shouldThrowExceptionWhenGettingFromEmptyList() {
            assertThatThrownBy(() -> list.get(0))
                    .isInstanceOf(IndexOutOfBoundsException.class)
                    .hasMessageContaining("Index: 0, Size: 0");
        }
    }

    @Nested
    class ClearTests {
        @Test
        void shouldRemoveAllElementsFromList() {
            list.add("one");
            list.add("two");

            list.clear();

            assertThat(list.size()).isEqualTo(0);
            assertThat(list.isEmpty()).isTrue();
        }
    }

    @Nested
    class SortTests {
        @Test
        void shouldSortListInNaturalOrder() {
            list.add("three");
            list.add("one");
            list.add("two");

            list.sort();

            assertThat(list.get(0)).isEqualTo("one");
            assertThat(list.get(1)).isEqualTo("three");
            assertThat(list.get(2)).isEqualTo("two");
        }

        @Test
        void shouldSortListInNaturalOrderWithNull() {
            list.add("one");
            list.add(null);
            list.add("two");
            list.add("three");

            list.sort();

            assertThat(list.get(0)).isEqualTo("one");
            assertThat(list.get(1)).isEqualTo("three");
            assertThat(list.get(2)).isEqualTo("two");
            assertThat(list.get(3)).isNull();
        }
    }

    @Nested
    class SizeAndEmptyTests {
        @Test
        void shouldReturnCorrectSizeOfList() {
            list.add("one");
            list.add("two");

            assertThat(list.size()).isEqualTo(2);
        }

        @Test
        void shouldReturnTrueIfListIsEmpty() {

            assertThat(list.isEmpty()).isTrue();
        }
    }

}
