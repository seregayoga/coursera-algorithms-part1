import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size = 0;

    private Node first;
    private Node last;

    private class Node {
        Item item;
        Node prev;
        Node next;
    }


    public Deque() {
        // construct
    }

    public boolean isEmpty() {
        return first == null && last == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        size++;

        Node node = new Node();
        node.item = item;

        node.next = first;
        first = node;

        if (node.next != null) {
            node.next.prev = node;
        }

        if (last == null) {
            last = first;
        }
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        size++;

        Node node = new Node();
        node.item = item;

        if (last != null) {
            last.next = node;
        }

        node.prev = last;
        last = node;

        if (first == null) {
            first = last;
        }
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        size--;

        Item item = first.item;

        if (first == last) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }

        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        size--;

        Item item = last.item;

        if (last == first) {
            last = null;
            first = null;
        } else {
            last = last.prev;
            last.next = null;
        }

        return item;
    }

    private class QueueIterator implements Iterator<Item> {
        private Node curr;

        public QueueIterator(Node first) {
            curr = first;
        }

        public boolean hasNext() {
            return curr != null;
        }

        public Item next() {
            if (curr == null) {
                throw new NoSuchElementException();
            }

            Item item = curr.item;

            curr = curr.next;

            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new QueueIterator(first);
    }

    public static void main(String[] args) {
        // nothing here
    }
}
