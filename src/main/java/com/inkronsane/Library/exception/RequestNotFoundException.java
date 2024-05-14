package com.inkronsane.Library.exception;


import java.util.*;

public class RequestNotFoundException extends RuntimeException {

   public RequestNotFoundException(UUID id) {
      super("Request with '" + id + "' id not found.");
   }
}
