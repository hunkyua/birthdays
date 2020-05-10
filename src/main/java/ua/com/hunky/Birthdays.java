package ua.com.hunky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import ua.com.hunky.model.Person;

import java.util.List;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = { "ua.com.hunky" }, excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION) })
public class Birthdays {

    public static void main(String[] args) {
        //SpringApplication.run(Birthdays.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(Birthdays.class, args);
    }

    private static void printPersons(List<Person> personList) {
        System.out.println("*********************************************************************************************************");
        System.out.println("************************************************   PERSONS     ******************************************");
        System.out.println("*********************************************************************************************************");
        personList.forEach(p -> System.out.println(
                "PersonID : " + p.getId() + " | " +
                        "Name : " + p.getName() + " | " +
                        "Surname : " + p.getSurname() + " | " +
                        "Email : " + p.getEmail() + " | " +
                        "DateOfBirth : " + p.getDateOfBirth()
        ));
        System.out.println("*********************************************************************************************************");
    }
}
