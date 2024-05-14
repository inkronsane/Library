package com.inkronsane.Library.service;


import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.*;
import java.nio.charset.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

   @Autowired
   private JavaMailSender mailSender;

   public void sendRegistrationEmail(String toEmail, int code) {
      try {
         MimeMessage message = mailSender.createMimeMessage();
         MimeMessageHelper helper = new MimeMessageHelper(message, true);

         // Load the content of the email template from a file
         String emailContent = loadEmailTemplate("registration-email-template.html");
         // Replace the parameters in the email template with real data
         emailContent = emailContent.replace("[user email]", toEmail);
         emailContent = emailContent.replace("[Verification Code]", String.valueOf(code));

         helper.setTo(toEmail);
         helper.setSubject("Registration confirmation");
         helper.setText(emailContent, true);

         mailSender.send(message);
      } catch (MessagingException | IOException e) {
         e.printStackTrace();
      }
   }

   private String loadEmailTemplate(String path) throws IOException {
      try (InputStream inputStream = getClass().getResourceAsStream("/templates/" + path)) {
         assert inputStream != null;
         try (InputStreamReader reader = new InputStreamReader(inputStream,
           StandardCharsets.UTF_8)) {
            StringBuilder content = new StringBuilder();
            char[] buffer = new char[1024];
            int bytesRead;
            while ((bytesRead = reader.read(buffer)) != -1) {
               content.append(buffer, 0, bytesRead);
            }
            return content.toString();
         }
      }
   }
}