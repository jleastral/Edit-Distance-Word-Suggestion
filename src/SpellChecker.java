import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SpellChecker {
    private Set<String> wordBank = new HashSet<>();
    private int maxDistance = 2;
    private int matchCost = 0;
    private int missCost = 1;
    private int gapCost = 1;
    private boolean dynamicThreshold = true;

    public SpellChecker(String[] words) {
        for (String word : words) {
            wordBank.add(word.toLowerCase());
        }
    }

    public SpellChecker(String[] words, int max) {
        for (String word : words) {
            wordBank.add(word.toLowerCase());
        }
        maxDistance = max;
    }

    public SpellChecker(String[] words, int max, int match, int miss, int gap) {
        for (String word : words) {
            wordBank.add(word.toLowerCase());
        }
        maxDistance = max;
        matchCost = match;
        missCost = miss;
        gapCost = gap;
    }

    public String[] getWordBank() {
        return this.wordBank.toArray(new String[0]);
    }

    public int getMaxDistance() {
        return this.maxDistance;
    }

    public int getMatchCost() {
        return this.matchCost;
    }

    public int getMissCost() {
        return this.missCost;
    }

    public int getGapCost() {
        return this.gapCost;
    }

    public boolean getdynamicThreshold() {
        return this.dynamicThreshold;
    }

    public void setWordBank(Set<String> wordBank) {
        this.wordBank = wordBank;
    }

    public void setMaxDistance(int maxDistance) {
        if (maxDistance >= 0) {
            this.maxDistance = maxDistance;
        } else {
            System.err.println("Warning: Maximum distance must be non-negative.");
        }
    }

    public void setMatchCost(int matchCost) {
        if (matchCost >= 0) {
            this.matchCost = matchCost;
        } else {
            System.err.println("Warning: Cost must be non-negative.");
        }
    }

    public void setMissCost(int missCost) {
        if (missCost >= 0) {
            this.missCost = missCost;
        } else {
            System.err.println("Warning: Cost must be non-negative.");
        }
    }

    public void setGapCost(int gapCost) {
        if (gapCost >= 0) {
            this.gapCost = gapCost;
        } else {
            System.err.println("Warning: Cost must be non-negative.");
        }
    }

    public void SetDynamicThreshold(boolean dynamicThreshold) {
        this.dynamicThreshold = dynamicThreshold;
    }

    public static class Suggestion {
        String word;
        int distance;

        public Suggestion(String word, int distance) {
            this.word = word;
            this.distance = distance;
        }
    }

    private int dynamicMaxDistance(String word) {
        return Math.max(maxDistance, (word.length() / 3));
    }

    public List<Suggestion> getSuggestions(String inputWord) {
        String normalizedWord = inputWord.toLowerCase();

        if (wordBank.contains(normalizedWord)) {
            List<Suggestion> exactMatch = new ArrayList<>();
            exactMatch.add(new Suggestion(inputWord, 0));
            return exactMatch;
        }

        List<Suggestion> suggestions = new ArrayList<>();

        for (String word : wordBank) {
            if ((word.charAt(0) != normalizedWord.charAt(0))
                    || (Math.abs((normalizedWord.length() - word.length())
                            * gapCost) > (dynamicThreshold ? dynamicMaxDistance(normalizedWord) : maxDistance))) {
                continue;
            }

            int distance = EditDistance.editDistance(normalizedWord, word, missCost, gapCost, matchCost);

            if (distance <= maxDistance) {
                suggestions.add(new Suggestion(word, distance));
            }
        }

        Collections.sort(suggestions, (a, b) -> {
            if (a.distance != b.distance) {
                return Integer.compare(a.distance, b.distance);
            }
            return a.word.compareTo(b.word);
        });

        return suggestions;
    }
}