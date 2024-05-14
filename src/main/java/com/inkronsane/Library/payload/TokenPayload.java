package com.inkronsane.Library.payload;


import com.fasterxml.jackson.annotation.*;
import java.util.*;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenPayload extends Payload {

   private UUID uid;
   private String token;
   private String refreshToken;
   private String expirationDate;
}
