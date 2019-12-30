package com.diviso.graeshoppe.store.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.store.web.rest.TestUtil;

public class StoreSettingsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreSettingsDTO.class);
        StoreSettingsDTO storeSettingsDTO1 = new StoreSettingsDTO();
        storeSettingsDTO1.setId(1L);
        StoreSettingsDTO storeSettingsDTO2 = new StoreSettingsDTO();
        assertThat(storeSettingsDTO1).isNotEqualTo(storeSettingsDTO2);
        storeSettingsDTO2.setId(storeSettingsDTO1.getId());
        assertThat(storeSettingsDTO1).isEqualTo(storeSettingsDTO2);
        storeSettingsDTO2.setId(2L);
        assertThat(storeSettingsDTO1).isNotEqualTo(storeSettingsDTO2);
        storeSettingsDTO1.setId(null);
        assertThat(storeSettingsDTO1).isNotEqualTo(storeSettingsDTO2);
    }
}
