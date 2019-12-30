package com.diviso.graeshoppe.store.service.mapper;

import com.diviso.graeshoppe.store.domain.*;
import com.diviso.graeshoppe.store.service.dto.TypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Type} and its DTO {@link TypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeMapper extends EntityMapper<TypeDTO, Type> {



    default Type fromId(Long id) {
        if (id == null) {
            return null;
        }
        Type type = new Type();
        type.setId(id);
        return type;
    }
}
