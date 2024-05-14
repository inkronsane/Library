package com.inkronsane.Library.service;


import com.inkronsane.Library.dto.*;
import com.inkronsane.Library.dto.filter.*;
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
public class BookService {

   @Autowired
   private BookRepository bookRepository;
   @Autowired
   private BookMapper bookMapper;
   @Autowired
   private Validator bookValidator;

   public BookPayload create(BookDto request) {
      BookPayload response = new BookPayload();
      try {
         var entity = bookMapper.mapToEntity(request);
         bookRepository.save(entity);
         response.setInfo(200, "Book created successfully");
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to create book: " + e.getMessage());
      }
      return response;
   }

   public BookPayload getAll(PageRequest page) {
      BookPayload response = new BookPayload();
      try {
         List<BookDto> collect =
           bookRepository.findAll(page).getContent().stream()
             .map(bookMapper::mapToDto)
             .collect(Collectors.toList());
         response.setInfo(200, "Books fetched successfully");
         response.setDtos(collect);
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to fetch books: " + e.getMessage());
      }
      return response;
   }

   public BookPayload getId(UUID id) {
      BookPayload response = new BookPayload();
      try {
         var optional = bookRepository.findById(id);
         if (optional.isPresent()) {
            var entity = bookMapper.mapToDto(optional.get());
            response.setInfo(200, "Book fetched successfully");
            response.setDto(entity);
         } else {
            response.setErrorInfo(404, "Book not found");
            throw new BookNotFoundException(id);
         }
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to fetch book: " + e.getMessage());
      }
      return response;
   }

   public BookPayload update(BookPayload request) {
      BookPayload response = new BookPayload();
      try {
         var optional = bookRepository.findById(request.getDto().getId());
         if (optional.isPresent()) {
            Errors errors = new BeanPropertyBindingResult(request, "bookDto");
            bookValidator.validate(request, errors);
            if (errors.hasErrors()) {
               response.setErrorInfo(400, "Invalid book data");
               response.setErrors(errors.getAllErrors());
               return response;
            }
            var entity = optional.get();
            entity.setAmount(request.getDto().getAmount());
            response.setInfo(200, "Updated successfully");
         } else {
            response.setErrorInfo(404, "Book not found");
         }
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to update book: " + e.getMessage());
      }
      return response;
   }

   public BookPayload searchBooks(BookFilterDto filter, PageRequest page) {
      BookPayload response = new BookPayload();
      try {
         var spec = new BookSpecification(filter);
         response.setDtos(bookRepository.findAll(spec, page).stream()
           .map(bookMapper::mapToDto)
           .collect(Collectors.toList()));
         response.setInfo(200, "Books found successfully");
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to search books: " + e.getMessage());
      }
      return response;
   }

   public BookPayload delete(UUID id) {
      BookPayload response = new BookPayload();
      try {
         var optional = bookRepository.findById(id);
         if (optional.isPresent()) {
            bookRepository.delete(optional.get());
            response.setInfo(200, "Book deleted successfully");
         } else {
            response.setErrorInfo(404, "Book not found");
         }
      } catch (Exception e) {
         response.setErrorInfo(500, "Failed to delete book: " + e.getMessage());
      }
      return response;
   }
}
