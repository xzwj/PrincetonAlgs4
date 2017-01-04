import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // public Percolation(int n) // ����һ�� n*n ���������Ը��Ӷ��ǹرյ�
    // public void open(int row, int col) // ������� (row, col) �ǹرյģ�����
    // public boolean isOpen(int row, int col) // �жϸ��� (row, col) �Ƿ��Ǵ򿪵�
    // public boolean isFull(int row, int col) // �жϸ��� (row, col) �Ƿ������񶥲���ͨ
    // public boolean percolates() // �ж�ϵͳ�Ƿ�����͸��

    private WeightedQuickUnionUF grid;
    private int side;
    private boolean[][] isOpen; // ��¼�����Ƿ��
    private boolean[][] isFull;
    private int numberOfOpenSites;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        numberOfOpenSites = 0;
        side = n;
        grid = new WeightedQuickUnionUF(n * n + 2); // �ڶ����͵ײ�����һ������ڵ�
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

        if (row > 1 && isOpen[row - 1][col]) { // ������Ӳ��ڵ�һ�ţ���������ĸ�����ͨ
            grid.union((row - 1) * side + col, (row - 2) * side + col);
        }
        if (row < side && isOpen[row + 1][col]) { // ������Ӳ������һ�ţ���������ĸ�����ͨ
            grid.union((row - 1) * side + col, (row) * side + col);
        }
        if (col > 1 && isOpen[row][col - 1]) { // ������Ӳ��ڵ�һ�У���������ĸ�����ͨ
            grid.union((row - 1) * side + col, (row - 1) * side + col - 1);
        }
        if (col < side && isOpen[row][col + 1]) { // ������Ӳ������һ�У���������ĸ�����ͨ
            grid.union((row - 1) * side + col, (row - 1) * side + col + 1);
        }
        if (row == 1) { // ��������ڵ�һ�ţ����붥������ڵ���ͨ
            grid.union((row - 1) * side + col, 0);
        }
        if (row == side) { // ������������һ�ţ�����ײ�����ڵ���ͨ
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
