package com.diviso.graeshoppe.store.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.store.web.rest.TestUtil;

public class TypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeDTO.class);
        TypeDTO typeDTO1 = new TypeDTO();
        typeDTO1.setId(1L);
        TypeDTO typeDTO2 = new TypeDTO();
        assertThat(typeDTO1).isNotEqualTo(typeDTO2);
        typeDTO2.setId(typeDTO1.getId());
        assertThat(typeDTO1).isEqualTo(typeDTO2);
        typeDTO2.setId(2L);
        assertThat(typeDTO1).isNotEqualTo(typeDTO2);
        typeDTO1.setId(null);
        assertThat(typeDTO1).isNotEqualTo(typeDTO2);
    }
}
