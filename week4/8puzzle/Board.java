import java.util.ArrayList;

public class Board {
    private int[][] blocks;

    public Board(int[][] blocks) {
        this.blocks = blocks.clone();

        for (int i = 0; i < this.blocks.length; i++) {
            this.blocks[i] = this.blocks[i].clone();
        }
    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming() {
        int wrongBlocks = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }

                int number = getNumberFromCoords(i, j);
                if (number != blocks[i][j]) {
                    wrongBlocks++;
                }
            }
        }

        return wrongBlocks;
    }

    public int manhattan() {
        int dinstances = 0;

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }

                int number = getNumberFromCoords(i, j);
                if (number == blocks[i][j]) {
                    continue;
                }

                int goalI = blocks[i][j] / dimension();
                int goalJ = blocks[i][j] % dimension();

                if (goalJ == 0) {
                    goalI -= 1;
                    goalJ = dimension() - 1;
                } else {
                    goalJ -= 1;
                }

                dinstances += Math.abs(i - goalI) + Math.abs(j - goalJ);
            }
        }

        return dinstances;
    }

    public boolean isGoal() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                // last block should be empty
                if ((i == dimension() - 1) && (j == dimension() - 1)) {
                    return blocks[i][j] == 0;
                }

                int number = getNumberFromCoords(i, j);
                if (number != blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Board twin() {
        int blankI = 0;
        int blankJ = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    blankI = i;
                    blankJ = j;
                    break;
                }
            }
        }

        int oneI = 0;
        int oneJ = 0;
        int twoI = 1;
        int twoJ = 0;
        if ((oneI == blankI && oneJ == blankJ) || (twoI == blankI && twoJ == blankJ)) {
            oneJ += 1;
            twoJ += 1;
        }

        Board board = new Board(blocks);
        board.swapBlocks(oneI, oneJ, twoI, twoJ);

        return board;
    }

    public boolean equals(Object board) {
        if (this == board) {
            return true;
        }
        if (board == null) {
            return false;
        }
        if (getClass() != board.getClass()) {
            return false;
        }

        Board that = (Board) board;

        if (this.dimension() != that.dimension()) {
            return false;
        }

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        int blankI = 0;
        int blankJ = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    blankI = i;
                    blankJ = j;
                    break;
                }
            }
        }

        ArrayList<Board> boards = new ArrayList<Board>();

        if (blankI > 0) {
            Board board = new Board(blocks);
            board.swapBlocks(blankI, blankJ, blankI - 1, blankJ);
            boards.add(board);
        }

        if (blankI < dimension() - 1) {
            Board board = new Board(blocks);
            board.swapBlocks(blankI, blankJ, blankI + 1, blankJ);
            boards.add(board);
        }

        if (blankJ > 0) {
            Board board = new Board(blocks);
            board.swapBlocks(blankI, blankJ, blankI, blankJ - 1);
            boards.add(board);
        }

        if (blankJ < dimension() - 1) {
            Board board = new Board(blocks);
            board.swapBlocks(blankI, blankJ, blankI, blankJ + 1);
            boards.add(board);
        }

        return boards;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(dimension()).append('\n');
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension() - 1; j++) {
                sb.append(blocks[i][j]).append(' ');
            }
            sb.append(blocks[i][dimension() - 1]).append('\n');
        }

        return sb.toString();
    }

    private int getNumberFromCoords(int i, int j) {
        return i * dimension() + (j + 1);
    }

    private void swapBlocks(int oneI, int oneJ, int twoI, int twoJ) {
        int t = blocks[oneI][oneJ];
        blocks[oneI][oneJ] = blocks[twoI][twoJ];
        blocks[twoI][twoJ] = t;
    }

    public static void main(String[] args) {
        // empty
    }
}
