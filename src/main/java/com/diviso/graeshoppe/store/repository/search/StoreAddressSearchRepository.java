package com.diviso.graeshoppe.store.repository.search;

import com.diviso.graeshoppe.store.domain.StoreAddress;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StoreAddress} entity.
 */
public interface StoreAddressSearchRepository extends ElasticsearchRepository<StoreAddress, Long> {
}
