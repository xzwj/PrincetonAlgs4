import java.util.Arrays;

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

public class FastCollinearPoints {
    // public FastCollinearPoints(Point[] points) // finds all line segments
    // containing 4 or more points
    // public int numberOfSegments() // the number of line segments
    // public LineSegment[] segments() // the line segments

    private int numberOfSegments;
    private int numberOfPoints;
    private Point[] points;
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] p) {
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

        Point[] tmp = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            tmp[i] = points[i];
        }

        for (int i = 0; i < numberOfPoints; i++) {
            Arrays.sort(tmp, points[i].slopeOrder());
            for (int j = 0; j < tmp.length;) {
                int start = j;
                int end = start;
                for (int k = start + 1; k < tmp.length
                        && points[i].slopeTo(tmp[start]) == points[i]
                                .slopeTo(tmp[k]); k++) {
                    end = k;
                }
                j = end + 1;

                if (end - start > 1) {
                    // find the min and max point from tmp[start->end]
                    int min = start;
                    int max = start;
                    for (int k = start + 1; k <= end; k++) {
                        if (tmp[k].compareTo(tmp[min]) < 0) {
                            min = k;
                        }
                        if (tmp[k].compareTo(tmp[max]) > 0) {
                            max = k;
                        }
                    }

                    if (points[i].compareTo(tmp[min]) < 0) {
                        // resize array
                        if (numberOfSegments == segments.length) {
                            LineSegment[] t = new LineSegment[2 * numberOfSegments];
                            for (int i1 = 0; i1 < numberOfSegments; i1++) {
                                t[i1] = segments[i1];
                            }
                            segments = t;
                        }

                        segments[numberOfSegments++] = new LineSegment(
                                points[i], tmp[max]);
                    }

                }
            }
        }

        // resize array
        LineSegment[] t = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            t[i] = segments[i];
        }
        segments = t;
    }

    public int numberOfSegments() {
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

        // StdOut.println(Double.POSITIVE_INFINITY > 1.0); // t
        // StdOut.println(Double.NEGATIVE_INFINITY < 1.0);
        // StdOut.println(Double.NEGATIVE_INFINITY < +0.0); // t
        // StdOut.println();

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
        // StdOut.println("1");
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        // StdOut.println("2");
        for (LineSegment segment : collinear.segments()) {
            // StdOut.println("3");
            StdOut.println(segment);
            segment.draw();
        }
//        StdOut.println("4");
        StdDraw.show();

    }

}
