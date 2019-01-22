import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private final Stack<Board> solution;
    private int moves;

    private class Key implements Comparable<Key> {
        private final Board board;
        private final int moves;
        private final Key predecessor;
        private final int manhattan;

        public Key(Board initial, int moves, Key predecessor) {
            this.board = initial;
            this.moves = moves;
            this.predecessor = predecessor;

            manhattan = initial.manhattan() + moves;
        }

        @Override
        public int compareTo(Key that) {
            return Integer.compare(this.manhattan, that.manhattan);
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        solution = new Stack<>();
        MinPQ<Key> pq = new MinPQ<Key>();
        pq.insert(new Key(initial, 0, null));

        MinPQ<Key> twinPQ = new MinPQ<Key>();
        twinPQ.insert(new Key(initial.twin(), 0, null));

        while (!pq.isEmpty()) {
            Key key = pq.delMin();
            Key twinKey = twinPQ.delMin();

            if (twinKey.board.isGoal()) {
                break;
            }

            for (Board board : twinKey.board.neighbors()) {
                if (twinKey.predecessor != null && board.equals(twinKey.predecessor.board)) {
                    continue;
                }

                twinPQ.insert(new Key(board, twinKey.moves + 1, twinKey));
            }

            if (key.board.isGoal()) {
                solution.push(key.board);
                moves = key.moves;

                Key predecessor = key.predecessor;
                while (predecessor != null) {
                    solution.push(predecessor.board);
                    predecessor = predecessor.predecessor;
                }

                break;
            }

            for (Board board : key.board.neighbors()) {
                if (key.predecessor != null && board.equals(key.predecessor.board)) {
                    continue;
                }

                pq.insert(new Key(board, key.moves + 1, key));
            }
        }
    }

    public boolean isSolvable() {
        return !solution.isEmpty();
    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return moves;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return solution;
    }

    public static void main(String[] args) {
        // empty
    }
}
