import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Automata Programs ===");
            System.out.println("1. DFA Simulation");
            System.out.println("2. NFA to DFA Conversion");
            System.out.println("3. DFA State Reduction");
            System.out.println("4. Intersection of Two DFAs");
            System.out.println("5. PDA Simulation");
            System.out.println("0. Exit");
            System.out.print("Choose a task: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> DfaSimulation.run(scanner);
                case "2" -> NfaToDfa.run(scanner);
                case "3" -> DfaMinimization.run(scanner);
                case "4" -> DfaIntersection.run(scanner);
                case "5" -> PdaSimulation.run(scanner);
                case "0" -> {
                    System.out.println("Goodbye.");
                    return;
                }
                default -> System.out.println("Please choose a number from 0 to 5.");
            }
        }
    }
}
