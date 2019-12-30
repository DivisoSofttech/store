package com.diviso.graeshoppe.store.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.store.web.rest.TestUtil;

public class ReplyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReplyDTO.class);
        ReplyDTO replyDTO1 = new ReplyDTO();
        replyDTO1.setId(1L);
        ReplyDTO replyDTO2 = new ReplyDTO();
        assertThat(replyDTO1).isNotEqualTo(replyDTO2);
        replyDTO2.setId(replyDTO1.getId());
        assertThat(replyDTO1).isEqualTo(replyDTO2);
        replyDTO2.setId(2L);
        assertThat(replyDTO1).isNotEqualTo(replyDTO2);
        replyDTO1.setId(null);
        assertThat(replyDTO1).isNotEqualTo(replyDTO2);
    }
}
