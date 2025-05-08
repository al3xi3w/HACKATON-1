package org.example.init.domain;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
/*
@Component
public class WelcomeEmailListener {

    final private EmailService emailService;

    public WelcomeEmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    @Async
    public void sendWelcomeEmail(WelcomeEmailEvent welcomeEmailEvent) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        }
        emailService.sendEmail(welcomeEmailEvent.getEmail(), "Bienvenido", "Hola " + welcomeEmailEvent.getName() + ", bienvenido a nuestra plataforma.");
    }
}

 */
