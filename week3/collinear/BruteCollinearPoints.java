import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final Point[] points;
    private final ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
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
            for (Point q : points) {
                if (q.equals(p)) {
                    continue;
                }

                for (Point r : points) {
                    if (r.equals(p)) {
                        continue;
                    }
                    if (r.equals(q)) {
                        continue;
                    }

                    if (Double.compare(p.slopeTo(q), p.slopeTo(r)) != 0) {
                        continue;
                    }

                    for (Point s : points) {
                        if (s.equals(p)) {
                            continue;
                        }
                        if (s.equals(q)) {
                            continue;
                        }
                        if (s.equals(r)) {
                            continue;
                        }

                        if (Double.compare(p.slopeTo(q), p.slopeTo(s)) != 0) {
                            continue;
                        }

                        Point[] pointsInLine = {p, q, r, s};
                        Arrays.sort(pointsInLine);

                        Point first = pointsInLine[0];
                        Point last = pointsInLine[3];
                        LineSegment segment = new LineSegment(first, last);

                        ArrayList<Point> ownSegment = new ArrayList<Point>();
                        ownSegment.add(first);
                        ownSegment.add(last);
                        if (!saved.contains(ownSegment)) {
                            segments.add(segment);
                            saved.add(ownSegment);
                        }
                    }
                }
            }
        }

        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // empty
    }
}
