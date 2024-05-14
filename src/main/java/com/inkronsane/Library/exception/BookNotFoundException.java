package com.inkronsane.Library.exception;


import java.util.*;

public class BookNotFoundException extends RuntimeException {

   public BookNotFoundException(UUID id) {
      super("Book with '" + id + "' id not found.");
   }
}
