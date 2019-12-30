package com.diviso.graeshoppe.store.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class UniqueStoreIDMapperTest {

    private UniqueStoreIDMapper uniqueStoreIDMapper;

    @BeforeEach
    public void setUp() {
        uniqueStoreIDMapper = new UniqueStoreIDMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(uniqueStoreIDMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(uniqueStoreIDMapper.fromId(null)).isNull();
    }
}
