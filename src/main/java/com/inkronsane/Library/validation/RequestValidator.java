package com.inkronsane.Library.validation;


import com.inkronsane.Library.payload.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;

@Component
public class RequestValidator implements Validator {

   @Override
   public boolean supports(Class<?> clazz) {
      return RequestPayload.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      RequestPayload requestPayload = (RequestPayload) target;

      // Перевірка на пустий контент запиту
      if (requestPayload.getDto().getContent() == null || requestPayload.getDto().getContent()
        .isEmpty()) {
         errors.rejectValue("dto.content", "NotEmpty");
      }

      // Тут можна додати інші перевірки за необхідності
   }
}
