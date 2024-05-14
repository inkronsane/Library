package com.inkronsane.Library.payload;


import com.fasterxml.jackson.annotation.*;
import com.inkronsane.Library.dto.*;
import java.util.*;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPayload extends Payload {

   private int code;
   private UserDto dto;
   private List<UserDto> dtos;
}
