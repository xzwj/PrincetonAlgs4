import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
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

public class Solver {
    // public Solver(Board initial) // find a solution to the initial board
    // (using the A* algorithm)
    // public boolean isSolvable() // is the initial board solvable?
    // public int moves() // min number of moves to solve initial board; -1 if
    // unsolvable
    // public Iterable<Board> solution() // sequence of boards in a shortest
    // solution; null if unsolvable
    // public static void main(String[] args) // solve a slider puzzle (given
    // below)

    private boolean isSolvable;
    private int moves;
    private Stack<Board> solution;

    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException();

        isSolvable = false;
        moves = 0;
        // solution = new Stack<Board>();
        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        minPQ.insert(new SearchNode(initial, false));
        minPQ.insert(new SearchNode(initial.twin(), true));

        while (true) {
            SearchNode lastSearchNode = minPQ.delMin();

            if (lastSearchNode.board.isGoal()) {
                if (lastSearchNode.isFromTwin) {
                    moves = -1;
                    isSolvable = false;
                } else {
                    solution = new Stack<Board>();
                    for (SearchNode tmp = lastSearchNode; tmp != null; tmp = tmp.prew)
                        solution.push(tmp.board);

                    moves = lastSearchNode.moves;
                    isSolvable = true;
                }
                break;
            } else {
                for (Board neighbor : lastSearchNode.board.neighbors()) {
                    if (lastSearchNode.prew == null
                            || !neighbor.equals(lastSearchNode.prew.board)) {
                        SearchNode nextSearchNode = new SearchNode(neighbor,
                                lastSearchNode.isFromTwin);
                        nextSearchNode.prew = lastSearchNode;
                        nextSearchNode.moves = lastSearchNode.moves + 1;
                        minPQ.insert(nextSearchNode);
                    }
                }
            }
        }

    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        return solution;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode prew;
        private int moves;
        private boolean isFromTwin;

        public SearchNode(Board board, boolean isFromTwin) {
            this.board = board;
            prew = null;
            moves = 0;
            this.isFromTwin = isFromTwin;
        }

        @Override
        public int compareTo(SearchNode that) {
            // TODO Auto-generated method stub
            return board.manhattan() + moves - that.board.manhattan()
                    - that.moves;
        }

    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
            StdOut.println(solver.solution() == null);
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
