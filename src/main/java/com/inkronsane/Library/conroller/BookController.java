package com.inkronsane.Library.conroller;


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
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
//TODO: Код використати з пейлоаду тут
public class BookController {

   @Autowired
   private final BookService bookService;

   @PostMapping("/create")
   public ResponseEntity<BookPayload> createBook(@RequestBody BookDto request) {
      return ResponseEntity.ok(bookService.create(request));
   }

   @GetMapping("/getAll")
   public ResponseEntity<BookPayload> getAllBooks(@RequestParam(defaultValue = "0") int page) {
      return ResponseEntity.ok(bookService.getAll(PageRequest.of(page, 10)));
   }

   @GetMapping("/{id}")
   public ResponseEntity<BookPayload> getBookById(@PathVariable UUID id) {
      return ResponseEntity.ok(bookService.getId(id));
   }

   @PutMapping("/update")
   public ResponseEntity<BookPayload> updateBook(@RequestBody BookPayload request) {
      return ResponseEntity.ok(bookService.update(request));
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<BookPayload> deleteBook(@PathVariable UUID id) {
      return ResponseEntity.ok(bookService.delete(id));
   }

   @GetMapping("/search")
   public ResponseEntity<BookPayload> searchBooks(BookFilterDto filter,
     @RequestParam(defaultValue = "0") int page) {
      return ResponseEntity.ok(bookService.searchBooks(filter, PageRequest.of(page, 10)));
   }
}
