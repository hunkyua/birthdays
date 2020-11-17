package ua.com.hunky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = { "ua.com.hunky" }, excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION) })
public class Birthdays {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Birthdays.class, args);
    }

}
