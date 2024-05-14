package com.inkronsane.Library.mapper;


import com.inkronsane.Library.dto.*;
import com.inkronsane.Library.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

   @Mapping(target = "id", ignore = true)
   @Mapping(source = "role", target = "role", qualifiedByName = "convertStringToRole")
   User mapToEntity(UserDto dto);

   @Mapping(source = "role", target = "role", qualifiedByName = "convertRoleToString")
   UserDto mapToDto(User entity);

   @Named("convertStringToRole")
   default Role convertStringToRole(String role) {
      return Role.valueOf(role);
   }

   @Named("convertRoleToString")
   default String convertRoleToString(Role role) {
      return role.name();
   }
}
