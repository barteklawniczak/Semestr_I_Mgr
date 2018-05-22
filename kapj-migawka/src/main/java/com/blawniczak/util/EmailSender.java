package com.blawniczak.util;

import com.blawniczak.domain.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailSender {

    private Logger logger = Logger.getLogger(EmailSender.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendActivationLink(User user) {

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmail());
            helper.setSubject("Confirm your registration in MigawkaApp");
            helper.setText("Your link: http://localhost:8080/confirmation?id=" + user.getId() + "&token=" + user.getToken());
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
