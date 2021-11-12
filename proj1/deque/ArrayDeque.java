package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T> {
    private int size;
    private T[] items;
    private int nextFirst = 0;
    private int nextLast = 1;
    static final private double EXPAND_FACTOR = 2;
    static final private double USAGE_RATIO = 0.25;
    static final private double SHRINK_FACTOR = 0.5;
    static final private int BASE_CAPACITY = 16;

    private class ArrayDequeIterator implements Iterator<T> {
        private int index;

        public ArrayDequeIterator() {
            index = indexAdd(nextFirst, 1);
        }

        @Override
        public boolean hasNext() {
            return index != indexAdd(nextLast, -1);
        }

        @Override
        public T next() {
            T tmp = items[index];
            index = indexAdd(index, 1);
            return tmp;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
    }

    private boolean isFull() {
        return size == items.length;
    }

    private boolean isSparse() {
        return items.length > BASE_CAPACITY && size < (items.length * USAGE_RATIO);
    }

    private int indexAdd(int index, int num) {
        index += num;
        if (index >= items.length) {
            index -= items.length;
        } else if (index < 0) {
            index += items.length;
        }
        return index;
    }

    private void expandArray() {
        resizeArray(EXPAND_FACTOR);
    }

    private void shrinkArray() {
        resizeArray(SHRINK_FACTOR);
    }

    private void resizeArray(double factor) {
        T[] newItems = (T[]) new Object[(int) (items.length * factor)];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[indexAdd(nextFirst, i + 1)];
        }
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public void addFirst(T item) {
        if (isFull()) {
            expandArray();
        }

        items[nextFirst] = item;
        nextFirst = indexAdd(nextFirst, -1);
        size++;
    }

    @Override
    public void addLast(T item) {
        if (isFull()) {
            expandArray();
        }

        items[nextLast] = item;
        nextLast = indexAdd(nextLast, 1);
        size++;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        nextFirst = indexAdd(nextFirst, 1);
        T tmp = items[nextFirst];
        size--;
        if (isSparse()) {
            shrinkArray();
        }
        return tmp;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextLast = indexAdd(nextLast, -1);
        T tmp = items[nextLast];
        size--;
        if (isSparse()) {
            shrinkArray();
        }
        return tmp;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            return items[indexAdd(nextFirst, index + 1)];
        }
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (!(o instanceof ArrayDeque)) {
            return false;
        }
        ArrayDeque<T> ad = (ArrayDeque<T>) o;
        if (ad.size() != size) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (!get(i).equals(ad.get(i))) {
                return false;
            }
        }

        return true;
    }
}
