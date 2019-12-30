package com.diviso.graeshoppe.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.store.web.rest.TestUtil;

public class StoreTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreType.class);
        StoreType storeType1 = new StoreType();
        storeType1.setId(1L);
        StoreType storeType2 = new StoreType();
        storeType2.setId(storeType1.getId());
        assertThat(storeType1).isEqualTo(storeType2);
        storeType2.setId(2L);
        assertThat(storeType1).isNotEqualTo(storeType2);
        storeType1.setId(null);
        assertThat(storeType1).isNotEqualTo(storeType2);
    }
}
