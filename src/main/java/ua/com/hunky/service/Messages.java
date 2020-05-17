package ua.com.hunky.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Locale;

@Service
public class Messages {
    private MessageSourceAccessor accessor;

    public Messages(MessageSource messageSource) {
        this.accessor = new MessageSourceAccessor(messageSource);
    }

    public String get(String code, String ... args) {
        Locale locale = LocaleContextHolder.getLocale();

        String message = accessor.getMessage(code, locale);

        return MessageFormat.format(message, args);
    }

}