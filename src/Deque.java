import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

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

// use double linked list
public class Deque<Item> implements Iterable<Item> {
    // public Deque() // construct an empty deque
    // public boolean isEmpty() // is the deque empty?
    // public int size() // return the number of items on the deque
    // public void addFirst(Item item) // add the item to the front
    // public void addLast(Item item) // add the item to the end
    // public Item removeFirst() // remove and return the item from the front
    // public Item removeLast() // remove and return the item from the end
    // public Iterator<Item> iterator() // return an iterator over items in
    // order from front to end
    // public static void main(String[] args) // unit testing

    private Node<Item> first;
    private Node<Item> last;
    private int n;

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> front;
        private Node<Item> next;
    }

    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        if (oldfirst != null) {
            oldfirst.front = first;
        }
        if (last == null) {
            last = first;
        }
        n++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        last.front = null;
        if (isEmpty() || first == null)
            first = last;
        else {
            oldlast.next = last;
            last.front = oldlast;
        }
        n++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.front = null;
        }
        n--;
        if (isEmpty())
            last = null; // to avoid loitering
        return item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        Item item = last.item;
        last = last.front;
        if (last != null) {
            last.next = null;
        }
        n--;
        if (isEmpty())
            first = null; // to avoid loitering
        return item; // return the saved item
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        StdOut.println(deque.removeFirst() + "");
    }

}
