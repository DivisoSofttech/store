package com.diviso.graeshoppe.store.repository.search;

import com.diviso.graeshoppe.store.domain.UniqueStoreID;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link UniqueStoreID} entity.
 */
public interface UniqueStoreIDSearchRepository extends ElasticsearchRepository<UniqueStoreID, Long> {
}
