package com.diviso.graeshoppe.store.service.mapper;

import com.diviso.graeshoppe.store.domain.*;
import com.diviso.graeshoppe.store.service.dto.UniqueStoreIDDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UniqueStoreID} and its DTO {@link UniqueStoreIDDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UniqueStoreIDMapper extends EntityMapper<UniqueStoreIDDTO, UniqueStoreID> {



    default UniqueStoreID fromId(Long id) {
        if (id == null) {
            return null;
        }
        UniqueStoreID uniqueStoreID = new UniqueStoreID();
        uniqueStoreID.setId(id);
        return uniqueStoreID;
    }
}
