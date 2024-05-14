package com.inkronsane.Library.service;


import com.inkronsane.Library.dto.*;
import com.inkronsane.Library.payload.*;
import com.inkronsane.Library.repository.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cache.*;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.validation.*;

@Transactional
@Service
public class AuthService {

   @Autowired
   private UserService userService;
   @Autowired
   private JwtService jwtService;
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private PasswordEncoder passwordEncoder;
   @Autowired
   private AuthenticationManager authenticationManager;
   @Autowired
   private EmailService emailSenderService;
   @Autowired
   private CacheManager cacheManager;
   @Autowired
   private Validator userValidator;


   private static Integer generateVerificationCode() {
      return (int) (Math.random() * 900000 + 100000);
   }

   public UserPayload signUp(UserPayload request) {
      Errors errors = new BeanPropertyBindingResult(request, "userDto");
      userValidator.validate(request, errors);
      if (errors.hasErrors()) {
         UserPayload response = new UserPayload();
         response.setErrorInfo(500, "Invalid user data");
         response.setErrors(errors.getAllErrors());
         return response;
      }
      UserDto userDto = request.getDto();
      userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
      return userService.createUser(userDto);
   }

   public TokenPayload signIn(UserPayload signinRequest) {
      TokenPayload response = new TokenPayload();
      try {
         authenticationManager.authenticate(
           new UsernamePasswordAuthenticationToken(
             signinRequest.getDto().getUsername(), signinRequest.getDto().getPassword()
           )
         );
         var user = userRepository.findByUsername(signinRequest.getDto().getUsername()).get();
         var jwt = jwtService.generateToken(user);
         var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
         response.setStatusCode(200);
         response.setMessage("Successfully Signed In");
         response.setToken(jwt);
         response.setRefreshToken(refreshToken);
         response.setExpirationDate("24Hr");
      } catch (Exception e) {
         response.setStatusCode(500);
         response.setError(e.getMessage());
      }
      return response;
   }

   public TokenPayload refreshToken(TokenPayload refreshTokenRequest) {
      TokenPayload response = new TokenPayload();
      try {
         String username = jwtService.extractUsername(refreshTokenRequest.getRefreshToken());
         var user = userRepository.findByUsername(username).get();
         if (jwtService.isTokenValid(refreshTokenRequest.getRefreshToken(), user)) {
            String newToken = jwtService.generateToken(user);
            response.setStatusCode(200);
            response.setToken(newToken);
            response.setRefreshToken(refreshTokenRequest.getRefreshToken());
            response.setExpirationDate("24Hr");
            response.setMessage("Successfully Refreshed Token");
         } else {
            response.setStatusCode(401);
            response.setError("Invalid refresh token");
         }
      } catch (Exception e) {
         response.setStatusCode(500);
         response.setError("Internal server error");
      }
      return response;
   }

   public UserPayload codeChecking(UserPayload registrationRequest) {
      UserPayload response = new UserPayload();
      int code = isEmailCodeCached(registrationRequest.getDto().getEmail());
      if (code != 0) {
         if (code == registrationRequest.getCode()) {
            return signUp(registrationRequest);
         } else {
            response.setStatusCode(400);
            response.setMessage("incorrect code or email.");
            return response;
         }
      } else {
         code = generateVerificationCode();
         cacheEmailCode(registrationRequest.getDto().getEmail(), code);
         emailSenderService.sendRegistrationEmail(registrationRequest.getDto().getEmail(), code);
         response.setStatusCode(200);
         response.setMessage("Code was sent.");
      }
      return response;
   }

   private int isEmailCodeCached(String email) {
      Cache cache = cacheManager.getCache("VerificationCodes");
      if (cache != null && cache.get(email) != null) {
         return cache.get(email, Integer.class);
      }
      return 0;
   }

   private void cacheEmailCode(String email, int code) {
      Cache cache = cacheManager.getCache("VerificationCodes");
      if (cache != null) {
         cache.put(email, code);
      }
   }
}