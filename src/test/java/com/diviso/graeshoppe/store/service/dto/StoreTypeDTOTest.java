package com.diviso.graeshoppe.store.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.store.web.rest.TestUtil;

public class StoreTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreTypeDTO.class);
        StoreTypeDTO storeTypeDTO1 = new StoreTypeDTO();
        storeTypeDTO1.setId(1L);
        StoreTypeDTO storeTypeDTO2 = new StoreTypeDTO();
        assertThat(storeTypeDTO1).isNotEqualTo(storeTypeDTO2);
        storeTypeDTO2.setId(storeTypeDTO1.getId());
        assertThat(storeTypeDTO1).isEqualTo(storeTypeDTO2);
        storeTypeDTO2.setId(2L);
        assertThat(storeTypeDTO1).isNotEqualTo(storeTypeDTO2);
        storeTypeDTO1.setId(null);
        assertThat(storeTypeDTO1).isNotEqualTo(storeTypeDTO2);
    }
}
