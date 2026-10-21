import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DfaSimulation {
    public static void run(Scanner scanner) {
        System.out.println("\nDFA Simulation");
        System.out.print("States (space-separated): ");
        String[] states = scanner.nextLine().trim().split("\\s+");
        System.out.print("Alphabet (single characters, e.g. 01): ");
        String alphabet = scanner.nextLine().trim();
        System.out.print("Start state: ");
        String start = scanner.nextLine().trim();
        System.out.print("Final states (space-separated, or -): ");
        Set<String> finals = readSet(scanner.nextLine());

        Map<String, Map<Character, String>> transitions = new HashMap<>();
        System.out.println("For each state, enter destinations in alphabet order.");
        for (String state : states) {
            System.out.print(state + ": ");
            String[] destinations = scanner.nextLine().trim().split("\\s+");
            Map<Character, String> row = new HashMap<>();
            for (int i = 0; i < alphabet.length(); i++) {
                row.put(alphabet.charAt(i), destinations[i]);
            }
            transitions.put(state, row);
        }

        System.out.print("Input string (blank for empty): ");
        String input = scanner.nextLine();
        String current = start;
        boolean valid = true;
        for (char symbol : input.toCharArray()) {
            if (!alphabet.contains(String.valueOf(symbol))
                    || !transitions.containsKey(current)
                    || !transitions.get(current).containsKey(symbol)) {
                valid = false;
                break;
            }
            current = transitions.get(current).get(symbol);
        }

        if (valid && finals.contains(current)) {
            System.out.println("Accepted");
        } else {
            System.out.println("Rejected");
        }
    }

    private static Set<String> readSet(String line) {
        Set<String> result = new HashSet<>();
        if (!line.trim().equals("-")) {
            for (String item : line.trim().split("\\s+")) {
                result.add(item);
            }
        }
        return result;
    }
}