import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }

        int k = Integer.parseInt(args[0]);
        int i = 0;
        for (String item : q) {
            if (i == k) {
                break;
            }
            i++;

            StdOut.println(item);
        }
    }
}
