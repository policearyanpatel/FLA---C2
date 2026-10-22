import java.util.Scanner;

public class DfaIntersection {
    public static void run(Scanner sc) {
        System.out.print("Enter number of states for DFA 1: ");
        int n1 = sc.nextInt();
        int[][] first = new int[n1][2];
        System.out.println("Enter transitions for 0 and 1 for DFA 1:");
        for (int i = 0; i < n1; i++) {
            System.out.print("q" + i + " (on 0 and 1): ");
            first[i][0] = sc.nextInt();
            first[i][1] = sc.nextInt();
        }
        System.out.print("Enter number of final states for DFA 1: ");
        int f1 = sc.nextInt();
        boolean[] firstFinal = new boolean[n1];
        System.out.print("Enter final states for DFA 1: ");
        for (int i = 0; i < f1; i++) {
            firstFinal[sc.nextInt()] = true;
        }

        System.out.print("Enter number of states for DFA 2: ");
        int n2 = sc.nextInt();
        int[][] second = new int[n2][2];
        System.out.println("Enter transitions for 0 and 1 for DFA 2:");
        for (int i = 0; i < n2; i++) {
            System.out.print("q" + i + " (on 0 and 1): ");
            second[i][0] = sc.nextInt();
            second[i][1] = sc.nextInt();
        }
        System.out.print("Enter number of final states for DFA 2: ");
        int f2 = sc.nextInt();
        boolean[] secondFinal = new boolean[n2];
        System.out.print("Enter final states for DFA 2: ");
        for (int i = 0; i < f2; i++) {
            secondFinal[sc.nextInt()] = true;
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
