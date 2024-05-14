package com.inkronsane.Library.dto.filter;


import com.fasterxml.jackson.annotation.*;
import com.inkronsane.Library.entity.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFilterDto {

   private String username;
   private Role role;
}
