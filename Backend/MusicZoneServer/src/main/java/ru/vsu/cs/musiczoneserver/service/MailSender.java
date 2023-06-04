package ru.vsu.cs.musiczoneserver.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {
    private final Properties props;
    private final String FROM = "Romses2016@yandex.ru";
    private final String PASS = "mrlspvbracorwyxi";
    private final String to;

    public MailSender(String to) {
        this.to = to;

        props = System.getProperties();
        props.put("mail.smtp.host", "smtp.yandex.ru");
        props.put("mail.smtp.user", FROM);
        props.put("mail.smtp.password", PASS);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.port", "465");
    }

    public void send(String subject, String text) throws MessagingException {
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASS);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setHeader("Content-Type", "text/plain; charset=UTF-8");
        message.setSubject(subject, "UTF-8");

        try {
            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(Message.RecipientType.TO,  new InternetAddress(this.to));

            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);

        } catch (MessagingException e) {
            System.out.println(e);
        }
    }
}
