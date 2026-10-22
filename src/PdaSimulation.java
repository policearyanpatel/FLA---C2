import java.util.Scanner;

public class PdaSimulation {
    public static void run(Scanner scanner) {
        System.out.print("Enter a string of a's followed by b's: ");
        String input = scanner.nextLine();
        char[] stack = new char[input.length() + 1];
        int top = 0;
        stack[top] = '$';
        boolean readingB = false;
        boolean valid = true;

        for (int i = 0; i < input.length(); i++) {
            char symbol = input.charAt(i);
            if (symbol == 'a' && !readingB) {
                stack[++top] = 'A';
            } else if (symbol == 'b') {
                readingB = true;
                if (top == 0 || stack[top] != 'A') {
                    valid = false;
                    break;
                }
                top--;
            } else {
                valid = false;
                break;
            }
        }

        if (valid && top == 0 && readingB) {
            System.out.println("Accepted");
        } else {
            System.out.println("Declined");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        run(scanner);
    }
}
