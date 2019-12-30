package com.diviso.graeshoppe.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.store.web.rest.TestUtil;

public class StoreSettingsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreSettings.class);
        StoreSettings storeSettings1 = new StoreSettings();
        storeSettings1.setId(1L);
        StoreSettings storeSettings2 = new StoreSettings();
        storeSettings2.setId(storeSettings1.getId());
        assertThat(storeSettings1).isEqualTo(storeSettings2);
        storeSettings2.setId(2L);
        assertThat(storeSettings1).isNotEqualTo(storeSettings2);
        storeSettings1.setId(null);
        assertThat(storeSettings1).isNotEqualTo(storeSettings2);
    }
}
