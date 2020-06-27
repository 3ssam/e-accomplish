package com.learning.eaccomplish.controllers;

import com.learning.eaccomplish.models.Child;
import com.learning.eaccomplish.models.Report;
import com.learning.eaccomplish.models.Result;
import com.learning.eaccomplish.services.ChildService;
import com.learning.eaccomplish.services.ReportService;
import com.learning.eaccomplish.services.ResultService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    private ChildService childService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ResultService resultService;


    @RequestMapping("/{id}")
    public ResponseEntity deleteChild(@PathVariable("id") long id, Model model) {
        Child child = childService.getChild(id);
        String filename = child.getName() + ".xlsx";
        InputStreamResource file = new InputStreamResource(loadSheet(id));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }


    private ByteArrayInputStream loadSheet(long id) {

        Child child = childService.getChild(id);
        List<Report> reports = null;
        if (child == null)
            reports = new ArrayList<>();
        else
            reports = reportService.getReport(child);
        if (reports == null)
            reports = reportService.getReport(child);

        String[] HEADERs = {"Child Name", "Quiz Number",
                "Question", "Result", "Date"};
        String SHEET = "Child Report";


        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIndex = 1;
            for (Report report : reports) {
                List<Result> results = resultService.getResult(report);
                if (results == null)
                    results = new ArrayList<>();
                for (Result result : results) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(child.getName());
                    row.createCell(1).setCellValue(report.getQuiz().getLevel());
                    row.createCell(2).setCellValue(result.getQuestion().getFullQuestion());
                    row.createCell(3).setCellValue(result.getState());
                    row.createCell(4).setCellValue(report.getDate());
                }
                if (results.size() > 0) {
                    rowIndex += 2;
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue("Number of Wrong is : " + report.getNumOfWrong());
                    row = sheet.createRow(rowIndex);
                    row.createCell(0).setCellValue("Number of Right is : " + report.getNumOfRight());
                    rowIndex++;
                }
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }


}
