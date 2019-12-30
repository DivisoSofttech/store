package com.diviso.graeshoppe.store.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.store.web.rest.TestUtil;

public class UniqueStoreIDDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UniqueStoreIDDTO.class);
        UniqueStoreIDDTO uniqueStoreIDDTO1 = new UniqueStoreIDDTO();
        uniqueStoreIDDTO1.setId(1L);
        UniqueStoreIDDTO uniqueStoreIDDTO2 = new UniqueStoreIDDTO();
        assertThat(uniqueStoreIDDTO1).isNotEqualTo(uniqueStoreIDDTO2);
        uniqueStoreIDDTO2.setId(uniqueStoreIDDTO1.getId());
        assertThat(uniqueStoreIDDTO1).isEqualTo(uniqueStoreIDDTO2);
        uniqueStoreIDDTO2.setId(2L);
        assertThat(uniqueStoreIDDTO1).isNotEqualTo(uniqueStoreIDDTO2);
        uniqueStoreIDDTO1.setId(null);
        assertThat(uniqueStoreIDDTO1).isNotEqualTo(uniqueStoreIDDTO2);
    }
}
