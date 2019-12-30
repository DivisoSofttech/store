package com.diviso.graeshoppe.store.repository.search;

import com.diviso.graeshoppe.store.domain.StoreSettings;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StoreSettings} entity.
 */
public interface StoreSettingsSearchRepository extends ElasticsearchRepository<StoreSettings, Long> {
}
