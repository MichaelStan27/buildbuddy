package com.buildbuddy.domain.consult.service;

import com.buildbuddy.domain.consult.dto.EmailDto;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Slf4j
@Service
public class EmailService {
    @Value("${spring.mail.host}")
    String host;

    @Value("${spring.mail.port}")
    String port;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(EmailDto emailDto) throws MessagingException, IOException {
        log.info("Start building Email...");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariable("name", emailDto.getUsername());
        context.setVariable("body", emailDto.getBody());

        String htmlTemplate = templateEngine.process("mail-template", context);

        helper.addTo(emailDto.getTo());

        helper.setSubject("Consultation Notification");
        htmlTemplate = htmlTemplate.replace("${body}", emailDto.getBody());

        helper.setText(htmlTemplate, true);

        log.info("Email Builded");
        javaMailSender.send(message);
        log.info("Email has been sent from server {}:{} to email: {}", host, port, emailDto.getTo());
    }

    private String readHtmlTemplate() {
        ClassLoader classLoader = getClass().getClassLoader();
        try(InputStream inputStream = classLoader.getResourceAsStream("templates/mail-template.html")){
            if(inputStream != null){
                return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (IOException e){
            throw new RuntimeException("Error when trying to read email template", e);
        }
        throw new IllegalArgumentException("file not found!");
    }
}
