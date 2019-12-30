package com.diviso.graeshoppe.store.service.mapper;

import com.diviso.graeshoppe.store.domain.*;
import com.diviso.graeshoppe.store.service.dto.DeliveryInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeliveryInfo} and its DTO {@link DeliveryInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {TypeMapper.class, StoreMapper.class})
public interface DeliveryInfoMapper extends EntityMapper<DeliveryInfoDTO, DeliveryInfo> {

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "store.id", target = "storeId")
    DeliveryInfoDTO toDto(DeliveryInfo deliveryInfo);

    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "storeId", target = "store")
    DeliveryInfo toEntity(DeliveryInfoDTO deliveryInfoDTO);

    default DeliveryInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeliveryInfo deliveryInfo = new DeliveryInfo();
        deliveryInfo.setId(id);
        return deliveryInfo;
    }
}
