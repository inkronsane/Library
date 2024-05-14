package com.inkronsane.Library.conroller;


import com.inkronsane.Library.dto.filter.*;
import com.inkronsane.Library.payload.*;
import com.inkronsane.Library.service.*;
import java.security.*;
import java.util.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
//TODO: Код використати з пейлоаду тут
public class UserController {

   @Autowired
   private final UserService userService;

   @GetMapping("/getAll")
   public ResponseEntity<UserPayload> getAllUsers(@RequestParam(defaultValue = "0") int page) {
      return ResponseEntity.ok(userService.getAll(PageRequest.of(page, 10)));
   }

   @GetMapping("/{id}")
   public ResponseEntity<UserPayload> getUserById(@PathVariable UUID id) {
      return ResponseEntity.ok(userService.geBytId(id));
   }

   @GetMapping("/search")
   public ResponseEntity<UserPayload> searchUsers(UserFilterDto userSearch,
     @RequestParam(defaultValue = "0") int page) {
      return ResponseEntity.ok(userService.searchUsers(userSearch, PageRequest.of(page, 10)));
   }

   @PutMapping("/update")
   public ResponseEntity<UserPayload> updateUser(@RequestBody UserPayload request,
     Principal principal) {
      UUID input = UUID.fromString(principal.getName());
      if (userService.geBytId(input) != null) {
         request.getDto().setId(input);
         return ResponseEntity.ok(userService.update(request));
      } else {
         return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      }
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<UserPayload> deleteArticle(@PathVariable UUID id, Principal principal) {
      UUID input = UUID.fromString(principal.getName());
      if (input == userService.geBytId(id).getDto().getId()) {
         return ResponseEntity.ok(userService.delete(id));
      } else {
         return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      }
   }
}