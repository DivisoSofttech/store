package com.diviso.graeshoppe.store.repository.search;

import com.diviso.graeshoppe.store.domain.search.StoreSuggestion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Store} entity.
 */
public interface StoreSuggestionSearchRepository extends ElasticsearchRepository<StoreSuggestion, Long> {
}
