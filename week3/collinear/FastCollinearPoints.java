import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {
    private final Point[] points;
    private final ArrayList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        this.points = points.clone();
        for (Point p : this.points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }

        Arrays.sort(this.points);

        for (int i = 1; i < this.points.length; i++) {
            if (this.points[i].equals(this.points[i - 1])) {
                throw new IllegalArgumentException();
            }
        }

        segments = new ArrayList<LineSegment>();
    }

    public int numberOfSegments() {
        if (segments.isEmpty()) {
            this.segments();
        }

        return segments.size();
    }

    public LineSegment[] segments() {
        if (!segments.isEmpty()) {
            return segments.toArray(new LineSegment[0]);
        }

        ArrayList<ArrayList<Point>> saved = new ArrayList<ArrayList<Point>>();
        for (Point p : points) {
            if (points.length == 1) {
                break;
            }

            Point[] clonedPoints = points.clone();
            Arrays.sort(clonedPoints, p.slopeOrder());

            ArrayList<Point> pointsInLine = new ArrayList<Point>();
            pointsInLine.add(p);
            pointsInLine.add(clonedPoints[1]);
            double prevSlope = p.slopeTo(clonedPoints[1]);

            for (int i = 2; i < clonedPoints.length; i++) {
                double currSlope = p.slopeTo(clonedPoints[i]);
                if (Double.compare(prevSlope, currSlope) == 0) {
                    pointsInLine.add(clonedPoints[i]);

                    // we should skip the iteration if it's not a last point
                    if (i != clonedPoints.length - 1) {
                        continue;
                    }
                }

                if (pointsInLine.size() >= 4) {
                    Collections.sort(pointsInLine);
                    Point first = pointsInLine.get(0);
                    Point last = pointsInLine.get(pointsInLine.size() - 1);
                    LineSegment segment = new LineSegment(first, last);

                    ArrayList<Point> ownSegment = new ArrayList<Point>();
                    ownSegment.add(first);
                    ownSegment.add(last);
                    if (!saved.contains(ownSegment)) {
                        segments.add(segment);
                        saved.add(ownSegment);
                    }
                }

                prevSlope = currSlope;

                pointsInLine.clear();
                pointsInLine.add(p);
                pointsInLine.add(clonedPoints[i]);
            }
        }

        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // empty
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
