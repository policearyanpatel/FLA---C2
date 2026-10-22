import java.util.Scanner;

public class DfaIntersection {
    public static void run(Scanner sc) {
        System.out.print("Enter number of states for DFA 1: ");
        int n1 = sc.nextInt();
        int[][] first = new int[n1][2];
        System.out.println("Enter transitions for 0 and 1 for DFA 1 (state numbers 0 to " + (n1 - 1) + "):");
        for (int i = 0; i < n1; i++) {
            System.out.print("q" + i + " (on 0 and 1): ");
            int next0 = sc.nextInt();
            int next1 = sc.nextInt();
            if (next0 < 0 || next0 >= n1 || next1 < 0 || next1 >= n1) {
                System.out.println("Invalid state index in transition. Must be between 0 and " + (n1 - 1) + ".");
                return;
            }
            first[i][0] = next0;
            first[i][1] = next1;
        }

        System.out.print("Enter number of final states for DFA 1: ");
        int f1 = sc.nextInt();
        if (f1 < 0 || f1 > n1) {
            System.out.println("Invalid number of final states.");
            return;
        }
        boolean[] firstFinal = new boolean[n1];
        System.out.print("Enter final state numbers for DFA 1 (0 to " + (n1 - 1) + "): ");
        for (int i = 0; i < f1; i++) {
            int state = sc.nextInt();
            if (state < 0 || state >= n1) {
                System.out.println("Invalid final state index: " + state + ". Must be between 0 and " + (n1 - 1) + ".");
                return;
            }
            firstFinal[state] = true;
        }

        System.out.print("Enter number of states for DFA 2: ");
        int n2 = sc.nextInt();
        int[][] second = new int[n2][2];
        System.out.println("Enter transitions for 0 and 1 for DFA 2 (state numbers 0 to " + (n2 - 1) + "):");
        for (int i = 0; i < n2; i++) {
            System.out.print("q" + i + " (on 0 and 1): ");
            int next0 = sc.nextInt();
            int next1 = sc.nextInt();
            if (next0 < 0 || next0 >= n2 || next1 < 0 || next1 >= n2) {
                System.out.println("Invalid state index in transition. Must be between 0 and " + (n2 - 1) + ".");
                return;
            }
            second[i][0] = next0;
            second[i][1] = next1;
        }

        System.out.print("Enter number of final states for DFA 2: ");
        int f2 = sc.nextInt();
        if (f2 < 0 || f2 > n2) {
            System.out.println("Invalid number of final states.");
            return;
        }
        boolean[] secondFinal = new boolean[n2];
        System.out.print("Enter final state numbers for DFA 2 (0 to " + (n2 - 1) + "): ");
        for (int i = 0; i < f2; i++) {
            int state = sc.nextInt();
            if (state < 0 || state >= n2) {
                System.out.println("Invalid final state index: " + state + ". Must be between 0 and " + (n2 - 1) + ".");
                return;
            }
            secondFinal[state] = true;
        }

        System.out.println("Product DFA transitions (0, 1):");
        for (int a = 0; a < n1; a++) {
            for (int b = 0; b < n2; b++) {
                int state = a * n2 + b;
                int onZero = first[a][0] * n2 + second[b][0];
                int onOne = first[a][1] * n2 + second[b][1];
                boolean isFinal = firstFinal[a] && secondFinal[b];
                System.out.println("P" + state + " = (" + a + "," + b + ")"
                        + (isFinal ? " (final)" : "")
                        + " -> [" + onZero + ", " + onOne + "]");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        run(sc);
    }
}
