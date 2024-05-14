package com.inkronsane.Library.payload;


import com.fasterxml.jackson.annotation.*;
import com.inkronsane.Library.dto.*;
import java.util.*;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestPayload extends Payload {

   private RequestDto dto;
   private List<RequestDto> dtos;
}
