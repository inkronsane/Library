package com.inkronsane.Library.repository;


import com.inkronsane.Library.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface RequestRepository extends JpaRepository<Request, UUID>,
  JpaSpecificationExecutor<Request> {

}
