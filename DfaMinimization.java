import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class DfaMinimization {
    public static void run(Scanner scanner) {
        System.out.println("\nDFA State Reduction (complete DFA required)");
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

        Set<String> reachable = new HashSet<>();
        Queue<String> pending = new ArrayDeque<>();
        reachable.add(start);
        pending.add(start);
        while (!pending.isEmpty()) {
            String state = pending.remove();
            for (char symbol : alphabet.toCharArray()) {
                String destination = transitions.get(state).get(symbol);
                if (reachable.add(destination)) {
                    pending.add(destination);
                }
            }
        }

        List<Set<String>> groups = new ArrayList<>();
        Set<String> reachableFinals = new HashSet<>(reachable);
        reachableFinals.retainAll(finals);
        Set<String> reachableNonFinals = new HashSet<>(reachable);
        reachableNonFinals.removeAll(finals);
        if (!reachableFinals.isEmpty()) groups.add(reachableFinals);
        if (!reachableNonFinals.isEmpty()) groups.add(reachableNonFinals);

        boolean changed;
        do {
            Map<String, Set<String>> refined = new LinkedHashMap<>();
            Map<String, Integer> groupOf = groupLookup(groups);
            for (String state : reachable) {
                StringBuilder signature = new StringBuilder(finals.contains(state) ? "F" : "N");
                for (char symbol : alphabet.toCharArray()) {
                    signature.append(':').append(groupOf.get(transitions.get(state).get(symbol)));
                }
                refined.computeIfAbsent(signature.toString(), unused -> new HashSet<>()).add(state);
            }
            List<Set<String>> nextGroups = new ArrayList<>(refined.values());
            changed = nextGroups.size() != groups.size();
            groups = nextGroups;
        } while (changed);

        Map<String, Integer> groupOf = groupLookup(groups);
        System.out.println("\nMinimized DFA states:");
        for (int i = 0; i < groups.size(); i++) {
            Set<String> group = groups.get(i);
            String label = name(group);
            System.out.print(label + (group.contains(start) ? " (start)" : "")
                    + (group.stream().anyMatch(finals::contains) ? " (final)" : "") + ": ");
            String representative = group.iterator().next();
            for (char symbol : alphabet.toCharArray()) {
                String destination = transitions.get(representative).get(symbol);
                System.out.print(symbol + " -> " + name(groups.get(groupOf.get(destination))) + "  ");
            }
            System.out.println();
        }
    }

    private static Set<String> readSet(String line) {
        Set<String> result = new HashSet<>();
        if (!line.trim().equals("-")) {
            for (String item : line.trim().split("\\s+")) result.add(item);
        }
        return result;
    }

    private static Map<String, Integer> groupLookup(List<Set<String>> groups) {
        Map<String, Integer> result = new HashMap<>();
        for (int i = 0; i < groups.size(); i++) {
            for (String state : groups.get(i)) result.put(state, i);
        }
        return result;
    }

    private static String name(Set<String> states) {
        return "{" + String.join(",", states.stream().sorted().toList()) + "}";
    }
}