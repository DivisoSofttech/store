package com.diviso.graeshoppe.store.service.mapper;

import com.diviso.graeshoppe.store.domain.*;
import com.diviso.graeshoppe.store.service.dto.StoreAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StoreAddress} and its DTO {@link StoreAddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoreAddressMapper extends EntityMapper<StoreAddressDTO, StoreAddress> {



    default StoreAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        StoreAddress storeAddress = new StoreAddress();
        storeAddress.setId(id);
        return storeAddress;
    }
}
