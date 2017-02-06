import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

/******************************************************************************
 * Programming Assignment 5: Kd-Trees
 * https://xzwj.github.io/Kd-Trees/
 * See assignment: 
 *      http://coursera.cs.princeton.edu/algs4/assignments/kdtree.html
 *      http://coursera.cs.princeton.edu/algs4/checklists/kdtree.html
 *      http://introcs.cs.princeton.edu/java/assignments/
 * files: 
 *      PointSET.java
 *      KdTree.java
 * Dependencies: 
 *      algs4.jar
 * tests:
 *      KdTreeVisualizer.java
 *      RangeSearchVisualizer.java
 *      NearestNeighborVisualizer.java
 ******************************************************************************/

public class KdTree {
    // public KdTree() // construct an empty set of points
    // public boolean isEmpty() // is the set empty?
    // public int size() // number of points in the set
    // public void insert(Point2D p) // add the point to the set (if it is not
    // already in the set)
    // public boolean contains(Point2D p) // does the set contain point p?
    // public void draw() // draw all points to standard draw
    // public Iterable<Point2D> range(RectHV rect) // all points that are inside
    // the rectangle
    // public Point2D nearest(Point2D p) // a nearest neighbor in the set to
    // point p; null if the set is empty

    private static final RectHV CONTAINER = new RectHV(0, 0, 1, 1);
    private Node root;
    private int size;

    private static class Node {
        private Point2D p; // the point
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree
        private boolean isVertical;

        public Node(Point2D p, boolean isVertical) {
            this.p = p;
            this.isVertical = isVertical;
            lb = null;
            rt = null;
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        root = insert(root, p, true);
    }

    private Node insert(Node x, Point2D p, boolean isVertical) {
        if (x == null) {
            size++;
            return new Node(p, isVertical);
        }
        if (p.equals(x.p))
            return x;
        if ((x.isVertical && p.x() < x.p.x())
                || (!x.isVertical && p.y() < x.p.y()))
            x.lb = insert(x.lb, p, !isVertical);
        else
            x.rt = insert(x.rt, p, !isVertical);
        return x;
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null)
            return false;
        if (p.equals(x.p))
            return true;
        if ((x.isVertical && p.x() < x.p.x())
                || (!x.isVertical && p.y() < x.p.y()))
            return contains(x.lb, p);
        else
            return contains(x.rt, p);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        CONTAINER.draw();

        draw(root, CONTAINER);
    }

    private void draw(final Node node, final RectHV rect) {
        if (node == null)
            return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();

        StdDraw.setPenRadius();
        if (node.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            new Point2D(node.p.x(), rect.ymin()).drawTo(new Point2D(node.p.x(),
                    rect.ymax()));
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            new Point2D(rect.xmin(), node.p.y()).drawTo(new Point2D(
                    rect.xmax(), node.p.y()));
        }
        draw(node.lb, lbRect(rect, node));
        draw(node.rt, rtRect(rect, node));
    }

    private RectHV lbRect(final RectHV rect, final Node node) {
        if (node.isVertical)
            return new RectHV(rect.xmin(), rect.ymin(), node.p.x(), rect.ymax());
        else
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.p.y());
    }

    private RectHV rtRect(final RectHV rect, final Node node) {
        if (node.isVertical)
            return new RectHV(node.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
        else
            return new RectHV(rect.xmin(), node.p.y(), rect.xmax(), rect.ymax());
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        SET<Point2D> rangeSET = new SET<Point2D>();
        range(root, rect, CONTAINER, rangeSET);
        return rangeSET;
    }

    private void range(final Node node, final RectHV rect,
            final RectHV currentRect, SET<Point2D> rangeSET) {
        if (node == null)
            return;
        if (rect.contains(node.p))
            rangeSET.add(node.p);
        if (rect.intersects(lbRect(currentRect, node)))
            range(node.lb, rect, lbRect(currentRect, node), rangeSET);
        if (rect.intersects(rtRect(currentRect, node)))
            range(node.rt, rect, rtRect(currentRect, node), rangeSET);
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return nearest(root, p, CONTAINER, null);
    }

    private Point2D nearest(final Node node, final Point2D p,
            final RectHV currentRect, final Point2D candidate) {
        if (node == null)
            return candidate;

        double dcp = Double.MAX_VALUE;
        if (candidate != null) {
            dcp = p.distanceSquaredTo(candidate);
            if (currentRect.distanceSquaredTo(p) > dcp)
                return candidate;
        }
        double dnp = p.distanceSquaredTo(node.p);
        Point2D nearest = (dnp < dcp) ? node.p : candidate;

        RectHV lbRect = lbRect(currentRect, node);
        RectHV rtRect = rtRect(currentRect, node);
        if ((node.isVertical && p.x() < node.p.x())
                || (!node.isVertical && p.y() < node.p.y())) {
            nearest = nearest(node.lb, p, lbRect, nearest);
            nearest = nearest(node.rt, p, rtRect, nearest);
        } else {
            nearest = nearest(node.rt, p, rtRect, nearest);
            nearest = nearest(node.lb, p, lbRect, nearest);
        }

        return nearest;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
