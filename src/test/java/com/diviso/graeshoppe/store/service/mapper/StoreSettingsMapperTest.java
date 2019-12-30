package com.diviso.graeshoppe.store.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class StoreSettingsMapperTest {

    private StoreSettingsMapper storeSettingsMapper;

    @BeforeEach
    public void setUp() {
        storeSettingsMapper = new StoreSettingsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(storeSettingsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(storeSettingsMapper.fromId(null)).isNull();
    }
}
