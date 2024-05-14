package com.inkronsane.Library.spec;


import com.inkronsane.Library.dto.filter.*;
import com.inkronsane.Library.entity.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.*;

@AllArgsConstructor

public class UserSpecification implements Specification<User> {

   transient UserFilterDto userSearch;

   @Override
   public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
     CriteriaBuilder criteriaBuilder) {
      Predicate predicate = criteriaBuilder.conjunction();

      if (userSearch.getUsername() != null) {
         predicate = criteriaBuilder.and(predicate,
           criteriaBuilder.equal(root.get("username"), userSearch.getUsername()));
      }

      if (userSearch.getRole() != null) {
         predicate = criteriaBuilder.and(predicate,
           criteriaBuilder.equal(root.get("role"), userSearch.getRole()));
      }
      return predicate;
   }
}