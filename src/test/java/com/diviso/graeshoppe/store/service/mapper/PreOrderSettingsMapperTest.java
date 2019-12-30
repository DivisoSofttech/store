package com.diviso.graeshoppe.store.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PreOrderSettingsMapperTest {

    private PreOrderSettingsMapper preOrderSettingsMapper;

    @BeforeEach
    public void setUp() {
        preOrderSettingsMapper = new PreOrderSettingsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(preOrderSettingsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(preOrderSettingsMapper.fromId(null)).isNull();
    }
}
