package com.diviso.graeshoppe.store.service.mapper;

import com.diviso.graeshoppe.store.domain.*;
import com.diviso.graeshoppe.store.service.dto.PreOrderSettingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PreOrderSettings} and its DTO {@link PreOrderSettingsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PreOrderSettingsMapper extends EntityMapper<PreOrderSettingsDTO, PreOrderSettings> {



    default PreOrderSettings fromId(Long id) {
        if (id == null) {
            return null;
        }
        PreOrderSettings preOrderSettings = new PreOrderSettings();
        preOrderSettings.setId(id);
        return preOrderSettings;
    }
}
