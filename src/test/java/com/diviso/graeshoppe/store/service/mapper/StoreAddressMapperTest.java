package com.diviso.graeshoppe.store.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class StoreAddressMapperTest {

    private StoreAddressMapper storeAddressMapper;

    @BeforeEach
    public void setUp() {
        storeAddressMapper = new StoreAddressMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(storeAddressMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(storeAddressMapper.fromId(null)).isNull();
    }
}
