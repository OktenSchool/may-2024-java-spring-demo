package org.okten.may2024.demo.service;

import lombok.RequiredArgsConstructor;
import org.okten.may2024.demo.dto.SendMailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(SendMailDto sendMailDto) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(sendMailDto.to());

        message.setSubject(sendMailDto.subject());
        message.setText(sendMailDto.text());

        mailSender.send(message);
    }
}
