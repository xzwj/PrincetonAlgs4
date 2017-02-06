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

public class PointSET {
    // public PointSET() // construct an empty set of points
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

    private SET<Point2D> pointSET;

    public PointSET() {
        pointSET = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return pointSET.isEmpty();
    }

    public int size() {
        return pointSET.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        pointSET.add(p);
    }

    public boolean contains(Point2D p) {
        return pointSET.contains(p);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : pointSET) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        SET<Point2D> rangeSET = new SET<Point2D>();
        for (Point2D p : pointSET) {
            if (rect.contains(p)) {
                rangeSET.add(p);
            }
        }
        return rangeSET;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        double minDistanceSquared = Double.MAX_VALUE;
        Point2D nearest = null;
        for (Point2D point : pointSET) {
            if (point.distanceSquaredTo(p) < minDistanceSquared) {
                minDistanceSquared = point.distanceSquaredTo(p);
                nearest = point;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
