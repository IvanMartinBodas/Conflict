package com.example.accessing_data_jpa.mapper;

import com.example.accessing_data_jpa.dto.ConflictDto;
import com.example.accessing_data_jpa.model.Conflict;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConflictMapper {
    ConflictDto toDTO(Conflict c);
    List<ConflictDto> toDTO(List<Conflict> conflicts);
    Conflict toEntity(ConflictDto dto);
}