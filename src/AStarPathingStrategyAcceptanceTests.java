/**
 * JUnit tests for Sorts assignment.
 *
 * @author Paul Hatalsky
 * @author Ayaan Kazerouni
 * @version 11/17/2017 Developed for CPE 203 A* testing
 */

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
//import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class AStarPathingStrategyAcceptanceTests {
    private static final PathingStrategy strategy = new AStarPathingStrategy();

    private static GridValues[][] grid;
    private static final int ROWS = 9;
    private static final int COLS = 9;

    private enum GridValues {BACKGROUND, OBSTACLE}

    @BeforeAll
    public static void before() {
        grid = new GridValues[ROWS][COLS];
        PathingStrategy.publicizePoint();
    }
    private static int getX(Point p) {
        return PathingStrategy.getX(p);
    }

    private static int getY(Point p) {
        return PathingStrategy.getY(p);
    }

    private static void initialize_grid() {
        for (GridValues[] gridValues : grid) {
            Arrays.fill(gridValues, GridValues.BACKGROUND);
        }
        grid[1][3] = GridValues.OBSTACLE;
        for (int row = 1; row < 6; row++) {
            grid[row][4] = GridValues.OBSTACLE;
        }
        grid[5][2] = GridValues.OBSTACLE;
        grid[5][3] = GridValues.OBSTACLE;
    }

    private static boolean withinBounds(Point p, GridValues[][] grid) {
        return getY(p) >= 0 && getY(p) < grid.length &&
                getX(p) >= 0 && getX(p) < grid[0].length;
    }

    private static boolean neighbors(Point p1, Point p2) {
        return getX(p1) + 1 == getX(p2) && getY(p1) == getY(p2) ||
                getX(p1) - 1 == getX(p2) && getY(p1) == getY(p2) ||
                getX(p1) == getX(p2) && getY(p1) + 1 == getY(p2) ||
                getX(p1) == getX(p2) && getY(p1) - 1 == getY(p2);
    }

    private static boolean two_doors_down(Point p1, Point p2) {
        return getX(p1) + 2 == getX(p2) && getY(p1) == getY(p2) ||
                getX(p1) - 2 == getX(p2) && getY(p1) == getY(p2) ||
                getX(p1) == getX(p2) && getY(p1) + 2 == getY(p2) ||
                getX(p1) == getX(p2) && getY(p1) - 2 == getY(p2) ||
                getX(p1) + 1 == getX(p2) && getY(p1) + 1 == getY(p2) ||
                getX(p1) + 1 == getX(p2) && getY(p1) - 1 == getY(p2) ||
                getX(p1) - 1 == getX(p2) && getY(p1) + 1 == getY(p2) ||
                getX(p1) - 1 == getX(p2) && getY(p1) - 1 == getY(p2);
    }

    @Test
    public void test01_computePath01() {
        initialize_grid();
        Point start = new Point(3, 4);
        Point end = new Point(5, 4);
        List<Point> path =
                strategy.computePath(start, end,
                        p -> withinBounds(p, grid) && grid[getY(p)][getX(p)] != GridValues.OBSTACLE,
                        AStarPathingStrategyAcceptanceTests::neighbors,
                        PathingStrategy.CARDINAL_NEIGHBORS);

        List<Point> expected = List.of(new Point(2, 4), new Point(1, 4), new Point(1, 5), new Point(1, 6), new Point(2, 6),
                new Point(3, 6), new Point(4, 6), new Point(5, 6), new Point(5, 5));

        assertEquals(expected, path);
    }

    @Test
    public void test02_computePath02() {
        initialize_grid();
        List<Point> path =
                strategy.computePath(new Point(5, 4),
                        new Point(3, 4),
                        p -> withinBounds(p, grid) && grid[getY(p)][getX(p)] != GridValues.OBSTACLE,
                        AStarPathingStrategyAcceptanceTests::neighbors,
                        PathingStrategy.CARDINAL_NEIGHBORS);

        List<Point> expected = List.of(new Point(5, 5), new Point(5, 6), new Point(4, 6), new Point(3, 6), new Point(2, 6),
                new Point(1, 6), new Point(1, 5), new Point(1, 4), new Point(2, 4));
        assertEquals(expected, path);
    }

    @Test
    public void test03_computePath03() {
        initialize_grid();
        grid[5][1] = GridValues.OBSTACLE;
        grid[6][1] = GridValues.OBSTACLE;
        grid[3][2] = GridValues.OBSTACLE;
        List<Point> path =
                strategy.computePath(new Point(3, 4),
                        new Point(5, 4),
                        p -> withinBounds(p, grid) && grid[getY(p)][getX(p)] != GridValues.OBSTACLE,
                        AStarPathingStrategyAcceptanceTests::neighbors,
                        PathingStrategy.CARDINAL_NEIGHBORS);

        List<Point> expected = List.of(new Point(3, 3), new Point(3, 2), new Point(2, 2), new Point(2, 1), new Point(2, 0),
                new Point(3, 0), new Point(4, 0), new Point(5, 0), new Point(5, 1), new Point(5, 2), new Point(5, 3));
        assertEquals(expected, path);
    }

    @Test
    public void test04_computePath04() {
        initialize_grid();
        grid[5][1] = GridValues.OBSTACLE;
        grid[6][1] = GridValues.OBSTACLE;
        grid[3][2] = GridValues.OBSTACLE;
        List<Point> path =
                strategy.computePath(new Point(5, 4),
                        new Point(3, 4),
                        p -> withinBounds(p, grid) && grid[getY(p)][getX(p)] != GridValues.OBSTACLE,
                        AStarPathingStrategyAcceptanceTests::neighbors,
                        PathingStrategy.CARDINAL_NEIGHBORS);

        List<Point> expected = List.of(new Point(5, 3), new Point(5, 2), new Point(5, 1), new Point(5, 0), new Point(4, 0),
                new Point(3, 0), new Point(2, 0), new Point(2, 1), new Point(2, 2), new Point(3, 2), new Point(3, 3));
        assertEquals(expected, path);
    }

    @Test
    public void test05_computePath07() {
        for (GridValues[] gridValues : grid) {
            Arrays.fill(gridValues, GridValues.BACKGROUND);
        }
        grid[1][1] = GridValues.OBSTACLE;
        grid[1][3] = GridValues.OBSTACLE;
        grid[1][5] = GridValues.OBSTACLE;
        grid[1][6] = GridValues.OBSTACLE;
        grid[1][7] = GridValues.OBSTACLE;
        grid[2][2] = GridValues.OBSTACLE;
        grid[2][4] = GridValues.OBSTACLE;
        grid[2][7] = GridValues.OBSTACLE;
        grid[3][2] = GridValues.OBSTACLE;
        grid[3][4] = GridValues.OBSTACLE;
        grid[3][7] = GridValues.OBSTACLE;
        grid[4][2] = GridValues.OBSTACLE;
        grid[4][4] = GridValues.OBSTACLE;
        grid[4][7] = GridValues.OBSTACLE;
        grid[5][1] = GridValues.OBSTACLE;
        grid[5][4] = GridValues.OBSTACLE;
        grid[5][5] = GridValues.OBSTACLE;
        grid[5][7] = GridValues.OBSTACLE;
        grid[6][2] = GridValues.OBSTACLE;
        grid[6][4] = GridValues.OBSTACLE;
        grid[6][7] = GridValues.OBSTACLE;
        grid[7][0] = GridValues.OBSTACLE;
        grid[7][2] = GridValues.OBSTACLE;
        grid[7][4] = GridValues.OBSTACLE;
        grid[7][6] = GridValues.OBSTACLE;
        grid[7][7] = GridValues.OBSTACLE;
        grid[8][0] = GridValues.OBSTACLE;
        grid[8][4] = GridValues.OBSTACLE;
        List<Point> path =
                strategy.computePath(new Point(3, 4),
                        new Point(5, 4),
                        p -> withinBounds(p, grid) && grid[getY(p)][getX(p)] != GridValues.OBSTACLE,
                        AStarPathingStrategyAcceptanceTests::neighbors,
                        PathingStrategy.CARDINAL_NEIGHBORS);

        List<Point> expected = List.of(new Point(3, 5), new Point(3, 6), new Point(3, 7), new Point(3, 8), new Point(2, 8), new Point(1, 8),
                new Point(1, 7), new Point(1, 6), new Point(0, 6), new Point(0, 5), new Point(0, 4), new Point(0, 3),
                new Point(0, 2), new Point(0, 1), new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0),
                new Point(4, 0), new Point(5, 0), new Point(6, 0), new Point(7, 0), new Point(8, 0), new Point(8, 1),
                new Point(8, 2), new Point(8, 3), new Point(8, 4), new Point(8, 5), new Point(8, 6), new Point(8, 7),
                new Point(8, 8), new Point(7, 8), new Point(6, 8), new Point(5, 8), new Point(5, 7), new Point(5, 6),
                new Point(6, 6), new Point(6, 5), new Point(6, 4));
        assertEquals(expected, path);
    }

    @Test
    public void test06_computePath08() {
        for (GridValues[] gridValues : grid) {
            Arrays.fill(gridValues, GridValues.BACKGROUND);
        }
        grid[0][4] = GridValues.OBSTACLE;
        grid[1][1] = GridValues.OBSTACLE;
        grid[1][4] = GridValues.OBSTACLE;
        grid[4][0] = GridValues.OBSTACLE;
        grid[4][1] = GridValues.OBSTACLE;

        List<Point> path =
                strategy.computePath(new Point(0, 0),
                        new Point(8, 8),
                        p -> withinBounds(p, grid) && grid[getY(p)][getX(p)] != GridValues.OBSTACLE,
                        AStarPathingStrategyAcceptanceTests::neighbors,
                        PathingStrategy.CARDINAL_NEIGHBORS);


        assertEquals(15, path.size());
    }

    @Test
    public void test07_computePath09() {
        for (GridValues[] gridValues : grid) {
            Arrays.fill(gridValues, GridValues.BACKGROUND);
        }
        grid[0][4] = GridValues.OBSTACLE;
        grid[1][1] = GridValues.OBSTACLE;
        grid[1][4] = GridValues.OBSTACLE;
        grid[4][0] = GridValues.OBSTACLE;
        grid[4][1] = GridValues.OBSTACLE;

        List<Point> path =
                strategy.computePath(new Point(8, 8),
                        new Point(0, 0),
                        p -> withinBounds(p, grid) && grid[getY(p)][getX(p)] != GridValues.OBSTACLE,
                        AStarPathingStrategyAcceptanceTests::neighbors,
                        PathingStrategy.CARDINAL_NEIGHBORS);
        assertEquals(15, path.size());
    }

}
