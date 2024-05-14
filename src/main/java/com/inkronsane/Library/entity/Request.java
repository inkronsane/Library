package com.inkronsane.Library.entity;


import jakarta.persistence.*;
import java.util.*;
import lombok.*;


@Data
@Entity
@Table(name = "requests")
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class Request extends AuditableEntity<UUID> implements Comparable<
  Request> {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;
   @Enumerated(EnumType.STRING)
   private RequestStatus status;
   @Enumerated(EnumType.STRING)
   private RequestType type;
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "author_id")
   private User author;
   private String content;

   @Override
   public int compareTo(Request o) {
      return id.compareTo(o.id);
   }
}