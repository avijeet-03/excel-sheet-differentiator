package com.avijeet.excel_differentiator.util;

import com.avijeet.excel_differentiator.model.Change;

import java.util.ArrayList;
import java.util.List;

public class DiffAlgo {

    public static List<Change> findChanges(List<List<String>> oldFileData, List<List<String>> newFileData) {
        // This will get all the difference in one present between both the files
        int n = oldFileData.size();
        List<Change> resultChanges = new ArrayList<>(n);
        // will implement the logic for handling file change and save it here
        return resultChanges;
    }
}
