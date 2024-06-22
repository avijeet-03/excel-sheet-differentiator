package com.avijeet.excel_differentiator.util;

import com.avijeet.excel_differentiator.model.Change;

import java.util.ArrayList;
import java.util.List;

public class DiffAlgo {

    public static List<Change> findChanges(List<List<String>> oldFileData, List<List<String>> newFileData) {
        // This will get all the difference in one present between both the files
        int n = oldFileData.size();
        int m = newFileData.size();
        List<Change> resultChanges = new ArrayList<>();

        // This is similar to a DP problem, called EDIT distance
        // we are going to modify the old file to new File
        // we can modify the lines and, add a new line and delete a new line
        // print the solution

        // fills the DP table
        int[][] dp = editDistanceProblem(oldFileData, newFileData);

        // forming the solution from the DP states
        int i = n, j = m;
        while (i > 0 && j > 0) {
            if (oldFileData.get(i - 1).equals(newFileData.get(j - 1))) {
                // no changes detected
                resultChanges.add(new Change("NO_CHANGE", i, oldFileData.get(i - 1)));
                i--;
                j--;
                continue;
            }

            int bestChoice = Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i][j - 1]));

            // whichever choice matches with the best Choice take that solution
            if (dp[i - 1][j] == bestChoice) {
                // deletion of line i
                resultChanges.add(new Change("DELETE", i, oldFileData.get(i - 1)));
                i--;
            } else if (dp[i][j - 1] == bestChoice) {
                // addition of line j
                resultChanges.add(new Change("ADD", j, newFileData.get(i - 1)));
                j--;
            } else {
                // edit of current line
                resultChanges.add(new Change("EDIT_OLD", i, oldFileData.get(i - 1)));
                resultChanges.add(new Change("EDIT_NEW", j, newFileData.get(j - 1)));
                i--;
                j--;
            }
        }

        while (i > 0) {
            resultChanges.add(new Change("DELETE", i, oldFileData.get(i - 1)));
            i--;
        }

        while (j > 0) {
            resultChanges.add(new Change("ADD", j, newFileData.get(j - 1)));
            j--;
        }

        reOrderTheChanges(resultChanges);

        return resultChanges;
    }

    private static int[][] editDistanceProblem(List<List<String>> oldfileData, List<List<String>> newFileData) {
        int n = oldfileData.size();
        int m = newFileData.size();

        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else if (oldfileData.get(i - 1).equals(newFileData.get(j - 1))) {
                    // no edit in the same line..line i is now line j
                    dp[i][j] = dp[i - 1][j - 1];
                }
                else {
                    // now we have three choice
                    // add one line to old file
                    int addLine = 1 + dp[i][j - 1];
                    // delete one line of old file
                    int deleteLine = 1 + dp[i - 1][j];
                    // edit the current line
                    int editLine = 1 + dp[i - 1][j - 1];

                    dp[i][j] = Math.min(addLine, Math.min(deleteLine, editLine));
                }
            }
        }

        return dp;
    }

    private static void reOrderTheChanges(List<Change> resultChanges) {
        // custom sort the changes in order to view the result properly
        // inside the lambda function, return
        //          - negative if c1 should come first than c2
        //          - positive if c2 should come first than c1
        //          - zero if order should be same
        // return type should be integer
        resultChanges.sort((c1, c2) -> {
            if (c1.getLineNo() < c2.getLineNo())
                return -1;
            return 1;
        });
    }
}
