package com.inkronsane.Library.dto.filter;


import com.fasterxml.jackson.annotation.*;
import com.inkronsane.Library.entity.*;
import java.util.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestFilterDto {

   private UUID authorUUID;
   private RequestStatus status;
   private RequestType type;
}
