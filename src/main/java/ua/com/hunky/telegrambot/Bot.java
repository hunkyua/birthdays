package ua.com.hunky.telegrambot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ua.com.hunky.model.Person;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.PersonRepo;
import ua.com.hunky.repository.UserRepo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class Bot extends TelegramLongPollingBot {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PersonRepo personRepo;
    private static final Logger log = Logger.getLogger(Bot.class.getName());
    private final int RECONNECT_PAUSE = 10000;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");

    public Bot() {
        botConnect();
    }

    @Setter
    @Getter
    private String login;
    private String password;

    @Getter
    private final String BotToken = "";
    private final String BotUsername = "BirthdaysBot";

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendScheduledMsg(Long chaId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chaId.toString());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Receive new Update. updateID: " + update.getUpdateId());
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText().split(" ")[0]) {
                case "/start":
                    sendMsg(message, "Welcome to BirthdaysApp \n" +
                    "Before use this bot you should setup your login and password");
                    break;
                case "/help" :
                    sendMsg(message, "Available commands only for authorised user: \n"
                    + "/tbd \n"
                    + "/mbd");
                    break;
                case "/setting" :
                    sendMsg(message, "Available commands\n" +
                    "Change login /changelogin \n" +
                    "Change password /changepass");
                    break;
                case "/changelogin":
                    String login = getParameter(message);

                    if (login != null && !login.isBlank()) {
                        this.login = login;
                        setChatIDToUser(message);
                        sendMsg(message, "Your login was successfully changed");
                        break;
                    }
                    sendMsg(message, "Enter new login! In format /changelogin mylogin");
                    break;
                case "/changepass":
                    String password = getParameter(message);
                    if (password != null && !password.isBlank()) {
                        this.password = password;
                        setChatIDToUser(message);
                        sendMsg(message, "Your password was successfully changed");
                        break;
                    }
                    sendMsg(message, "Enter new password! In format /changepass mypassword");
                    break;
                case "/tbd" :
                    sendMsg(message, getTodayBirthdays(message));
                    break;
                case "/mbd" :
                    sendMsg(message, getMonthBirthdays(message));
                    break;
                default:
            }
        }
    }

    private void setChatIDToUser(Message message) {
        if (isLoginAndPasswordCorrect(message)) {
            User user = userRepo.findByUsername(login);
            if (user.getChatID() == null || !user.getChatID().equals(message.getChatId())) {
                user.setChatID(message.getChatId());
                resetLoginPass();
            }
            userRepo.save(user);
        }
    }

    private void resetLoginPass() {
        login = null;
        password = null;
    }

    private String getParameter(Message message) {
        String parameter;
        if (message.getText().split(" ").length > 1) {
            parameter = message.getText().split(" ")[1];
        } else {
            parameter = null;
        }
        return parameter;
    }

    private void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/setting"));

        keyboardRows.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    @Override
    public String getBotUsername() {
        log.info("Bot name: " + BotUsername);
        return BotUsername;
    }

    private void botConnect() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
            log.info("TelegramAPI started. Bot connected and waiting for messages");
        } catch (TelegramApiException e) {
            log.warning("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }

    private boolean isUserWithChatIDExist(Message message) {
        List<User> allUsers = userRepo.findAll();
        for (User user : allUsers){
            if (user.getChatID() != null) {
                return user.getChatID().equals(message.getChatId());
            }
        }

        return false;
    }

    private boolean isLoginAndPasswordCorrect(Message message) {
        if (isDataNotEmpty() || isUserWithChatIDExist(message)) {
            var user = userRepo.findByUsername(login);
            if (user != null) {
                return user.getPassword().equals(password);
            }
        }

        return false;
    }

    private boolean isDataNotEmpty() {
        return login != null && password != null;
    }

    private String getTodayBirthdays(Message message) {
        if (isUserWithChatIDExist(message) && isDataNotEmpty()) {
            List<Person> persons = getPersons(message);
            if (persons == null) {
                return "You aren't authorised";
            }
            List<Person> filteredPersons = persons.stream().filter(p ->
                    p.getDateOfBirth().toString().subSequence(5, 10).equals(dateFormat.format(new Date()).subSequence(5, 10))).collect(Collectors.toList());
            if (filteredPersons.size() == 0) {
                return "У Ваших друзей сегодня нет Дня Рождения";
            }
            StringBuilder result = new StringBuilder();
            result.append("Сегодня День Рождение у ");
            birthdays(filteredPersons, result);
            return result.toString();
        } else {
            return "You aren't authorised";
        }
    }


    private String getMonthBirthdays(Message message) {
        if (isUserWithChatIDExist(message)) {
            List<Person> persons = getPersons(message);
            List<Person> filteredPersons = persons.stream().filter(p ->
                    p.getDateOfBirth().toLocalDate().getMonthValue() == Calendar.getInstance().get(Calendar.MONTH) + 1).collect(Collectors.toList());
            if (filteredPersons.size() == 0) {
                return "У Ваших друзей сегодня нет Дня Рождения";
            }
            StringBuilder result = new StringBuilder();
            result.append("В этом месяце День Рождение у ");
            birthdays(filteredPersons, result);
            return result.toString();
        } else {
            return "You aren't authorised";
        }
    }

    private void birthdays(List<Person> filteredPersons, StringBuilder result) {
        if (filteredPersons.size() == 1) {
            var per = filteredPersons.get(0);
            result.append("твоего друга \n");
            result.append(per.getName());
            result.append(" ");
            result.append(per.getSurname());
            result.append(" ");
            result.append(per.getDateOfBirth().toLocalDate().getDayOfMonth());
            result.append("-го ");
            result.append("числа");
        } else {
            result.append("твоих друзей \n");
            for (Person fp : filteredPersons) {
                result.append(fp.getName());
                result.append(" ");
                result.append(fp.getSurname());
                result.append("\n");
            }
        }
    }

    private List<Person> getPersons(Message message) {
        User user = userRepo.findByChatIDAndUsernameAndPassword(message.getChatId(), login, password);
        if (user != null) {
            return personRepo.findAllByUserID(user.getId());
        } else {
            return null;
        }
    }

}