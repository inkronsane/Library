package com.inkronsane.Library.dto;


import com.fasterxml.jackson.annotation.*;
import java.util.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {

   private UUID id;
   private String status;
   private String type;
   private UUID authorUUID;
   private String authorUsername;
   private String content;
}
