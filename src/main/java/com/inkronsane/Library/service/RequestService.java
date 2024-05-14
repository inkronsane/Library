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
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.validation.*;

@Transactional
@Service
@RequiredArgsConstructor
public class RequestService {

   @Autowired
   private RequestRepository requestRepository;
   @Autowired
   private RequestMapper requestMapper;
   @Autowired
   private Validator requestValidator;

   public RequestPayload create(RequestDto request) {
      RequestPayload response = new RequestPayload();
      try {
         var entity = requestMapper.mapToEntity(request);
         requestRepository.save(entity);
         response.setInfo(200, "User created successfully");
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to create user: " + e.getMessage());
      }
      return response;
   }


   public RequestPayload getAll(PageRequest page) {
      RequestPayload response = new RequestPayload();
      try {
         List<RequestDto> collect =
           requestRepository.findAll(page).getContent().stream()
             .map(requestMapper::mapToDto)
             .collect(Collectors.toList());
         response.setInfo(200, "Users fetched successfully");
         response.setDtos(collect);
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to fetch users: " + e.getMessage());
      }
      return response;
   }


   public RequestPayload getId(UUID id) {
      RequestPayload response = new RequestPayload();
      try {
         Optional<Request> optional = requestRepository.findById(id);
         if (optional.isPresent()) {
            var entity = requestMapper.mapToDto(optional.get());
            response.setInfo(200, "User fetched successfully");
            response.setDto(entity);
         } else {
            response.setErrorInfo(404, "User not found");
            throw new UserNotFoundException(id);
         }
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to fetch user: " + e.getMessage());
      }
      return response;
   }


   public RequestPayload update(RequestPayload request) {
      RequestPayload response = new RequestPayload();
      try {
         var optional =
           requestRepository.findById(request.getDto().getId());
         if (optional.isPresent()) {
            Errors errors = new BeanPropertyBindingResult(request, "userDto");
            requestValidator.validate(request, errors);
            if (errors.hasErrors()) {
               response.setErrorInfo(500, "Invalid entity data");
               response.setErrors(errors.getAllErrors()); // List of errors
               return response;
            }
            var entity = optional.get();
            entity.setStatus(RequestStatus.valueOf(request.getDto().getStatus()));
            entity.setContent(request.getDto().getContent());
            response.setInfo(200, "User updated successfully");
         } else {
            response.setErrorInfo(404, "User not found");
         }
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to update user: " + e.getMessage());
      }
      return response;
   }

   public RequestPayload searchUsers(RequestFilterDto filter, PageRequest page) {
      RequestPayload response = new RequestPayload();
      try {
         var spec = new RequestSpecification(filter);
         response.setDtos(requestRepository.findAll(spec, page).stream()
           .map(requestMapper::mapToDto)
           .collect(Collectors.toList()));
         response.setInfo(200, "Users found successfully");
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to search users: " + e.getMessage());
      }
      return response;
   }

   public RequestPayload delete(UUID id) {
      RequestPayload response = new RequestPayload();
      try {
         var optional = requestRepository.findById(id);
         if (optional.isPresent()) {
            requestRepository.delete(optional.get());
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
