import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private final SET<Point2D> set;

    public PointSET() {
        set = new SET<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return set.contains(p);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);

        for (Point2D point : set) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        SET<Point2D> points = new SET<>();
        for (Point2D point : set) {
            if (rect.contains(point)) {
                points.add(point);
            }
        }

        return points;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Point2D nearestPoint = null;
        double minDistance = Double.POSITIVE_INFINITY;

        for (Point2D point : set) {
            double possibleMinDistance = point.distanceSquaredTo(p);
            if (possibleMinDistance < minDistance) {
                nearestPoint = point;
                minDistance = possibleMinDistance;
            }
        }

        return nearestPoint;
    }

    public static void main(String[] args) {
        // empty
    }
}
