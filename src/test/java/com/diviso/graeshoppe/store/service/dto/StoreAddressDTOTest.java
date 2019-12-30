package com.diviso.graeshoppe.store.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.store.web.rest.TestUtil;

public class StoreAddressDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreAddressDTO.class);
        StoreAddressDTO storeAddressDTO1 = new StoreAddressDTO();
        storeAddressDTO1.setId(1L);
        StoreAddressDTO storeAddressDTO2 = new StoreAddressDTO();
        assertThat(storeAddressDTO1).isNotEqualTo(storeAddressDTO2);
        storeAddressDTO2.setId(storeAddressDTO1.getId());
        assertThat(storeAddressDTO1).isEqualTo(storeAddressDTO2);
        storeAddressDTO2.setId(2L);
        assertThat(storeAddressDTO1).isNotEqualTo(storeAddressDTO2);
        storeAddressDTO1.setId(null);
        assertThat(storeAddressDTO1).isNotEqualTo(storeAddressDTO2);
    }
}
