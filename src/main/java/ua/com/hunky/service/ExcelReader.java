package ua.com.hunky.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ua.com.hunky.controller.PersonController;
import ua.com.hunky.model.Person;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.PersonRepo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.Iterator;

public class ExcelReader {

    private static final String XLSX_DEFAULT_EXT = ".xlsx";
    private DataFormatter formatter;
    private String file;

    private PersonRepo persons;

    public ExcelReader(File file, PersonRepo persons) {
        this.persons = persons;
        this.file = file.getName();
        this.formatter = new DataFormatter();

    }

    public void readExcelFile(User user) {
        try (FileInputStream excel = new FileInputStream(new File(file))) {
            Workbook workbook = loadWorkbook(excel);
            processSheet(user, workbook.getSheetAt(0));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private Workbook loadWorkbook(FileInputStream excel) throws IOException {
        return file.contains(XLSX_DEFAULT_EXT) ? new XSSFWorkbook(excel) : new HSSFWorkbook(excel);
    }

    private void processSheet(User user, Sheet sheet) throws ParseException {
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            Person person = getPerson(row);
            person.setUserID(user.getId());
            persons.save(person);
        }
    }

    private Person getPerson(Row row) throws ParseException {
        Iterator<Cell> iterator = row.iterator();
        Person person = new Person();
        while (iterator.hasNext()) {
            Cell cell = iterator.next();
            switch (cell.getColumnIndex()) {
                case 0:
                    person.setId(Long.parseLong(formatter.formatCellValue(cell)));
                    break;
                case 1:
                    person.setName(formatter.formatCellValue(cell));
                    break;
                case 2:
                    person.setSurname(formatter.formatCellValue(cell));
                    break;
                case 3:
                    person.setEmail(formatter.formatCellValue(cell));
                    break;
                case 4:
                    person.setDateOfBirth(getDate(cell));
                    return person;
            }
        }
        return person;
    }

    private java.sql.Date getDate(Cell cell) throws ParseException {
        return new Date(PersonController.DATE.parse(cell.getStringCellValue()).getTime());
    }

}