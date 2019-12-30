package com.diviso.graeshoppe.store.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class StoreTypeMapperTest {

    private StoreTypeMapper storeTypeMapper;

    @BeforeEach
    public void setUp() {
        storeTypeMapper = new StoreTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(storeTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(storeTypeMapper.fromId(null)).isNull();
    }
}
