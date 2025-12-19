import java.util.Scanner;
import java.util.List;

public class Repl {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SpellChecker spellChecker = new SpellChecker(WordBank.commonWords);
        System.out.println("--- Edit Distance Spell Checker! ---");
        System.out.printf("Dictionary Size: %d words.\n", spellChecker.getWordBank().length);
        System.out.printf("Max Edit Distance %d.\n", spellChecker.getMaxDistance());
        System.out.printf("Match Cost %d.\n", spellChecker.getMatchCost());
        System.out.printf("Miss Cost %d.\n", spellChecker.getMissCost());
        System.out.printf("Gap Cost %d.\n", spellChecker.getGapCost());
        System.out.printf("Dynamic Threshold %b.\n", spellChecker.getdynamicThreshold());
        System.out.println("Type 'quit' to stop the program.");
        System.out.println("...");

        while (true) {
            System.out.print("\nEnter word to check: ");
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("quit")) {
                    System.out.println("\nExiting Spell Checker. Goodbye!");
                    break;
                }

                if (input.isEmpty())
                    continue;

                System.out.printf("Checking: '%s'\n", input);

                long startTime = System.nanoTime();
                List<SpellChecker.Suggestion> results = spellChecker.getSuggestions(input);
                long endTime = System.nanoTime();

                if (results.isEmpty()) {
                    System.out.printf("-> No close match found for '%s'.\n", input);
                } else {
                    SpellChecker.Suggestion bestMatch = results.get(0);

                    if (bestMatch.distance == 0) {
                        System.out.printf("> '%s' is spelled correctly.\n", bestMatch.word);
                    } else {
                        System.out.printf("> Did you mean (Best match: Distance %d):\n", bestMatch.distance);
                        for (int i = 0; i < results.size(); i++) {
                            SpellChecker.Suggestion s = results.get(i);
                            System.out.printf("   [%d] %s (Distance: %d)\n", (i + 1), s.word, s.distance);
                        }
                    }
                }

                double deltaMs = (endTime - startTime) / 1_000_000.0;
                System.out.printf("\nTime taken for search: %.2f ms\n", deltaMs);
            } else {
                break;
            }
        }
        scanner.close();
    }
}
