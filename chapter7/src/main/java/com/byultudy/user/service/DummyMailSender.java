package com.byultudy.user.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DummyMailSender implements MailSender {
    @Override
    public void send(final SimpleMailMessage simpleMessage) throws MailException {

    }

    @Override
    public void send(final SimpleMailMessage[] simpleMessages) throws MailException {

    }
}
