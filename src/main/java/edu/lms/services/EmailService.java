package edu.lms.services;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

public class EmailService {

    private static Session email;

    public static void initialize() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        email = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Config.FROM_EMAIL, Config.EMAIL_PASSWORD);
            }
        });
    }

    public static void sendVerificationCode(String toEmail, int code) {
        if (email == null) {
            throw new IllegalStateException("Email is not initialized. Call initialize first. :(");
        }

        try {
            Message message = new MimeMessage(email);
            message.setFrom(new InternetAddress(Config.FROM_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            message.setSubject("Your Confirmation Code");
            message.setText("Your confirmation code is: " + code);

            Transport.send(message);
            System.out.println("Confirmation email sent successfully to the recipient!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessageEmail(String toEmail, String title, String text) {
        if (email == null) {
            throw new IllegalStateException("Email is not initialized. Call initialize first. :(");
        }

        try {
            Message message = new MimeMessage(email);
            message.setFrom(new InternetAddress(Config.FROM_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            message.setSubject(title);
            message.setText(text);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessageEmailToMultipleRecipients(List<String> toEmails, String title, String text) {
        if (email == null) {
            throw new IllegalStateException("Email is not initialized. Call initialize first. :(");
        }

        try {
            Message message = new MimeMessage(email);
            message.setFrom(new InternetAddress(Config.FROM_EMAIL));
            for (String toEmail : toEmails) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            }

            message.setSubject(title);
            message.setText(text);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initialize();

        System.out.println("send verification code...");
        sendVerificationCode("23021517@vnu.edu.vn", 123456);
    }

}

