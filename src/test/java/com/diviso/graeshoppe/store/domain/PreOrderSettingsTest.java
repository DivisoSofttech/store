package com.diviso.graeshoppe.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.store.web.rest.TestUtil;

public class PreOrderSettingsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreOrderSettings.class);
        PreOrderSettings preOrderSettings1 = new PreOrderSettings();
        preOrderSettings1.setId(1L);
        PreOrderSettings preOrderSettings2 = new PreOrderSettings();
        preOrderSettings2.setId(preOrderSettings1.getId());
        assertThat(preOrderSettings1).isEqualTo(preOrderSettings2);
        preOrderSettings2.setId(2L);
        assertThat(preOrderSettings1).isNotEqualTo(preOrderSettings2);
        preOrderSettings1.setId(null);
        assertThat(preOrderSettings1).isNotEqualTo(preOrderSettings2);
    }
}
