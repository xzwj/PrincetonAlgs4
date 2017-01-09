import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/******************************************************************************
 * Programming Assignment 2: Deques and Randomized Queues
 * https://xzwj.github.io/Stacks-and-Queues-Deques-and-Randomized-Queues/
 * See assignment: 
 *      http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 *      http://introcs.cs.princeton.edu/java/assignments/
 * files: 
 *      Deque.java 
 *      RandomizedQueue.java
 *      Permutation.java
 * Dependencies: 
 *      StdIn.java
 *      StdOut.java
 *      StdRandom.java
 ******************************************************************************/

// use resizing array
public class RandomizedQueue<Item> implements Iterable<Item> {
    // public RandomizedQueue() // construct an empty randomized queue
    // public boolean isEmpty() // is the queue empty?
    // public int size() // return the number of items on the queue
    // public void enqueue(Item item) // add the item
    // public Item dequeue() // remove and return a random item
    // public Item sample() // return (but do not remove) a random item
    // public Iterator<Item> iterator() // return an independent iterator over
    // items in random order
    // public static void main(String[] args) // unit testing

    private Item[] q; // queue elements
    private int n; // number of elements on queue
    private int first; // index of first element of queue
    private int last; // index of next available slot

    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        n = 0;
        first = 0;
        last = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        // double size of array if necessary and recopy to front of array
        if (n == q.length)
            resize(2 * q.length); // double size of array if necessary
        if (last == q.length) {
            last = 0;
        }
        q[last++] = item; // add item
        if (last == q.length)
            last = 0; // wrap-around
        n++;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        int r;
        if (n == 1) {
            r = first;
        } else {
            r = (StdRandom.uniform(n) + first) % q.length;
        }
        Item item = q[r];
        q[r] = q[first];
        q[first] = null; // to avoid loitering
        n--;
        first++;
        if (first == q.length)
            first = 0; // wrap-around
        // shrink size of array if necessary
        if (n > 0 && n == q.length / 4)
            resize(q.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        int r;
        if (n == 1) {
            r = first;
        } else {
            r = (StdRandom.uniform(n) + first) % q.length;
        }
        return q[r];
    }

    // resize the underlying array
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last = n;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private Item[] aux;

        public ArrayIterator() {
            aux = (Item[]) new Object[n];
            for (int j = 0; j < aux.length; j++) {
                aux[j] = q[(j + first) % q.length];
            }
            StdRandom.shuffle(aux);
        }

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = aux[(i + first) % aux.length];
            i++;
            return item;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        StdOut.println(rq.size());
        rq.enqueue(38);
        // rq.enqueue(44);
        StdOut.println(rq.sample() + "");
        StdOut.println(rq.dequeue() + "");
        rq.enqueue(42);
    }

}
