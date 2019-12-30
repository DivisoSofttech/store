package com.diviso.graeshoppe.store.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link StoreAddressSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class StoreAddressSearchRepositoryMockConfiguration {

    @MockBean
    private StoreAddressSearchRepository mockStoreAddressSearchRepository;

}
