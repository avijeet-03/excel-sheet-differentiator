package com.avijeet.excel_differentiator.controller;

import com.avijeet.excel_differentiator.model.Change;
import com.avijeet.excel_differentiator.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/excel")
public class ExcelController {
    @Autowired
    private ExcelService excelService;

    @GetMapping("/get-application-health")
    public String checkApplicationHealth() {
        return "Excel Sheet differentiator application is up and running";
    }

    @PostMapping("/diff")
    public ResponseEntity<List<Change>> getDifferenceOfTwoExcelFiles(
            @RequestPart("oldCommitFile") MultipartFile oldCommitFile,
            @RequestPart("newCommitFile") MultipartFile newCommitFile) throws IOException {

        try {
            if (null == oldCommitFile && null == newCommitFile)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            List<Change> changes = excelService.getExcelFileDifference(oldCommitFile, newCommitFile);
            return ResponseEntity.ok(changes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
