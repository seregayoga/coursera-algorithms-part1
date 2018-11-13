/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final int sitesNumber;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufFullness;
    private final boolean[] sites;
    private int openSites = 0;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        sitesNumber = n * n;
        uf = new WeightedQuickUnionUF(sitesNumber + 2);
        ufFullness = new WeightedQuickUnionUF(sitesNumber + 1);
        sites = new boolean[sitesNumber + 1];
    }

    public static void main(String[] args) {
        // empty body
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        openSites++;

        int number = rowAndColToNumber(row, col);

        if (row == 1) {
            uf.union(0, number);
            ufFullness.union(0, number);
        }
        else if (this.isOpen(row - 1, col)) {
            uf.union(rowAndColToNumber(row - 1, col), number);
            ufFullness.union(rowAndColToNumber(row - 1, col), number);
        }

        if (row == n) {
            uf.union(sitesNumber + 1, number);
            // we don't need to call union for fullness here
        }
        else if (this.isOpen(row + 1, col)) {
            uf.union(rowAndColToNumber(row + 1, col), number);
            ufFullness.union(rowAndColToNumber(row + 1, col), number);
        }

        if (col > 1 && this.isOpen(row, col - 1)) {
            uf.union(rowAndColToNumber(row, col - 1), number);
            ufFullness.union(rowAndColToNumber(row, col - 1), number);
        }
        if (col < n && this.isOpen(row, col + 1)) {
            uf.union(rowAndColToNumber(row, col + 1), number);
            ufFullness.union(rowAndColToNumber(row, col + 1), number);
        }

        sites[rowAndColToNumber(row, col)] = true;
    }

    public boolean isOpen(int row, int col) {
        return sites[rowAndColToNumber(row, col)];
    }

    public boolean isFull(int row, int col) {
        return ufFullness.connected(0, rowAndColToNumber(row, col));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return uf.connected(0, sitesNumber + 1);
    }

    private int rowAndColToNumber(int row, int col) {
        if (row <= 0 || row > n) {
            throw new IllegalArgumentException("row index out of bounds");
        }
        if (col <= 0 || col > n) {
            throw new IllegalArgumentException("col index col out of bounds");
        }

        return (row - 1) * n + col;
    }
}
