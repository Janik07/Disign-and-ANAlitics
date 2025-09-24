package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

public class ClosestPairTest {
    private static final Random rand = new Random();

    @Test
    public void testSmallInput() {
        ClosestPair.Point[] points = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(1, 1),
                new ClosestPair.Point(2, 2),
                new ClosestPair.Point(0.5, 0.5)
        };
        ClosestPair.PairResult result = ClosestPair.closestPair(points);
        assertEquals(0.7071, result.dist, 0.0001, "Distance should be ~sqrt(2)/2");
    }

    @Test
    public void testAgainstBruteForce() {
        for (int trial = 0; trial < 100; trial++) {
            int n = rand.nextInt(1000) + 2;
            ClosestPair.Point[] points = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                points[i] = new ClosestPair.Point(rand.nextDouble() * 100, rand.nextDouble() * 100);
            }
            ClosestPair.PairResult fast = ClosestPair.closestPair(points);
            ClosestPair.PairResult brute = bruteForceAll(points);
            assertEquals(brute.dist, fast.dist, 0.0001, "Distances should match");
            assertTrue(isValidPair(points, fast.p1, fast.p2, fast.dist), "Invalid pair");
        }
    }

    private ClosestPair.PairResult bruteForceAll(ClosestPair.Point[] points) {
        double minDist = Double.POSITIVE_INFINITY;
        ClosestPair.Point p1 = null, p2 = null;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = Math.sqrt(
                        (points[i].x - points[j].x) * (points[i].x - points[j].x) +
                                (points[i].y - points[j].y) * (points[i].y - points[j].y)
                );
                if (dist < minDist) {
                    minDist = dist;
                    p1 = points[i];
                    p2 = points[j];
                }
            }
        }
        return new ClosestPair.PairResult(p1, p2, minDist);
    }

    private boolean isValidPair(ClosestPair.Point[] points, ClosestPair.Point p1, ClosestPair.Point p2, double dist) {
        for (ClosestPair.Point p : points) {
            if (p == p1 || p == p2) continue;
            for (ClosestPair.Point q : points) {
                if (q == p || q == p1 || q == p2) continue;
                double d = Math.sqrt((p.x - q.x) * (p.x - q.x) + (p.y - q.y) * (p.y - q.y));
                if (d < dist - 0.0001) return false;
            }
        }
        return true;
    }

    @Test
    public void testLargeInput() {
        int n = 100000;
        ClosestPair.Point[] points = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new ClosestPair.Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
        }
        long start = System.nanoTime();
        ClosestPair.PairResult result = ClosestPair.closestPair(points);
        long end = System.nanoTime();
        System.out.println("Time for n=" + n + ": " + (end - start) / 1e9 + " seconds");
        assertNotNull(result, "Result should not be null");
    }
}