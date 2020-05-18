package ua.com.hunky.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.com.hunky.model.Person;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.PersonRepo;
import ua.com.hunky.repository.UserRepo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Scheduler {
    @Autowired
    private UserRepo users;
    @Autowired
    private PersonRepo personRepo;
    @Autowired
    private MailSender mailSender;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");

    @Scheduled(cron = "0 0 10 * * *")
    public void reportCurrentTime() {
        List <User> allUsers = users.findAll();
        List <Person> allPersons = new ArrayList<>();
        for (User user : allUsers) {
            allPersons.addAll(personRepo.findAllByUserID(user.getId()));
            for (Person person : allPersons) {
                if (person.getDateOfBirth().toString().equals(dateFormat.format(new Date()))){
                    System.out.println(person.getDateOfBirth());
                    mailSender.send(user.getEmail(),
                                    "День Рождение у " + person.getName() + " " + person.getSurname(),
                                    "Сегодня День Рождение у твоего друга " + person.getName()  + " " + person.getSurname());
                }
            }
            allPersons.clear();
        }
    }
}
