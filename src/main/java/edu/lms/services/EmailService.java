package edu.lms.services;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailService {

    /*static {
        // Set JavaMail logging level to SEVERE to suppress warnings
        Logger.getLogger("javax.mail").setLevel(Level.SEVERE);
    }*/

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

            String content = "<img src='src/main/resources/edu/lms/images/Logo.png' alt='LMS Logo' style='width:100px; height:auto;' />" +
                    "<h2>Verify your identity</h2>" +
                    "<p>Hello,</p>" +
                    "<p>We identified unusual activity in a recent sign-un attempt from your lms account's user. " +
                    "If you initiated the request to sign up into lms, please enter the following code to verify your identity and complete your sign-up.</p>" +
                    "<h1 style='color: #ff9900;'>Verification code<br> " + code + "</h1>" +
                    "<p><small>(This code will expire 10 minutes after it was sent.)</small></p>" +
                    "<p>For more information, visit <a href='https://docs.lms.apple'>this link</a>.</p>";

            message.setContent(content, "text/html");

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

