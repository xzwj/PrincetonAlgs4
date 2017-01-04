import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private double[] threshold;
    // public PercolationStats(int n, int trials) // 执行 trials 次独立实验，网格为 n*n
    // public double mean() // 计算渗透阈值的平均数
    // public double stddev() // 计算渗透阈值的标准差
    // public double confidenceLo() // 95% 置信区间的左端
    // public double confidenceHi() // 95% 置信区间的右端
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        threshold = new double[trials]; // 存放每次实验得到的渗透阈值
        for (int i = 0; i < trials; i++) {
            threshold[i] = 0;
        }
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(row, col)) {
                    threshold[i]++;
                    perc.open(row, col);
                }
            }
            threshold[i] = threshold[i] / (n * n);
            // StdOut.println("threshold" + i + " = " + threshold[i]);
        }
    }
    public double mean() {
        return StdStats.mean(threshold);
    }
    public double stddev() {
        return StdStats.stddev(threshold);
    }
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();
        double temp = 1.96 * stddev
                / java.lang.StrictMath.pow(threshold.length, 1.0 / 2);
        return mean - temp;
    }
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();
        double temp = 1.96 * stddev
                / java.lang.StrictMath.pow(threshold.length, 1.0 / 2);
        return mean + temp;
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percStates = new PercolationStats(n, trials);
        StdOut.println("mean = " + percStates.mean());
        StdOut.println("stddev = " + percStates.stddev());
        StdOut.println("95% confidence interval = " + percStates.confidenceLo()
                + ", " + percStates.confidenceHi());
    }
}