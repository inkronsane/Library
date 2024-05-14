package com.inkronsane.Library.repository;


import com.inkronsane.Library.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

   Optional<User> findByUsername(String username);
}
