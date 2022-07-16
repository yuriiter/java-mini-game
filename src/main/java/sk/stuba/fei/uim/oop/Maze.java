package sk.stuba.fei.uim.oop;

import java.util.Random;

public class Maze {
    public static final int DIMENSION = 15;

    private final Random random = new Random();

    private boolean[][] north;
    private boolean[][] west;
    private boolean[][] south;
    private boolean[][] east;

    private boolean[][] visited;

    private int[] startPosition;
    private int[] endPosition;

    private Ball ball;


    public Maze() {
        this.init();
        this.generate();
    }

    public void updateMaze() {
        this.init();
        this.generate();
    }

    public Ball getBall() {
        return this.ball;
    }

    private void init() {
        this.visited = new boolean[Maze.DIMENSION + 2][Maze.DIMENSION + 2];
        for(int x = 0; x < Maze.DIMENSION + 2; x++) {
            this.visited[x][0] = true;
            this.visited[x][Maze.DIMENSION + 1] = true;
        }
        for(int y = 0; y < Maze.DIMENSION + 2; y++) {
            this.visited[0][y] = true;
            this.visited[Maze.DIMENSION + 1][y] = true;
        }

        north = new boolean[Maze.DIMENSION + 2][Maze.DIMENSION + 2];
        west = new boolean[Maze.DIMENSION + 2][Maze.DIMENSION + 2];
        south = new boolean[Maze.DIMENSION + 2][Maze.DIMENSION + 2];
        east = new boolean[Maze.DIMENSION + 2][Maze.DIMENSION + 2];
        for(int x = 0; x < Maze.DIMENSION + 2; x++) {
            for(int y = 0; y < Maze.DIMENSION + 2; y++) {
                north[x][y] = true;
                west[x][y] = true;
                south[x][y] = true;
                east[x][y] = true;
            }
        }
    }

    private void generate() {
        int x = random.nextInt(Maze.DIMENSION) + 1;
        int y = random.nextInt(Maze.DIMENSION) + 1;
        this.startPosition = new int[]{x, y};
        this.ball = new Ball(x, y);
        generate(x, y);
    }

    private void generate(int x, int y) {
        this.endPosition = new int[]{x, y};
        visited[x][y] = true;
        while(hasUnvisitedNeighbour(x, y)) {
            while(true) {
                int r = random.nextInt(4);
                if (r == 0 && !visited[x][y+1]) {
                    north[x][y] = false;
                    south[x][y+1] = false;
                    generate(x, y + 1);
                    break;
                }
                else if (r == 1 && !visited[x+1][y]) {
                    east[x][y] = false;
                    west[x+1][y] = false;
                    generate(x+1, y);
                    break;
                }
                else if (r == 2 && !visited[x][y-1]) {
                    south[x][y] = false;
                    north[x][y-1] = false;
                    generate(x, y-1);
                    break;
                }
                else if (r == 3 && !visited[x-1][y]) {
                    west[x][y] = false;
                    east[x-1][y] = false;
                    generate(x-1, y);
                    break;
                }
            }
        }
    }

    private boolean hasUnvisitedNeighbour(int x, int y) {
        return !visited[x][y + 1] || !visited[x][y - 1] || !visited[x + 1][y] || !visited[x - 1][y];
    }

    public boolean[][] getNorth() {
        return north;
    }

    public boolean[][] getWest() {
        return west;
    }

    public boolean[][] getSouth() {
        return south;
    }

    public boolean[][] getEast() {
        return east;
    }

    public int[] getStartPosition() {
        return new int[]{startPosition[0] - 1, startPosition[1] - 1};
    }

    public int[] getEndPosition() {
        return new int[]{endPosition[0] - 1, endPosition[1] - 1};
    }
}
