package com.diviso.graeshoppe.store.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link StoreTypeSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class StoreTypeSearchRepositoryMockConfiguration {

    @MockBean
    private StoreTypeSearchRepository mockStoreTypeSearchRepository;

}
