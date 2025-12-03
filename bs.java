import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BulgarianSolitaire {

    // Check triangularity of pile
    private static boolean isTriangular(int n) {
        double k = (-1 + Math.sqrt(1 + 8 * n)) / 2;
        return k == (int) k;
    }

    // Perform round
    private static List<Integer> doRound(List<Integer> piles) {
        assert piles.stream().allMatch(p -> p > 0) : "Something went wrong, we have non-positive numbers.";

        int originalSize = piles.size();
        List<Integer> newPiles = new ArrayList<>();

        // subtract 1 from each pile, drop zeros
        for (int p : piles) {
            if (p - 1 > 0) {
                newPiles.add(p - 1);
            }
        }

        // add new pile
        newPiles.add(originalSize);

        Collections.sort(newPiles);
        return newPiles;
    }

    public static void run(List<Integer> piles) {
        Collections.sort(piles);

        int total = piles.stream().mapToInt(Integer::intValue).sum();
        assert isTriangular(total) : "Something went wrong; non-triangularity detected.";

        System.out.println("Start: " + piles);

        // Target for triangular numbers
        int c = (int)((-1 + Math.sqrt(1 + 8 * total)) / 2);

        List<Integer> target = new ArrayList<>();
        for (int i = 1; i <= c; i++) target.add(i);

        int rounds = 0;
        while (!piles.equals(target)) {
            rounds++;
            piles = doRound(piles);
            System.out.println("Round " + rounds + ": " + piles);
        }

        System.out.println("\nCompleted!");
        System.out.println("Total rounds: " + rounds);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input
        System.out.println("Enter starting piles separated by commas (e.g., 1,3,5,6,7,10,13):");
        String input = sc.nextLine();
        sc.close();

        // Convert input to array of ints
        String[] parts = input.split(",");
        List<Integer> piles = new ArrayList<>();

        for (String s : parts) {
            try {
                int value = Integer.parseInt(s.trim());

                // If negative number: Stop
                if (value < 0) {
                    System.out.println("Error: Negative pile sizes are not allowed (" + value + ")");
                    return;
                }

                // Don't let inputted 0s break it
                if (value == 0) {
                    continue; 
                }

                piles.add(value);

            } catch (NumberFormatException e) {
                System.out.println("Invalid number: " + s);
                return;
            }
        }

        // Handle case if all 0
        if (piles.isEmpty()) {
            System.out.println("All piles were zero. Nothing to run.");
            return;
        }

        // Stop on non-triangularity
        int total = piles.stream().mapToInt(Integer::intValue).sum();
        if (!isTriangular(total)) {
            System.out.println("Error: " + total + " is not a triangular number. Non-triangular numbers are too scary for this program. We hope you understand. Thank you.");
            return;
        }

        run(piles);
    }
}
