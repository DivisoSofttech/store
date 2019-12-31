package com.diviso.graeshoppe.store.repository.search;

import com.diviso.graeshoppe.store.domain.search.Test;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Store} entity.
 */
public interface TestSearchRepository extends ElasticsearchRepository<Test, Long> {
}
