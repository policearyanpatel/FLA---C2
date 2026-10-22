import java.util.Scanner;

public class DfaSimulation {
    public static void run(Scanner sc) {
        System.out.print("Enter number of states: ");
        int n = sc.nextInt();

        int[][] transition = new int[n][2];
        System.out.println("Enter transitions for 0 and 1 for each state:");
        for (int i = 0; i < n; i++) {
            System.out.print("State q" + i + " (on 0 and 1): ");
            transition[i][0] = sc.nextInt();
            transition[i][1] = sc.nextInt();
        }

        System.out.print("Enter number of final states: ");
        int f = sc.nextInt();
        boolean[] finalState = new boolean[n];
        System.out.print("Enter final states: ");
        for (int i = 0; i < f; i++) {
            finalState[sc.nextInt()] = true;
        }

        sc.nextLine(); // consume trailing newline
        System.out.print("Enter a binary string: ");
        String input = sc.nextLine();

        int currentState = 0;
        for (int i = 0; i < input.length(); i++) {
            char symbol = input.charAt(i);
            if (symbol != '0' && symbol != '1') {
                System.out.println("Invalid string");
                return;
            }
            int column = symbol - '0';
            currentState = transition[currentState][column];
        }

        if (finalState[currentState]) {
            System.out.println("Accepted");
        } else {
            System.out.println("Declined");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        run(sc);
    }
}
