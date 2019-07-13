package ua.hunky;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.hunky.dao.DAOException;
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
    public static void main(String[] args) throws DAOException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        prepareDB(context);
        context.close();
    }

    private static void prepareDB(ClassPathXmlApplicationContext context) {
        DB db = context.getBean("db", DB.class);
        db.dropDB();
        db.prepareDB();

        User admin = context.getBean("user", User.class);
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setRole(ADMIN);

        User user = context.getBean("user", User.class);
        admin.setLogin("user");
        admin.setPassword("user");
        admin.setRole(USER);

        User unknown = context.getBean("user", User.class);
        admin.setLogin("unknown");
        admin.setPassword("unknown");
        admin.setRole(UNKNOWN);

        User userTest = context.getBean("user", User.class);
        admin.setLogin("userTest");
        admin.setPassword("userTest");
        admin.setRole(USER);

        UserDAO userDAO = context.getBean("userDAO", UserDAO.class);
        userDAO.createUser(admin);
        userDAO.createUser(user);
        userDAO.createUser(unknown);
        userDAO.createUser(userTest);

        userTest.setUserID(userDAO.getUserId(userTest));

        Person person = context.getBean("person", Person.class);
        Person personTest1 = context.getBean("personTest1", Person.class);
        Person personTest2 = context.getBean("personTest2", Person.class);
        PersonDAO personDAO = context.getBean("personDAO", PersonDAO.class);
        personDAO.addPerson(person);
        personDAO.addPerson(personTest1);
        personDAO.addPerson(personTest2);
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
