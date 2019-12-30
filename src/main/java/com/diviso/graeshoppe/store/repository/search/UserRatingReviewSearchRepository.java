package com.diviso.graeshoppe.store.repository.search;

import com.diviso.graeshoppe.store.domain.UserRatingReview;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link UserRatingReview} entity.
 */
public interface UserRatingReviewSearchRepository extends ElasticsearchRepository<UserRatingReview, Long> {
}
