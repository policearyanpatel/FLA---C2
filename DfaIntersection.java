import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class DfaIntersection {
    private record Pair(String first, String second) {}

    private record Dfa(String[] states, String alphabet, String start, Set<String> finals,
                       Map<String, Map<Character, String>> transitions) {}

    public static void run(Scanner scanner) {
        System.out.println("\nIntersection of Two DFAs");
        Dfa first = readDfa(scanner, "first");
        Dfa second = readDfa(scanner, "second");
        if (!first.alphabet().equals(second.alphabet())) {
            System.out.println("The two DFAs must use the same alphabet.");
            return;
        }

        Pair start = new Pair(first.start(), second.start());
        Set<Pair> visited = new HashSet<>();
        Queue<Pair> pending = new ArrayDeque<>();
        Map<Pair, Map<Character, Pair>> productTransitions = new HashMap<>();
        visited.add(start);
        pending.add(start);

        while (!pending.isEmpty()) {
            Pair current = pending.remove();
            Map<Character, Pair> row = new HashMap<>();
            for (char symbol : first.alphabet().toCharArray()) {
                Pair destination = new Pair(
                        first.transitions().get(current.first()).get(symbol),
                        second.transitions().get(current.second()).get(symbol));
                row.put(symbol, destination);
                if (visited.add(destination)) pending.add(destination);
            }
            productTransitions.put(current, row);
        }

        System.out.println("\nProduct DFA transitions:");
        for (Pair state : visited) {
            boolean isFinal = first.finals().contains(state.first())
                    && second.finals().contains(state.second());
            System.out.print(name(state) + (state.equals(start) ? " (start)" : "")
                    + (isFinal ? " (final)" : "") + ": ");
            for (char symbol : first.alphabet().toCharArray()) {
                System.out.print(symbol + " -> " + name(productTransitions.get(state).get(symbol)) + "  ");
            }
            System.out.println();
        }
    }

    private static Dfa readDfa(Scanner scanner, String label) {
        System.out.println("Enter the " + label + " DFA:");
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
        return new Dfa(states, alphabet, start, finals, transitions);
    }

    private static Set<String> readSet(String line) {
        Set<String> result = new HashSet<>();
        if (!line.trim().equals("-")) {
            for (String item : line.trim().split("\\s+")) result.add(item);
        }
        return result;
    }

    private static String name(Pair pair) {
        return "(" + pair.first() + "," + pair.second() + ")";
    }
}