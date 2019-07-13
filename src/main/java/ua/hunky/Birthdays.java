package ua.hunky;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.hunky.dao.DB;
import ua.hunky.dao.PersonDAO;
import ua.hunky.dao.UserDAO;
import ua.hunky.model.Person;
import ua.hunky.model.User;

import java.util.List;

import static ua.hunky.model.ROLE.*;

/**
 * Created by hunky on 7/9/19.
 */

public class Birthdays {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        prepareDB(context);
        context.close();
    }

    private static void prepareDB(ClassPathXmlApplicationContext context) {
        DB db = context.getBean("db", DB.class);
        db.dropDB();
        db.prepareDB();

        PersonDAO personDAO = context.getBean("personDAO", PersonDAO.class);
        printPersons(personDAO.getAllPersons());
    }

    private static void printPersons(List<Person> personList) {
        System.out.println("*********************************************************************************************************");
        System.out.println("************************************************   PERSONS     ******************************************");
        System.out.println("*********************************************************************************************************");
        personList.forEach(p -> System.out.println(
                "PersonID : " + p.getPersonID() + " | " +
                        "Name : " + p.getName() + " | " +
                        "Surname : " + p.getSurname() + " | " +
                        "Email : " + p.getEmail() + " | " +
                        "DateOfBirth : " + p.getDateOfBirth()
        ));
        System.out.println("*********************************************************************************************************");
    }
}
