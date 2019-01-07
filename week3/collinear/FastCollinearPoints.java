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
        if (!segments.isEmpty() || points.length < 4) {
            return segments.toArray(new LineSegment[0]);
        }

        ArrayList<ArrayList<Point>> ownSegments = new ArrayList<ArrayList<Point>>();
        for (Point p : points) {

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

                    ArrayList<Point> ownSegment = new ArrayList<Point>();
                    ownSegment.add(first);
                    ownSegment.add(last);
                    ownSegments.add(ownSegment);
                }

                prevSlope = currSlope;

                pointsInLine.clear();
                pointsInLine.add(p);
                pointsInLine.add(clonedPoints[i]);
            }
        }

        if (ownSegments.isEmpty()) {
            return segments.toArray(new LineSegment[0]);
        }

        ownSegments.sort((ArrayList<Point> segment1, ArrayList<Point> segment2) -> {
            if (!segment1.get(0).equals(segment2.get(0))) {
                return segment1.get(0).compareTo(segment2.get(0));
            }
            return segment1.get(1).compareTo(segment2.get(1));
        });

        ArrayList<Point> firstOwnSegment = ownSegments.get(0);

        segments.add(new LineSegment(firstOwnSegment.get(0), firstOwnSegment.get(1)));
        for (int i = 1; i < ownSegments.size(); i++) {
            ArrayList<Point> ownSegment = ownSegments.get(i);
            if (!ownSegment.equals(ownSegments.get(i - 1))) {
                segments.add(new LineSegment(ownSegment.get(0), ownSegment.get(1)));
            }
        }

        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // empty
    }
}
