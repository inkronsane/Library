package com.inkronsane.Library.spec;


import com.inkronsane.Library.dto.filter.*;
import com.inkronsane.Library.entity.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.*;

@AllArgsConstructor

public class RequestSpecification implements Specification<Request> {

   transient RequestFilterDto filter;

   @Override
   public Predicate toPredicate(Root<Request> root, CriteriaQuery<?> query,
     CriteriaBuilder criteriaBuilder) {
      Predicate predicate = criteriaBuilder.conjunction();

      if (filter.getType() != null) {
         predicate = criteriaBuilder.and(predicate,
           criteriaBuilder.equal(root.get("status"), filter.getStatus()));
      }

      if (filter.getType() != null) {
         predicate = criteriaBuilder.and(predicate,
           criteriaBuilder.equal(root.get("type"), filter.getType()));
      }
      return predicate;
   }
}