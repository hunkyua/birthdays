package ua.com.hunky.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import ua.com.hunky.model.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelPersonExportAndImport extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment;filename=\"person.xls\"");
        List<Person> personList = (List<Person>) model.get("personList");
        Sheet sheet = workbook.createSheet("Persons");
        Row header = sheet.createRow(0);
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Surname");
        header.createCell(3).setCellValue("Email");
        header.createCell(4).setCellValue("DateOfBirth");

        int rowNum = 1;
        for(Person person:personList){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(person.getId());
            row.createCell(1).setCellValue(person.getName());
            row.createCell(2).setCellValue(person.getSurname());
            row.createCell(3).setCellValue(person.getEmail());
            Cell cell = row.createCell(4);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(person.getDateOfBirth());

        }
    }
}