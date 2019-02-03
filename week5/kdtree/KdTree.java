import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private static final double MIN_COORD = 0;
    private static final double MAX_COORD = 1;

    private Node root;
    private int count;

    private static class Node {
        private static final boolean VERTICAL = true;
        private static final boolean HORIZONTAL = false;

        private final Point2D p;
        private final RectHV rect;
        private final boolean direction;
        private Node lb;
        private Node rt;

        public Node(Point2D p, boolean direction, RectHV rect) {
            this.p = p;
            this.direction = direction;
            this.rect = rect;
        }

        public boolean isVertical() {
            return direction == VERTICAL;
        }

        public boolean isHorizontal() {
            return direction == HORIZONTAL;
        }

        public boolean contains(Point2D point) {
            if (point.equals(this.p)) {
                return true;
            }
            if (lb != null && lb.rect.contains(point) && lb.contains(point)) {
                return true;
            }
            if (rt != null && rt.rect.contains(point) && rt.contains(point)) {
                return true;
            }

            return false;
        }

        public void draw() {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            p.draw();

            StdDraw.setPenRadius();
            if (isVertical()) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(p.x(), rect.ymin(), p.x(), rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(rect.xmin(), p.y(), rect.xmax(), p.y());
            }

            if (lb != null) {
                lb.draw();
            }
            if (rt != null) {
                rt.draw();
            }
        }

        public void range(RectHV rectHV, ArrayList<Point2D> points) {
            if (rectHV.contains(p)) {
                points.add(p);
            }

            if (lb != null && lb.rect.intersects(rectHV)) {
                lb.range(rectHV, points);
            }

            if (rt != null && rt.rect.intersects(rectHV)) {
                rt.range(rectHV, points);
            }
        }

        public Point2D nearest(Point2D point, double shortestDistance) {
            Point2D nearestPoint = null;

            double distanceToPoint = point.distanceSquaredTo(p);
            if (distanceToPoint < shortestDistance) {
                shortestDistance = distanceToPoint;
                nearestPoint = p;
            }

            if (lb != null && lb.rect.distanceSquaredTo(point) < shortestDistance) {
                Point2D lbPoint = lb.nearest(point, shortestDistance);
                if (lbPoint != null) {
                    shortestDistance = point.distanceSquaredTo(lbPoint);
                    nearestPoint = lbPoint;
                }
            }

            if (rt != null && rt.rect.distanceSquaredTo(point) < shortestDistance) {
                Point2D rtPoint = rt.nearest(point, shortestDistance);
                if (rtPoint != null) {
                    nearestPoint = rtPoint;
                }
            }

            return nearestPoint;
        }
    }

    public KdTree() {
        this.count = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return count;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (contains(p)) {
            return;
        }

        this.count++;

        if (isEmpty()) {
            RectHV rect = new RectHV(MIN_COORD, MIN_COORD, MAX_COORD, MAX_COORD);
            root = new Node(p, Node.VERTICAL, rect);
            return;
        }

        Node curr = root;
        while (true) {
            if (curr.isVertical()) {
                if (p.x() < curr.p.x()) {
                    if (curr.lb != null) {
                        curr = curr.lb;
                        continue;
                    }

                    RectHV rect = new RectHV(curr.rect.xmin(), curr.rect.ymin(), curr.p.x(), curr.rect.ymax());
                    curr.lb = new Node(p, Node.HORIZONTAL, rect);
                    break;
                } else {
                    if (curr.rt != null) {
                        curr = curr.rt;
                        continue;
                    }

                    RectHV rect = new RectHV(curr.p.x(), curr.rect.ymin(), curr.rect.xmax(), curr.rect.ymax());
                    curr.rt = new Node(p, Node.HORIZONTAL, rect);
                    break;
                }
            } else if (curr.isHorizontal()) {
                if (p.y() < curr.p.y()) {
                    if (curr.lb != null) {
                        curr = curr.lb;
                        continue;
                    }

                    RectHV rect = new RectHV(curr.rect.xmin(), curr.rect.ymin(), curr.rect.xmax(), curr.p.y());
                    curr.lb = new Node(p, Node.VERTICAL, rect);
                    break;
                } else {
                    if (curr.rt != null) {
                        curr = curr.rt;
                        continue;
                    }

                    RectHV rect = new RectHV(curr.rect.xmin(), curr.p.y(), curr.rect.xmax(), curr.rect.ymax());
                    curr.rt = new Node(p, Node.VERTICAL, rect);
                    break;
                }
            }
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            return false;
        }
        return root.contains(p);
    }

    public void draw() {
        if (!isEmpty()) {
            root.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            return null;
        }

        ArrayList<Point2D> points = new ArrayList<>();

        root.range(rect, points);

        return points;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            return null;
        }

        return root.nearest(p, Double.POSITIVE_INFINITY);
    }

    public static void main(String[] args) {
        // empty
    }
}
