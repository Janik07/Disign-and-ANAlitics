# Closest Pair of Points

Implementation of the Closest Pair of Points algorithm (O(n log n)) using divide-and-conquer.

## Setup
1. Ensure Java 17+ and Maven are installed.
2. Run `mvn test` to execute JUnit tests.
3. Use `mvn package` to build the project.

## Features
- Divide-and-conquer algorithm for finding the closest pair of points in 2D.
- Tests for small inputs, brute-force comparison (n ≤ 2000), and large inputs (n = 100000).
- Metrics collection for time and recursion depth (to be implemented).

## Git Workflow
- Branch: `feature/closest`
- Commits:
    - `feat(closest): divide-and-conquer implementation + tests`
    - `fix: edge cases (duplicates, tiny arrays)`