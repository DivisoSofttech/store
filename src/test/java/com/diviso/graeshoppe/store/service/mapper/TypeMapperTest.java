package com.diviso.graeshoppe.store.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TypeMapperTest {

    private TypeMapper typeMapper;

    @BeforeEach
    public void setUp() {
        typeMapper = new TypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(typeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(typeMapper.fromId(null)).isNull();
    }
}
