package ua.com.hunky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ua.com.hunky.dao.DB;
import ua.com.hunky.dao.PersonDAO;
import ua.com.hunky.model.Person;

import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = { "ua.com.hunky" }, excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class) })
public class Birthdays {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Birthdays.class, args);
        //prepareDB(context);
    }

    private static void prepareDB(ConfigurableApplicationContext context) {
        DB db = context.getBean(DB.class);
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
