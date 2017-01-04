import java.util.Iterator;
import java.util.NoSuchElementException;

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
        private Node<Item> next;
    }

    public Deque() {

    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    public void addLast(Item item) {
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty())
            first = last;
        else
            oldlast.next = last;
        n++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty())
            last = null; // to avoid loitering
        return item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Stack underflow");
        Item item = first.item; // save item to return
        first = first.next; // delete first node
        n--;
        return item; // return the saved item
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

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

    }

}
