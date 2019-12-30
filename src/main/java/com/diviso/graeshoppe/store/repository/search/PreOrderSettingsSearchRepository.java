package com.diviso.graeshoppe.store.repository.search;

import com.diviso.graeshoppe.store.domain.PreOrderSettings;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PreOrderSettings} entity.
 */
public interface PreOrderSettingsSearchRepository extends ElasticsearchRepository<PreOrderSettings, Long> {
}
