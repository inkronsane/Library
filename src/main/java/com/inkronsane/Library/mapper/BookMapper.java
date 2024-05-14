package com.inkronsane.Library.mapper;


import com.inkronsane.Library.dto.*;
import com.inkronsane.Library.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookMapper {

   @Mapping(target = "id", ignore = true)
   Book mapToEntity(BookDto dto);

   BookDto mapToDto(Book entity);
}
