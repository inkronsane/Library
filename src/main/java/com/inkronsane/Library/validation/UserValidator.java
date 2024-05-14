package com.inkronsane.Library.validation;


import com.inkronsane.Library.payload.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;

@Component
public class UserValidator implements Validator {

   @Override
   public boolean supports(Class<?> clazz) {
      return UserPayload.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      UserPayload userPayload = (UserPayload) target;

      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dto.username", "NotEmpty");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dto.email", "NotEmpty");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dto.password", "NotEmpty");

      if (userPayload.getDto().getUsername().length() < 6) {
         errors.rejectValue("dto.username", "Size.userForm.username");
      }

      if (!userPayload.getDto().getEmail().matches("^(?!.*gmail.com$)")) {
         errors.rejectValue("dto.email", "Email.userForm.email");
      }

      if (userPayload.getDto().getPassword().length() < 10) {
         errors.rejectValue("dto.password", "Size.userForm.password");
      }
   }
}