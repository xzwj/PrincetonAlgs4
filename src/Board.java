import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/******************************************************************************
 * Programming Assignment 4: 8 Puzzle
 * https://xzwj.github.io/8-Puzzle/
 * See assignment: 
 *      http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
 *      http://introcs.cs.princeton.edu/java/assignments/
 * files: 
 *      Board.java 
 *      Solver.java 
 * Dependencies: 
 *      algs4.jar
 * tests:
 *      PuzzleChecker.java
 ******************************************************************************/

public class Board {
    // public Board(int[][] blocks) // construct a board from an n-by-n array of
    // blocks
    // // (where blocks[i][j] = block in row i, column j)
    // public int dimension() // board dimension n
    // public int hamming() // number of blocks out of place
    // public int manhattan() // sum of Manhattan distances between blocks and
    // goal
    // public boolean isGoal() // is this board the goal board?
    // public Board twin() // a board that is obtained by exchanging any pair of
    // blocks
    // public boolean equals(Object y) // does this board equal y?
    // public Iterable<Board> neighbors() // all neighboring boards
    // public String toString() // string representation of this board (in the
    // output format specified below)
    // public static void main(String[] args) // unit tests (not graded)

    private int[][] blocks;
    private int dimension;
    private int hamming;
    private int manhattan;

    public Board(int[][] blocks) {
        dimension = blocks.length;
        this.blocks = new int[dimension][dimension];
        hamming = 0;
        manhattan = 0;

        int count = 1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = blocks[i][j];

                // compute hamming and manhattan priority of this board
                if (blocks[i][j] != count && blocks[i][j] != 0) {
                    hamming++;

                    // compute the position of the block in goal board
                    int r = (blocks[i][j] - 1) / dimension;
                    int c = (blocks[i][j] - 1) % dimension;

                    manhattan = manhattan + Math.abs(i - r) + Math.abs(j - c);
                }

                count++;
            }
        }
    }

    // exchange blocks[i1][j1] and blocks[i2][j2], and then initialize the board
    private Board(int[][] blocks, int i1, int j1, int i2, int j2) {
        // initialize
        dimension = blocks.length;
        this.blocks = new int[dimension][dimension];
        hamming = 0;
        manhattan = 0;

        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                this.blocks[i][j] = blocks[i][j];

        // exchange blocks[i1][j1] and blocks[i2][j2]
        int tmp = this.blocks[i1][j1];
        this.blocks[i1][j1] = this.blocks[i2][j2];
        this.blocks[i2][j2] = tmp;

        int count = 1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                // compute hamming and manhattan priority of this board
                if (this.blocks[i][j] != count && this.blocks[i][j] != 0) {
                    hamming++;

                    // compute the position of the block in goal board
                    int r = (this.blocks[i][j]- 1) / dimension;
                    int c = (this.blocks[i][j]- 1) % dimension;

                    manhattan = manhattan + Math.abs(i - r) + Math.abs(j - c);
                }

                count++;
            }
        }
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        int count = 1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != count && blocks[i][j] != 0) {
                    return false;
                }
                count++;
            }
        }
        return true;
    }

    public Board twin() {
        Board twin;
        if (blocks[0][0] != 0 && blocks[0][1] != 0) {
            twin = new Board(blocks, 0, 0, 0, 1);
        } else if (blocks[0][0] != 0 && blocks[1][0] != 0) {
            twin = new Board(blocks, 0, 0, 1, 0);
        } else {
            twin = new Board(blocks, 0, 1, 1, 0);
        }
        return twin;
    }

    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        if (that.dimension != this.dimension)
            return false;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (that.blocks[i][j] != this.blocks[i][j])
                    return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<Board>();
        int blanki = 0, blankj = 0;

        // find the position of the blank
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    blanki = i;
                    blankj = j;
                }
            }
        }

        if (blanki > 0) {
            // if the blank doesn't at the left side
            neighbors.enqueue(new Board(blocks, blanki - 1, blankj, blanki,
                    blankj));
        }
        if (blanki < dimension - 1) {
            // if the blank doesn't at the right side
            neighbors.enqueue(new Board(blocks, blanki + 1, blankj, blanki,
                    blankj));
        }
        if (blankj > 0) {
            // if the blank doesn't at the top side
            neighbors.enqueue(new Board(blocks, blanki, blankj, blanki,
                    blankj - 1));
        }
        if (blankj < dimension - 1) {
            // if the blank doesn't at the bottom side
            neighbors.enqueue(new Board(blocks, blanki, blankj, blanki,
                    blankj + 1));
        }

        return neighbors;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
                // StdOut.println(blocks[i][j]);
            }
        Board initial = new Board(blocks);
        StdOut.println(initial);
        StdOut.println(initial.manhattan());
        StdOut.println("neighbors");
        for (Board neighbor : initial.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println(neighbor.manhattan());
        }
    }

}
