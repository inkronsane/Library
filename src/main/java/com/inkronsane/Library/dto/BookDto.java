package com.inkronsane.Library.dto;


import com.fasterxml.jackson.annotation.*;
import java.util.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

   private UUID id;
   private String name;
   private String author;
   private Long amount;
}
