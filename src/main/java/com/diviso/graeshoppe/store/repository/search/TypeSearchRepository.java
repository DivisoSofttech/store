package com.diviso.graeshoppe.store.repository.search;

import com.diviso.graeshoppe.store.domain.Type;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Type} entity.
 */
public interface TypeSearchRepository extends ElasticsearchRepository<Type, Long> {
}
