package com.inkronsane.Library.spec;


import com.inkronsane.Library.dto.filter.*;
import com.inkronsane.Library.entity.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.*;

@AllArgsConstructor

public class BookSpecification implements Specification<Book> {

   transient BookFilterDto filter;

   @Override
   public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query,
     CriteriaBuilder criteriaBuilder) {
      Predicate predicate = criteriaBuilder.conjunction();

      if (filter.getName() != null) {
         predicate = criteriaBuilder.and(predicate,
           criteriaBuilder.equal(root.get("name"), filter.getName()));
      }

      if (filter.getAuthor() != null) {
         predicate = criteriaBuilder.and(predicate,
           criteriaBuilder.equal(root.get("author"), filter.getAuthor()));
      }
      return predicate;
   }
}
