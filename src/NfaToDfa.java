import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class NfaToDfa {
    public static void run(Scanner scanner) {
        System.out.println("\nNFA to DFA (no epsilon transitions)");
        System.out.print("NFA states (space-separated): ");
        String[] states = scanner.nextLine().trim().split("\\s+");
        System.out.print("Alphabet (single characters, e.g. 01): ");
        String alphabet = scanner.nextLine().trim();
        System.out.print("Start state: ");
        String start = scanner.nextLine().trim();
        System.out.print("Final states (space-separated, or -): ");
        Set<String> finals = readSet(scanner.nextLine());

        Map<String, Map<Character, Set<String>>> transitions = new HashMap<>();
        System.out.println("For each state and symbol, enter destination states separated by spaces, or -.");
        for (String state : states) {
            Map<Character, Set<String>> row = new HashMap<>();
            for (char symbol : alphabet.toCharArray()) {
                System.out.print("From " + state + " on " + symbol + ": ");
                row.put(symbol, readSet(scanner.nextLine()));
            }
            transitions.put(state, row);
        }

        Set<String> initial = new TreeSet<>();
        initial.add(start);
        List<Set<String>> dfaStates = new ArrayList<>();
        Queue<Set<String>> pending = new ArrayDeque<>();
        Map<String, Map<Character, Set<String>>> dfaTransitions = new LinkedHashMap<>();
        Set<String> discovered = new HashSet<>();
        dfaStates.add(initial);
        pending.add(initial);
        discovered.add(key(initial));

        while (!pending.isEmpty()) {
            Set<String> subset = pending.remove();
            String key = key(subset);
            Map<Character, Set<String>> row = new LinkedHashMap<>();
            for (char symbol : alphabet.toCharArray()) {
                Set<String> destination = new TreeSet<>();
                for (String state : subset) {
                    destination.addAll(transitions.get(state).get(symbol));
                }
                String destinationKey = key(destination);
                if (discovered.add(destinationKey)) {
                    dfaStates.add(destination);
                    pending.add(destination);
                }
                row.put(symbol, destination);
            }
            dfaTransitions.put(key, row);
        }

        System.out.println("\nEquivalent DFA transitions:");
        for (Set<String> subset : dfaStates) {
            System.out.print(name(subset) + (subset.equals(initial) ? " (start)" : "")
                    + (containsFinal(subset, finals) ? " (final)" : "") + ": ");
            for (char symbol : alphabet.toCharArray()) {
                System.out.print(symbol + " -> " + name(dfaTransitions.get(key(subset)).get(symbol)) + "  ");
            }
            System.out.println();
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

    private static String key(Set<String> states) {
        return String.join(",", new TreeSet<>(states));
    }

    private static String name(Set<String> states) {
        return states.isEmpty() ? "{}" : "{" + String.join(",", new TreeSet<>(states)) + "}";
    }

    private static boolean containsFinal(Set<String> states, Set<String> finals) {
        for (String state : states) {
            if (finals.contains(state)) {
                return true;
            }
        }
        return false;
    }
}