package com.inkronsane.Library.entity;


import jakarta.persistence.*;
import java.time.*;
import java.util.*;
import lombok.*;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class User extends AuditableEntity<UUID> implements Comparable<User> {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;
   @Column(unique = true)
   private String username;

   @Column(unique = true)
   private String email;

   private String password;
   @Enumerated(EnumType.STRING)
   private Role role;
   private String firstName;
   private String lastName;
   private LocalDate birthDate;
   private String info;


   @Override
   public int compareTo(User o) {
      return id.compareTo(o.id);
   }
}
