package com.diviso.graeshoppe.store.repository.search;

import com.diviso.graeshoppe.store.domain.Reply;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Reply} entity.
 */
public interface ReplySearchRepository extends ElasticsearchRepository<Reply, Long> {
}
