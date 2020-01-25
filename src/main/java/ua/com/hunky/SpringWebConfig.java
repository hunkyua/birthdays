package ua.com.hunky;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Locale;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer {

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        return new LocaleChangeInterceptor(){{
            setParamName("lang");
        }};
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        return new ResourceBundleMessageSource(){{
            setBasename("messages");
            setDefaultEncoding("UTF-8");
        }};

    }

    @Override
    public Validator getValidator() {
        return new LocalValidatorFactoryBean(){{
            setValidationMessageSource(messageSource());
        }};
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver(){{
            setDefaultLocale(Locale.ENGLISH);
        }};
    }

    @Bean
    public ITemplateResolver templateResolver() {
        return new SpringResourceTemplateResolver(){{
            setPrefix("classpath:/templates/");
            setSuffix(".html");
            setTemplateMode("HTML5");
            setCacheable(false);
        }};
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        return new SpringTemplateEngine(){{
            setTemplateResolver(templateResolver());
            setEnableSpringELCompiler(true);
        }};
    }

    @Bean
    public ThymeleafViewResolver viewResolver(){
        return new ThymeleafViewResolver(){{
            setTemplateEngine(templateEngine());
            setOrder(1);
            setViewNames(new String[] {".html", ".xhtml"});
        }};

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login")
                .setViewName("login");
    }

}
