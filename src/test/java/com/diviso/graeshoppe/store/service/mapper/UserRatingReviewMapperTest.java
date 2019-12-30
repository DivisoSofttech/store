package com.diviso.graeshoppe.store.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class UserRatingReviewMapperTest {

    private UserRatingReviewMapper userRatingReviewMapper;

    @BeforeEach
    public void setUp() {
        userRatingReviewMapper = new UserRatingReviewMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(userRatingReviewMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userRatingReviewMapper.fromId(null)).isNull();
    }
}
