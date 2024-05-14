package com.inkronsane.Library.conroller;


import com.inkronsane.Library.payload.*;
import com.inkronsane.Library.repository.*;
import com.inkronsane.Library.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
//TODO: Код використати з пейлоаду тут
public class AuthController {

   @Autowired
   private AuthService authService;
   @Autowired
   private UserService userService;
   @Autowired
   private UserRepository UserRepository;
   @Autowired
   private UserRepository userRepository;

   //TODO: Код використати з пейлоаду тут
   @PostMapping("/signup")
   public ResponseEntity<UserPayload> signUp(@RequestBody UserPayload signUpRequest) {
      UserPayload response = authService.codeChecking(signUpRequest);
      if (response.getStatusCode() == 200) {
         return ResponseEntity.ok(response);
      } else {
         return ResponseEntity.status(response.getStatusCode()).build();
      }
   }

   @CrossOrigin
   @PostMapping("/signin")
   public ResponseEntity<TokenPayload> signIn(@RequestBody UserPayload signInRequest) {
      TokenPayload response = authService.signIn(signInRequest);
      if (response.getStatusCode() == 200) {
         response.setUid(
           userRepository.findByUsername(signInRequest.getDto().getUsername()).get().getId());
         return ResponseEntity.ok(response);
      } else {
         return ResponseEntity.status(response.getStatusCode()).build();
      }
   }

   @PostMapping("/refresh")
   public ResponseEntity<TokenPayload> refreshToken(@RequestBody TokenPayload refreshTokenRequest) {
      return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
   }
}