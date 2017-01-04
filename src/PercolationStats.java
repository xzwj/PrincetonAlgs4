import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private double[] threshold;
    // public PercolationStats(int n, int trials) // ִ�� trials �ζ���ʵ�飬����Ϊ n*n
    // public double mean() // ������͸��ֵ��ƽ����
    // public double stddev() // ������͸��ֵ�ı�׼��
    // public double confidenceLo() // 95% ������������
    // public double confidenceHi() // 95% ����������Ҷ�
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        threshold = new double[trials]; // ���ÿ��ʵ��õ�����͸��ֵ
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