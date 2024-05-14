package com.inkronsane.Library.validation;


import com.inkronsane.Library.payload.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;

@Component
public class BookValidator implements Validator {

   @Override
   public boolean supports(Class<?> clazz) {
      return BookPayload.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      BookPayload bookPayload = (BookPayload) target;

      // Перевірка на пусте ім'я книги
      if (bookPayload.getDto().getName() == null || bookPayload.getDto().getName().isEmpty()) {
         errors.rejectValue("dto.name", "NotEmpty");
      }

      // Перевірка на пустого автора книги
      if (bookPayload.getDto().getAuthor() == null || bookPayload.getDto().getAuthor().isEmpty()) {
         errors.rejectValue("dto.author", "NotEmpty");
      }

      // Перевірка на невід'ємну кількість книг
      if (bookPayload.getDto().getAmount() != null && bookPayload.getDto().getAmount() < 0) {
         errors.rejectValue("dto.amount", "NegativeOrZero");
      }
   }
}
