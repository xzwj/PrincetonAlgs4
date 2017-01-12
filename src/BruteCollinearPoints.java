import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/******************************************************************************
 * Programming Assignment 3: Collinear Points
 * https://xzwj.github.io/Mergesort-Collinear-Points/
 * See assignment: 
 *      http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 *      http://introcs.cs.princeton.edu/java/assignments/
 * files: 
 *      Point.java 
 *      BruteCollinearPoints.java 
 *      FastCollinearPoints.java
 * Dependencies: 
 *      algs4.jar
 *      LineSegment.java
 ******************************************************************************/

public class BruteCollinearPoints {
    // public BruteCollinearPoints(Point[] points) // finds all line segments
    // containing 4 points
    // public int numberOfSegments() // the number of line segments
    // public LineSegment[] segments() // the line segments
    private int numberOfSegments;
    private int numberOfPoints;
    private Point[] points;
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] p) {
        Point[] ps = p.clone();
        if (ps == null)
            throw new NullPointerException();

        // Insertion Sort
        for (int i = 1; i < ps.length; i++) {
            if (ps[i] == null)
                throw new NullPointerException();
            for (int j = i; j > 0 && ps[j].compareTo(ps[j - 1]) <= 0; j--) {
                if (ps[j].compareTo(ps[j - 1]) == 0)
                    throw new IllegalArgumentException("repeated point");
                Point tmp = ps[j];
                ps[j] = ps[j - 1];
                ps[j - 1] = tmp;
            }

        }

        numberOfPoints = ps.length;
        numberOfSegments = 0;
        this.points = new Point[numberOfPoints];
        for (int i = 0; i < ps.length; i++) {
            this.points[i] = ps[i];
        }

        find();
    }

    private void find() {
        numberOfSegments = 0;
        segments = new LineSegment[1]; // use resizing array

        for (int i1 = 0; i1 < numberOfPoints - 3; i1++) {
            for (int i2 = i1 + 1; i2 < numberOfPoints - 2; i2++) {
                for (int i3 = i2 + 1; i3 < numberOfPoints - 1; i3++) {
                    for (int i4 = i3 + 1; i4 < numberOfPoints; i4++) {
                        if ((points[i1].slopeTo(points[i2]) == points[i1]
                                .slopeTo(points[i3]))
                                && (points[i1].slopeTo(points[i2]) == points[i1]
                                        .slopeTo(points[i4]))) {
                            // resize array
                            if (numberOfSegments == segments.length) {
                                LineSegment[] tmp = new LineSegment[2 * numberOfSegments];
                                for (int i = 0; i < numberOfSegments; i++) {
                                    tmp[i] = segments[i];
                                }
                                segments = tmp;
                            }

                            segments[numberOfSegments++] = new LineSegment(
                                    points[i1], points[i4]);
                        }
                    }
                }
            }
        }

        // resize array
        LineSegment[] tmp = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            tmp[i] = segments[i];
        }
        segments = tmp;
    }

    public int numberOfSegments() {
        segments();
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        // StdOut.println(numberOfSegments);
        return segments.clone();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }

}
