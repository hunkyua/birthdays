package ua.com.hunky.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import ua.com.hunky.model.Role;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.UserRepo;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo users;

    @Autowired
    MailSender mail;

    @Value("${hostname}")
    private String hostname;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.findByUsername(username);
    }

    public boolean addUser(User user) {
        User exist = users.findByUsername(user.getUsername());

        if (exist != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        users.save(user);

        if (!StringUtils.isEmpty(user.getEmail())) {
            sendEmail(user);
        }

        return true;
    }

    private void sendEmail(User user) {
        String message = String.format(

        "Hello, %s, \n" +
        "Thanks for joining Birthdays! Please confirm your email address and activate your account by clicking this link: \n\n" +
        "http://%s/activate/%s \n\n" +
        "If you have any trouble with your account, you can always email us at birthdaysapp.ua@gmail.com.\n" +
        "Regards, \n" +
        "The Birthdays team. \n" +
        "If you didn't register for Birthdays, please ignore this message."  ,

                user.getUsername(),
                hostname,
                user.getActivationCode()
        );
        mail.send(user.getEmail(), "Activation code", message);
    }

    public boolean activateUser(String code) {
        User user = users.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        users.save(user);

        return true;
    }
}