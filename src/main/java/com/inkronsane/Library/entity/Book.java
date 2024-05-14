package com.inkronsane.Library.entity;


import jakarta.persistence.*;
import java.time.*;
import java.util.*;
import lombok.*;

@Data
@Entity
@Table(name = "books")
@EqualsAndHashCode(of = "name")
@AllArgsConstructor
@NoArgsConstructor
public class Book implements BaseEntity<UUID>, Comparable<Book> {

   LocalDate publicationDate;
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;
   private String name;
   private String author;
   private Long amount;

   @Override
   public int compareTo(Book o) {
      return id.compareTo(o.id);
   }
}
