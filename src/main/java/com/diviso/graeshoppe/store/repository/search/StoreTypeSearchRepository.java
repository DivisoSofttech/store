package com.diviso.graeshoppe.store.repository.search;

import com.diviso.graeshoppe.store.domain.StoreType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StoreType} entity.
 */
public interface StoreTypeSearchRepository extends ElasticsearchRepository<StoreType, Long> {
}
