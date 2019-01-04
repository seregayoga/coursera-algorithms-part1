import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size = 0;
    private Item[] arr;

    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size == arr.length) {
            resize(size * 2);
        }

        arr[size] = item;
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int i = StdRandom.uniform(size);
        Item item = arr[i];
        arr[i] = arr[size - 1];
        arr[size - 1] = null;

        size--;

        if (size > 0 && size == arr.length / 4) {
            resize(arr.length / 2);
        }

        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return arr[StdRandom.uniform(size)];
    }

    private class QueueIterator implements Iterator<Item> {
        private final Item[] arr;
        private final int size;
        private int currIndex = 0;

        public QueueIterator(Item[] arr, int size) {
            this.arr = arr.clone();
            this.size = size;

            StdRandom.shuffle(this.arr, 0, size);
        }

        public boolean hasNext() {
            return currIndex < size;
        }

        public Item next() {
            if (currIndex >= size) {
                throw new NoSuchElementException();
            }

            return arr[currIndex++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new QueueIterator(arr, size);
    }


    private void resize(int newSize) {
        Item[] newArr = (Item[]) new Object[newSize];

        int len = Math.min(arr.length, newSize);
        for (int i = 0; i < len; i++) {
            newArr[i] = arr[i];
        }

        arr = newArr;
    }

    public static void main(String[] args) {
        // nothing here
    }
}
