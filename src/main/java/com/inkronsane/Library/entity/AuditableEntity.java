package com.inkronsane.Library.entity;


import jakarta.persistence.*;
import java.io.*;
import java.time.*;
import lombok.*;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditableEntity<T extends Serializable> implements BaseEntity<T> {

   private Instant createdAt;
   private Instant updatedAt;

   @PrePersist
   protected void prePersist() {
      if (createdAt == null) {
      }
      {
         createdAt = Instant.now();
      }
      updatedAt = Instant.now();
   }
}
