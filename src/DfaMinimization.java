import java.util.Scanner;

public class DfaMinimization {
    public static void run(Scanner sc) {
        System.out.print("Enter number of states: ");
        int n = sc.nextInt();

        int[][] t = new int[n][2];
        System.out.println("Enter transitions for 0 and 1 for each state:");
        for (int i = 0; i < n; i++) {
            System.out.print("State q" + i + " (on 0 and 1): ");
            t[i][0] = sc.nextInt();
            t[i][1] = sc.nextInt();
        }

        System.out.print("Enter number of final states: ");
        int f = sc.nextInt();
        boolean[] fin = new boolean[n];
        System.out.print("Enter final states: ");
        for (int i = 0; i < f; i++) {
            fin[sc.nextInt()] = true;
        }

        boolean[][] d = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (fin[i] != fin[j]) {
                    d[i][j] = true;
                }
            }
        }

        boolean change;
        do {
            change = false;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (d[i][j]) continue;
                    for (int s = 0; s < 2; s++) {
                        int a = t[i][s];
                        int b = t[j][s];
                        if (a != b && d[Math.min(a, b)][Math.max(a, b)]) {
                            d[i][j] = true;
                            change = true;
                            break;
                        }
                    }
                }
            }
        } while (change);

        int groups = n;
        System.out.println("Equivalent pairs:");
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (!d[i][j]) {
                    System.out.println("q" + i + " = q" + j);
                    groups--;
                    break;
                }
            }
        }
        System.out.println("Minimized states: " + groups + " from " + n + " states");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        run(sc);
    }
}
