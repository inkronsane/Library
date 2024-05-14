package com.inkronsane.Library.controller;


import com.inkronsane.Library.dto.*;
import com.inkronsane.Library.dto.filter.*;
import com.inkronsane.Library.payload.*;
import com.inkronsane.Library.service.*;
import java.util.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/requests")
@RequiredArgsConstructor
//TODO: Код використати з пейлоаду тут
public class RequestController {

   @Autowired
   private final RequestService requestService;

   @PostMapping("/create")
   public ResponseEntity<RequestPayload> createRequest(@RequestBody RequestDto request) {
      return ResponseEntity.ok(requestService.create(request));
   }

   @GetMapping("/getAll")
   public ResponseEntity<RequestPayload> getAllRequests(
     @RequestParam(defaultValue = "0") int page) {
      return ResponseEntity.ok(requestService.getAll(PageRequest.of(page, 10)));
   }

   @GetMapping("/{id}")
   public ResponseEntity<RequestPayload> getRequestById(@PathVariable UUID id) {
      return ResponseEntity.ok(requestService.getId(id));
   }

   @PutMapping("/update")
   public ResponseEntity<RequestPayload> updateRequest(@RequestBody RequestPayload request) {
      return ResponseEntity.ok(requestService.update(request));
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<RequestPayload> deleteRequest(@PathVariable UUID id) {
      return ResponseEntity.ok(requestService.delete(id));
   }

   @GetMapping("/search")
   public ResponseEntity<RequestPayload> searchRequests(RequestFilterDto filter,
     @RequestParam(defaultValue = "0") int page) {
      return ResponseEntity.ok(requestService.searchUsers(filter, PageRequest.of(page, 10)));
   }
}
