package com.diviso.graeshoppe.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.store.web.rest.TestUtil;

public class StoreAddressTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreAddress.class);
        StoreAddress storeAddress1 = new StoreAddress();
        storeAddress1.setId(1L);
        StoreAddress storeAddress2 = new StoreAddress();
        storeAddress2.setId(storeAddress1.getId());
        assertThat(storeAddress1).isEqualTo(storeAddress2);
        storeAddress2.setId(2L);
        assertThat(storeAddress1).isNotEqualTo(storeAddress2);
        storeAddress1.setId(null);
        assertThat(storeAddress1).isNotEqualTo(storeAddress2);
    }
}
