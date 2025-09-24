package com.example;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    static class Point {
        double x, y;
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    static class PairResult {
        Point p1, p2;
        double dist;
        PairResult(Point p1, Point p2, double dist) {
            this.p1 = p1;
            this.p2 = p2;
            this.dist = dist;
        }
    }

    public static PairResult closestPair(Point[] points) {
        if (points.length < 2) return null;
        Point[] byX = points.clone();
        Arrays.sort(byX, Comparator.comparingDouble(p -> p.x));
        Point[] byY = points.clone();
        Arrays.sort(byY, Comparator.comparingDouble(p -> p.y));
        return closestPairRec(byX, byY, 0, points.length - 1, 0, new Metrics());
    }

    private static PairResult closestPairRec(Point[] byX, Point[] byY, int left, int right, int depth, Metrics metrics) {
        metrics.log("metrics.csv", "closest_pair", right - left + 1, 0, depth);
        if (right - left <= 3) {
            return bruteForce(byX, left, right);
        }
        int mid = (left + right) / 2;
        double midX = byX[mid].x;
        Point[] leftY = new Point[mid - left + 1];
        Point[] rightY = new Point[right - mid];
        int leftCount = 0, rightCount = 0;
        for (Point p : byY) {
            if (p.x <= midX && leftCount < leftY.length) {
                leftY[leftCount++] = p;
            } else if (rightCount < rightY.length) {
                rightY[rightCount++] = p;
            }
        }
        PairResult leftResult = closestPairRec(byX, leftY, left, mid, depth + 1, metrics);
        PairResult rightResult = closestPairRec(byX, rightY, mid + 1, right, depth + 1, metrics);
        double d = Math.min(leftResult.dist, rightResult.dist);
        PairResult minResult = leftResult.dist <= rightResult.dist ? leftResult : rightResult;
        Point[] strip = new Point[right - left + 1];
        int stripCount = 0;
        for (Point p : byY) {
            if (Math.abs(p.x - midX) < d) {
                strip[stripCount++] = p;
            }
        }
        for (int i = 0; i < stripCount; i++) {
            for (int j = i + 1; j < stripCount && (strip[j].y - strip[i].y) < d; j++) {
                double dist = distance(strip[i], strip[j]);
                if (dist < d) {
                    d = dist;
                    minResult = new PairResult(strip[i], strip[j], dist);
                }
            }
        }
        return minResult;
    }

    private static PairResult bruteForce(Point[] points, int left, int right) {
        double minDist = Double.POSITIVE_INFINITY;
        Point p1 = null, p2 = null;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                double dist = distance(points[i], points[j]);
                if (dist < minDist) {
                    minDist = dist;
                    p1 = points[i];
                    p2 = points[j];
                }
            }
        }
        return new PairResult(p1, p2, minDist);
    }

    private static double distance(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }
}