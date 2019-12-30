package com.diviso.graeshoppe.store.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.store.web.rest.TestUtil;

public class PreOrderSettingsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreOrderSettingsDTO.class);
        PreOrderSettingsDTO preOrderSettingsDTO1 = new PreOrderSettingsDTO();
        preOrderSettingsDTO1.setId(1L);
        PreOrderSettingsDTO preOrderSettingsDTO2 = new PreOrderSettingsDTO();
        assertThat(preOrderSettingsDTO1).isNotEqualTo(preOrderSettingsDTO2);
        preOrderSettingsDTO2.setId(preOrderSettingsDTO1.getId());
        assertThat(preOrderSettingsDTO1).isEqualTo(preOrderSettingsDTO2);
        preOrderSettingsDTO2.setId(2L);
        assertThat(preOrderSettingsDTO1).isNotEqualTo(preOrderSettingsDTO2);
        preOrderSettingsDTO1.setId(null);
        assertThat(preOrderSettingsDTO1).isNotEqualTo(preOrderSettingsDTO2);
    }
}
