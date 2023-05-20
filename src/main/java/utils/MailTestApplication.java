package utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
@SpringBootApplication
public class MailTestApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MailTestApplication.class, args);

        // Obtain the MailService bean
        MailService mailService = context.getBean(MailService.class);

        // Email parameters
        String to = "bilalsaifi24977@gmail.com";
        String subject = "Test Spring Mail";
        String body = "This is a sample mail sent by the Spring framework.";

        // Send the email
        mailService.sendEmail(to, subject, body);
    }
}