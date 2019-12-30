package com.diviso.graeshoppe.store.repository.search;

import com.diviso.graeshoppe.store.domain.DeliveryInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DeliveryInfo} entity.
 */
public interface DeliveryInfoSearchRepository extends ElasticsearchRepository<DeliveryInfo, Long> {
}
