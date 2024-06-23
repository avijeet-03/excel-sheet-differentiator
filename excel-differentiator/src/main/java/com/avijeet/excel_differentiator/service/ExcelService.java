package com.avijeet.excel_differentiator.service;

import com.avijeet.excel_differentiator.model.Change;
import com.avijeet.excel_differentiator.util.DiffAlgo;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {
    public List<Change> getExcelFileDifference(MultipartFile oldCommitFile, MultipartFile newCommitFile) {
        try {
            Workbook oldWorkbook = WorkbookFactory.create(oldCommitFile.getInputStream());
            Workbook newWorkbook = WorkbookFactory.create(newCommitFile.getInputStream());

            Sheet oldWorkbookSheet = oldWorkbook.getSheetAt(0);
            Sheet newWorkbookSheet = newWorkbook.getSheetAt(0);

            // convert the sheet data into list of lists of strings
            List<List<String>> oldFileData = getSheetData(oldWorkbookSheet);
            List<List<String>> newFileData = getSheetData(newWorkbookSheet);
            // get the difference between two list of lists
            return DiffAlgo.findChanges(oldFileData, newFileData);
        } catch (Exception e) {
            return null;
        }
    }

    private List<List<String>> getSheetData(Sheet sheet) {
        List<List<String>> sheetData = new ArrayList<>();

        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            Row currRow = sheet.getRow(i);
            List<String> currRowData = new ArrayList<>();
            if (null == currRow) {
                sheetData.add(currRowData);
                continue;
            }
            for (Cell cell : currRow) {
                currRowData.add(cell.toString());
            }
            sheetData.add(currRowData);
        }
        return sheetData;
    }
}
