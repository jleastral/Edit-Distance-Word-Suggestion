public class EditDistance {
    public static int editDistance(String s1, String s2, int missCost, int gapCost, int matchCost) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int m = s1.length();
        int n = s2.length();

        // opt is of size m + 1 and n + 1
        // because m and n are inclusive
        int[][] opt = new int[m + 1][n + 1];

        // opt[i][n] = GAP_COST(m - i)
        for (int i = m; i >= 0; i--) {
            opt[i][n] = (m - i) * gapCost;
        }
        // opt[m][j] = GAP_COST(n - j)
        for (int j = n; j >= 0; j--) {
            opt[m][j] = (n - j) * gapCost;
        }
        // opt[i][j] = min(opt[i + 1][j + 1] + MATCH_COST or MISS_COST,
        // opt[i + 1][j] + GAP_COST, opt[i][j + 1] + GAP_COST)
        for (int i = (m - 1); i >= 0; i--) {
            for (int j = (n - 1); j >= 0; j--) {
                int n1 = opt[i + 1][j + 1] + ((s1.charAt(i) == s2.charAt(j)) ? matchCost : missCost);
                int n2 = opt[i + 1][j] + gapCost;
                int n3 = opt[i][j + 1] + gapCost;

                int min1 = Math.min(n1, n2);
                int min2 = Math.min(n2, n3);
                opt[i][j] = Math.min(min1, min2);
            }
        }

        return opt[0][0];
    }

    public static int editDistance(String s1, String s2) {
        return editDistance(s1, s2, 1, 2, 0);
    }
}
