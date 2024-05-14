package com.inkronsane.Library.service;


import com.inkronsane.Library.dto.*;
import com.inkronsane.Library.dto.filter.*;
import com.inkronsane.Library.entity.*;
import com.inkronsane.Library.exception.*;
import com.inkronsane.Library.mapper.*;
import com.inkronsane.Library.payload.*;
import com.inkronsane.Library.repository.*;
import com.inkronsane.Library.spec.*;
import java.util.*;
import java.util.stream.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.validation.*;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

   private final UserRepository userRepository;
   private final UserMapper userMapper;
   @Autowired
   private Validator userValidator;

   public UserPayload createUser(UserDto request) {
      UserPayload response = new UserPayload();
      try {
         User user = userMapper.mapToEntity(request);
         userRepository.save(user);
         response.setInfo(200, "User created successfully");
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to create user: " + e.getMessage());
      }
      return response;
   }


   public UserPayload getAll(PageRequest page) {
      UserPayload response = new UserPayload();
      try {
         List<UserDto> users =
           userRepository.findAll(page).getContent().stream()
             .map(userMapper::mapToDto)
             .collect(Collectors.toList());
         response.setInfo(200, "Users fetched successfully");
         response.setDtos(users);
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to fetch users: " + e.getMessage());
      }
      return response;
   }


   public UserPayload geBytId(UUID id) {
      UserPayload response = new UserPayload();
      try {
         Optional<User> userOptional = userRepository.findById(id);
         if (userOptional.isPresent()) {
            UserDto user = userMapper.mapToDto(userOptional.get());
            response.setInfo(200, "User fetched successfully");
            response.setDto(user);
         } else {
            response.setErrorInfo(404, "User not found");
            throw new UserNotFoundException(id);
         }
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to fetch user: " + e.getMessage());
      }
      return response;
   }


   public UserPayload update(UserPayload request) {
      UserPayload response = new UserPayload();
      try {
         Optional<User> optionalUser =
           userRepository.findByUsername(request.getDto().getUsername());
         if (optionalUser.isPresent()) {
            Errors errors = new BeanPropertyBindingResult(request, "userDto");
            userValidator.validate(request, errors);
            if (errors.hasErrors()) {
               response.setErrorInfo(500, "Invalid user data");
               response.setErrors(errors.getAllErrors()); // List of errors
               return response;
            }
            User user = optionalUser.get();
            user.setFirstName(request.getDto().getFirstName());
            user.setLastName(request.getDto().getLastName());
            user.setBirthDate(request.getDto().getBirthDate());
            user.setInfo(request.getDto().getInfo());
            userRepository.save(user);
            response.setInfo(200, "User updated successfully");
         } else {
            response.setErrorInfo(404, "User not found");
         }
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to update user: " + e.getMessage());
      }
      return response;
   }

   public UserPayload searchUsers(UserFilterDto filter, PageRequest page) {
      UserPayload response = new UserPayload();
      try {
         Specification<User> spec = new UserSpecification(filter);
         response.setDtos(userRepository.findAll(spec, page).stream()
           .map(userMapper::mapToDto)
           .collect(Collectors.toList()));
         response.setInfo(200, "Users found successfully");
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to search users: " + e.getMessage());
      }
      return response;
   }

   public UserPayload delete(UUID id) {
      UserPayload response = new UserPayload();
      try {
         Optional<User> optionalComment = userRepository.findById(id);
         if (optionalComment.isPresent()) {
            userRepository.delete(optionalComment.get());
            response.setInfo(200, "User deleted successfully");
         } else {
            response.setErrorInfo(404, "User not found");
         }
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to delete user: " + e.getMessage());
      }
      return response;
   }
}
