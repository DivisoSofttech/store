package com.diviso.graeshoppe.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.store.web.rest.TestUtil;

public class UniqueStoreIDTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UniqueStoreID.class);
        UniqueStoreID uniqueStoreID1 = new UniqueStoreID();
        uniqueStoreID1.setId(1L);
        UniqueStoreID uniqueStoreID2 = new UniqueStoreID();
        uniqueStoreID2.setId(uniqueStoreID1.getId());
        assertThat(uniqueStoreID1).isEqualTo(uniqueStoreID2);
        uniqueStoreID2.setId(2L);
        assertThat(uniqueStoreID1).isNotEqualTo(uniqueStoreID2);
        uniqueStoreID1.setId(null);
        assertThat(uniqueStoreID1).isNotEqualTo(uniqueStoreID2);
    }
}
