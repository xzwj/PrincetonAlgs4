import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // public Percolation(int n) // 创建一个 n*n 的网格，所以格子都是关闭的
    // public void open(int row, int col) // 如果格子 (row, col) 是关闭的，打开它
    // public boolean isOpen(int row, int col) // 判断格子 (row, col) 是否是打开的
    // public boolean isFull(int row, int col) // 判断格子 (row, col) 是否与网格顶部连通
    // public boolean percolates() // 判断系统是否是渗透的

    private WeightedQuickUnionUF grid;
    private int side;
    private boolean[][] isOpen; // 记录格子是否打开
    private boolean[][] isFull;
    private int numberOfOpenSites;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        numberOfOpenSites = 0;
        side = n;
        grid = new WeightedQuickUnionUF(n * n + 2); // 在顶部和底部各加一个虚拟节点
        isOpen = new boolean[n + 1][n + 1];
        isFull = new boolean[n + 1][n + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                isOpen[i][j] = false;
                isFull[i][j] = false;
            }
        }
    }

    public void open(int row, int col) {
        if (row <= 0 || row > side || col <= 0 || col > side)
            throw new IndexOutOfBoundsException();
        if (isOpen[row][col]) {
            return;
        }
        isOpen[row][col] = true;
        numberOfOpenSites++;

        if (row > 1 && isOpen[row - 1][col]) { // 如果格子不在第一排，则与上面的格子连通
            grid.union((row - 1) * side + col, (row - 2) * side + col);
        }
        if (row < side && isOpen[row + 1][col]) { // 如果格子不在最后一排，则与下面的格子连通
            grid.union((row - 1) * side + col, (row) * side + col);
        }
        if (col > 1 && isOpen[row][col - 1]) { // 如果格子不在第一列，则与左面的格子连通
            grid.union((row - 1) * side + col, (row - 1) * side + col - 1);
        }
        if (col < side && isOpen[row][col + 1]) { // 如果格子不在最后一列，则与右面的格子连通
            grid.union((row - 1) * side + col, (row - 1) * side + col + 1);
        }
        if (row == 1) { // 如果格子在第一排，则与顶部虚拟节点连通
            grid.union((row - 1) * side + col, 0);
        }
        if (row == side) { // 如果格子在最后一排，则与底部虚拟节点连通
            grid.union((row - 1) * side + col, side * side + 1);
        }
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > side || col <= 0 || col > side)
            throw new IndexOutOfBoundsException();
        return isOpen[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row <= 0 || row > side || col <= 0 || col > side)
            throw new IndexOutOfBoundsException();
        return grid.connected((row - 1) * side + col, 0);
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return grid.connected(side * side + 1, 0);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            if (!percolation.isOpen(row, col)) {
                percolation.open(row, col);
                StdOut.println(row + " " + col);
            }
        }
    }

}
