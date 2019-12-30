package com.diviso.graeshoppe.store.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.store.web.rest.TestUtil;

public class UserRatingReviewDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserRatingReviewDTO.class);
        UserRatingReviewDTO userRatingReviewDTO1 = new UserRatingReviewDTO();
        userRatingReviewDTO1.setId(1L);
        UserRatingReviewDTO userRatingReviewDTO2 = new UserRatingReviewDTO();
        assertThat(userRatingReviewDTO1).isNotEqualTo(userRatingReviewDTO2);
        userRatingReviewDTO2.setId(userRatingReviewDTO1.getId());
        assertThat(userRatingReviewDTO1).isEqualTo(userRatingReviewDTO2);
        userRatingReviewDTO2.setId(2L);
        assertThat(userRatingReviewDTO1).isNotEqualTo(userRatingReviewDTO2);
        userRatingReviewDTO1.setId(null);
        assertThat(userRatingReviewDTO1).isNotEqualTo(userRatingReviewDTO2);
    }
}
