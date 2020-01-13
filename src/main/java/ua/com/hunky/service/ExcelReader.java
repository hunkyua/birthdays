package ua.com.hunky.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ua.com.hunky.model.Person;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.PersonRepo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

public class ExcelReader {

    private final PersonRepo personRepo;

    private static String FILE_NAME;

    private final User user;

    public ExcelReader(File file, PersonRepo personRepo, User user) {
        this.personRepo = personRepo;
        FILE_NAME = file.getName();
        this.user = user;
    }

    public void ReadXLSX() {
        try {
            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            //By default works with .xls files
            Workbook workbook = new HSSFWorkbook(excelFile);
            if (FILE_NAME.contains(".xlsx")) {
                workbook = new XSSFWorkbook(excelFile);
            }
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            int numberOfRow = 0;
            while (iterator.hasNext()) {
                numberOfRow++;
                Row currentRow = iterator.next();
                if (numberOfRow == 1) {
                    continue;
                }
                DataFormatter fmt = new DataFormatter();
                Iterator<Cell> cellIterator = currentRow.iterator();
                Person person = new Person();
                int numberOfCell = 0;
                while (cellIterator.hasNext()) {
                    numberOfCell++;
                    Cell currentCell = cellIterator.next();
                    switch (numberOfCell) {
                        case 1:
                            person.setId(Long.parseLong(fmt.formatCellValue(currentCell)));
                            break;
                        case 2:
                            person.setName(fmt.formatCellValue(currentCell));
                            break;
                        case 3:
                            person.setSurname(fmt.formatCellValue(currentCell));
                            break;
                        case 4:
                            person.setEmail(fmt.formatCellValue(currentCell));
                            break;
                        case 5:
                            Date date = currentCell.getDateCellValue();
                            java.sql.Date sDate = new java.sql.Date(date.getTime());
                            person.setDateOfBirth(sDate);
                            person.setUserID(user.getId());
                            personRepo.save(person);
                            numberOfCell = 0;
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}