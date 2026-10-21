import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PdaSimulation {
    private record Key(String state, char input, char stackTop) {}
    private record Move(String nextState, String push) {}

    public static void run(Scanner scanner) {
        System.out.println("\nPDA Simulation (deterministic, no epsilon moves)");
        System.out.print("Start state: ");
        String start = scanner.nextLine().trim();
        System.out.print("Initial stack symbol: ");
        char initialStack = scanner.nextLine().trim().charAt(0);
        System.out.print("Final states (space-separated, or -): ");
        Set<String> finals = readSet(scanner.nextLine());

        System.out.print("Number of transitions: ");
        int count = Integer.parseInt(scanner.nextLine().trim());
        Map<Key, Move> transitions = new HashMap<>();
        System.out.println("Enter each transition: state input stackTop nextState pushString");
        System.out.println("Use - as pushString for no push; leftmost pushed symbol becomes the new top.");
        for (int i = 0; i < count; i++) {
            String[] parts = scanner.nextLine().trim().split("\\s+");
            String push = parts[4].equals("-") ? "" : parts[4];
            transitions.put(new Key(parts[0], parts[1].charAt(0), parts[2].charAt(0)),
                    new Move(parts[3], push));
        }

        System.out.print("Input string (blank for empty): ");
        String input = scanner.nextLine();
        ArrayDeque<Character> stack = new ArrayDeque<>();
        stack.push(initialStack);
        String current = start;
        boolean accepted = true;

        for (char symbol : input.toCharArray()) {
            if (stack.isEmpty()) {
                accepted = false;
                break;
            }
            Move move = transitions.get(new Key(current, symbol, stack.peek()));
            if (move == null) {
                accepted = false;
                break;
            }
            stack.pop();
            for (int i = move.push().length() - 1; i >= 0; i--) {
                stack.push(move.push().charAt(i));
            }
            current = move.nextState();
        }

        if (accepted && finals.contains(current)) {
            System.out.println("Accepted");
        } else {
            System.out.println("Rejected");
        }
    }

    private static Set<String> readSet(String line) {
        Set<String> result = new HashSet<>();
        if (!line.trim().equals("-")) {
            for (String item : line.trim().split("\\s+")) result.add(item);
        }
        return result;
    }
}