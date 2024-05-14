package com.inkronsane.Library.mapper;


import com.inkronsane.Library.dto.*;
import com.inkronsane.Library.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RequestMapper {

   @Mapping(target = "id", ignore = true)
   @Mapping(source = "type", target = "type", qualifiedByName = "convertStringToType")
   @Mapping(source = "status", target = "status", qualifiedByName = "convertStringToStatus")
   Request mapToEntity(RequestDto dto);

   @Mapping(source = "type", target = "type", qualifiedByName = "convertTypeToString")
   @Mapping(source = "status", target = "status", qualifiedByName = "convertStatusToString")
   RequestDto mapToDto(Request entity);

   @Named("convertStringToStatus")
   default RequestStatus convertStringToStatus(String status) {
      return RequestStatus.valueOf(status);
   }

   @Named("convertStatusToString")
   default String convertStatusToString(RequestStatus status) {
      return status.name();
   }

   @Named("convertStringToType")
   default RequestType convertStringToType(String type) {
      return RequestType.valueOf(type);
   }

   @Named("convertTypeToString")
   default String convertTypeToString(RequestType type) {
      return type.name();
   }
}
