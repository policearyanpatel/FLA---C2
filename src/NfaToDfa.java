import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class NfaToDfa {
    public static void run(Scanner sc) {
        System.out.print("Enter number of NFA states: ");
        int n = sc.nextInt();

        int[][][] nfa = new int[n][2][];
        for (int i = 0; i < n; i++) {
            for (int s = 0; s < 2; s++) {
                System.out.print("Count of transitions from q" + i + " on " + s + ": ");
                int k = sc.nextInt();
                nfa[i][s] = new int[k];
                if (k > 0) {
                    System.out.print("Enter next states: ");
                    for (int j = 0; j < k; j++) {
                        nfa[i][s][j] = sc.nextInt();
                    }
                }
            }
        }

        System.out.print("Enter number of final states: ");
        int f = sc.nextInt();
        boolean[] nfaFinal = new boolean[n];
        System.out.print("Enter final states: ");
        for (int i = 0; i < f; i++) {
            nfaFinal[sc.nextInt()] = true;
        }

        List<Set<Integer>> dfa = new ArrayList<>();
        List<int[]> table = new ArrayList<>();
        Set<Integer> start = new HashSet<>();

        start.add(0);
        dfa.add(start);
        for (int i = 0; i < dfa.size(); i++) {
            Set<Integer> current = dfa.get(i);
            int[] row = new int[2];
            for (int s = 0; s < 2; s++) {
                Set<Integer> next = new HashSet<>();
                for (int q : current) {
                    for (int d : nfa[q][s]) {
                        next.add(d);
                    }
                }
                int idx = dfa.indexOf(next);
                if (idx == -1) {
                    dfa.add(new HashSet<>(next));
                    idx = dfa.size() - 1;
                }
                row[s] = idx;
            }
            table.add(row);
        }

        System.out.println("DFA transitions (0, 1):");
        for (int i = 0; i < dfa.size(); i++) {
            boolean isFinal = false;
            for (int q : dfa.get(i)) {
                if (nfaFinal[q]) isFinal = true;
            }
            System.out.println("D" + i + " " + dfa.get(i)
                    + (isFinal ? " (final)" : "") + " -> " + Arrays.toString(table.get(i)));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        run(sc);
    }
}
