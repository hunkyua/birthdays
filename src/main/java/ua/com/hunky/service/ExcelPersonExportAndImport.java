package ua.com.hunky.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import ua.com.hunky.controller.PersonController;
import ua.com.hunky.model.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelPersonExportAndImport extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {

        response.setHeader("Content-Disposition", "attachment;filename=\"person.xls\"");

        List<Person> persons = (List<Person>) model.get("personList");

        Sheet sheet = workbook.createSheet("Persons");
        setHeader(sheet);

        int number = 0;
        for (Person person : persons) {
            setRow(sheet, ++number, person);
        }
    }

    private void setRow(Sheet sheet, int number, Person person) {
        createRow(sheet, number,
                String.valueOf(person.getId()),
                person.getName(),
                person.getSurname(),
                person.getEmail(),
                PersonController.DATE.format(person.getDateOfBirth()));
    }

    private void setHeader(Sheet sheet) {

        createRow(sheet, 0, "ID", "Name", "Surname", "Email", "DateOfBirth");
    }

    private void createRow(Sheet sheet, int row, String... data) {
        Row header = sheet.createRow(row);
        for (int i = 0; i < data.length; i++) {
            header.createCell(i).setCellValue(data[i]);
        }
    }

    @Override
    protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
        return new HSSFWorkbook();
    }
}