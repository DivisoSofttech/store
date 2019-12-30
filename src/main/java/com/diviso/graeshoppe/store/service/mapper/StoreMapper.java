package com.diviso.graeshoppe.store.service.mapper;

import com.diviso.graeshoppe.store.domain.*;
import com.diviso.graeshoppe.store.service.dto.StoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Store} and its DTO {@link StoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {StoreAddressMapper.class, StoreSettingsMapper.class, PreOrderSettingsMapper.class})
public interface StoreMapper extends EntityMapper<StoreDTO, Store> {

    @Mapping(source = "storeAddress.id", target = "storeAddressId")
    @Mapping(source = "storeSettings.id", target = "storeSettingsId")
    @Mapping(source = "preOrderSettings.id", target = "preOrderSettingsId")
    StoreDTO toDto(Store store);

    @Mapping(source = "storeAddressId", target = "storeAddress")
    @Mapping(source = "storeSettingsId", target = "storeSettings")
    @Mapping(source = "preOrderSettingsId", target = "preOrderSettings")
    @Mapping(target = "storeTypes", ignore = true)
    @Mapping(target = "removeStoreType", ignore = true)
    @Mapping(target = "banners", ignore = true)
    @Mapping(target = "removeBanner", ignore = true)
    @Mapping(target = "deliveryInfos", ignore = true)
    @Mapping(target = "removeDeliveryInfo", ignore = true)
    @Mapping(target = "userRatingReviews", ignore = true)
    @Mapping(target = "removeUserRatingReview", ignore = true)
    Store toEntity(StoreDTO storeDTO);

    default Store fromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
