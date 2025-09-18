//package com.th.guard.component;
//
//import com.th.guard.service.GmailService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class EmailRunner implements CommandLineRunner {
//
//    private final GmailService gmailService;
//
//    public EmailRunner(GmailService gmailService) {
//        this.gmailService = gmailService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        gmailService.sendSimpleMessage(
//                "recipient@example.com",
//                "Hello from Spring Boot",
//                "This is a test email sent using Spring Boot 2.7.18 and Java 11!"
//        );
//    }
//}
