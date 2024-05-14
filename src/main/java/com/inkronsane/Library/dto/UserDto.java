package com.inkronsane.Library.dto;


import com.fasterxml.jackson.annotation.*;
import java.time.*;
import java.util.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

   private UUID id;
   private String username;
   private String email;
   private String password;
   private String firstName;
   private String lastName;
   private LocalDate birthDate;
   private String info;
   private String role;
}