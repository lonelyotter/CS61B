package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T> {
    private int size;
    private final Node sentinel;
    private final Node last;

    private class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node() {
            prev = null;
            next = null;
            item = null;
        }

        public Node(T i, Node p, Node n) {
            prev = p;
            next = n;
            item = i;
        }
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node p;

        public LinkedListDequeIterator() {
            p = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return p.next != last;
        }

        @Override
        public T next() {
            T item = p.item;
            p = p.next;
            return item;
        }
    }

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node();
        last = new Node();
        sentinel.next = last;
        last.prev = sentinel;
    }

    @Override
    public void addFirst(T item) {
        Node tmp = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = tmp;
        sentinel.next = tmp;
        size++;
    }

    @Override
    public void addLast(T item) {
        Node tmp = new Node(item, last.prev, last);
        last.prev.next = tmp;
        last.prev = tmp;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (size != 0) {
            Node tmp = sentinel.next;

            while (tmp != last) {
                System.out.print(tmp.item + " ");
                tmp = tmp.next;
            }
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            Node tmp = sentinel.next;
            sentinel.next = tmp.next;
            tmp.next.prev = sentinel;
            size--;
            return tmp.item;
        }
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            Node tmp = last.prev;
            last.prev = tmp.prev;
            tmp.prev.next = last;
            size--;
            return tmp.item;
        }
    }

    @Override
    public T get(int index) {
        if (index > size - 1 || index < 0) {
            return null;
        } else {
            Node tmp = sentinel.next;

            for (int i = 0; i < index; i++) {
                tmp = tmp.next;
            }

            return tmp.item;
        }
    }

    public T getRecursive(int index) {
        if (index > size - 1 || index < 0) {
            return null;
        } else {
            return getRecursive(index, sentinel.next);
        }
    }

    /**
     * the method helps complement public method getRecursive
     *
     * @param index the position of the required item
     * @param start the position of the node start for index
     * @return the required item
     */
    private T getRecursive(int index, Node start) {
        if (index == 0) {
            return start.item;
        } else {
            return getRecursive(index - 1, start.next);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (!(o instanceof LinkedListDeque)) {
            return false;
        }

        LinkedListDeque<T> lld = (LinkedListDeque<T>) o;
        if (lld.size() != size) {
            return false;
        }

        Node p = this.sentinel.next;
        Node q = lld.sentinel.next;

        for (int i = 0; i < size; i++) {
            if (!p.item.equals(q.item)) {
                return false;
            }
        }
        return true;
    }

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }
}
