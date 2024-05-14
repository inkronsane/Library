package com.inkronsane.Library.exception;


import java.util.*;

public class UserNotFoundException extends RuntimeException {

   public UserNotFoundException(UUID id) {
      super("User with '" + id + "' id not found.");
   }
}