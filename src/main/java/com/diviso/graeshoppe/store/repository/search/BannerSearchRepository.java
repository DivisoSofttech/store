package com.diviso.graeshoppe.store.repository.search;

import com.diviso.graeshoppe.store.domain.Banner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Banner} entity.
 */
public interface BannerSearchRepository extends ElasticsearchRepository<Banner, Long> {
}
