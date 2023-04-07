package com.simplon.rhp.services.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSender {

    final JavaMailSender javaMailSender;


    // method to send email
    public void sendEmail(String toAddress, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toAddress);
        msg.setSubject(subject);
        msg.setText(body);

        javaMailSender.send(msg);
    }


    // email sent to the user when his account is created
    public void sendEmailUserCreated(String toAddress,String firstName,String lastname , String username,String password) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toAddress);
        msg.setSubject("Welcome to RHP");
        msg.setText("Dear " + firstName+ ",\n\n" +
                "We are pleased to inform you that your account has been successfully created on our platform.\n\n" +
                "Thank you for choosing our services and trusting us with your business needs." +
                "Your account details are as follows:\n\n" +
                        "\tUsername: " + username + "\n" +
                        "\tPassword: " + password + "\n\n" +
                "Please do not hesitate to contact us if you have any questions or concerns.\n\n" +
                "Best regards,\n" +
                "The RHP Team");
        javaMailSender.send(msg);
    }


}
