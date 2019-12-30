package com.diviso.graeshoppe.store.service.mapper;

import com.diviso.graeshoppe.store.domain.*;
import com.diviso.graeshoppe.store.service.dto.StoreSettingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StoreSettings} and its DTO {@link StoreSettingsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoreSettingsMapper extends EntityMapper<StoreSettingsDTO, StoreSettings> {



    default StoreSettings fromId(Long id) {
        if (id == null) {
            return null;
        }
        StoreSettings storeSettings = new StoreSettings();
        storeSettings.setId(id);
        return storeSettings;
    }
}
