package com.diviso.graeshoppe.store.web.rest;

import com.diviso.graeshoppe.store.service.UserRatingReviewService;
import com.diviso.graeshoppe.store.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.store.service.dto.UserRatingReviewDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.diviso.graeshoppe.store.domain.UserRatingReview}.
 */
@RestController
@RequestMapping("/api")
public class UserRatingReviewResource {

    private final Logger log = LoggerFactory.getLogger(UserRatingReviewResource.class);

    private static final String ENTITY_NAME = "storeUserRatingReview";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserRatingReviewService userRatingReviewService;

    public UserRatingReviewResource(UserRatingReviewService userRatingReviewService) {
        this.userRatingReviewService = userRatingReviewService;
    }

    /**
     * {@code POST  /user-rating-reviews} : Create a new userRatingReview.
     *
     * @param userRatingReviewDTO the userRatingReviewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userRatingReviewDTO, or with status {@code 400 (Bad Request)} if the userRatingReview has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-rating-reviews")
    public ResponseEntity<UserRatingReviewDTO> createUserRatingReview(@RequestBody UserRatingReviewDTO userRatingReviewDTO) throws URISyntaxException {
        log.debug("REST request to save UserRatingReview : {}", userRatingReviewDTO);
        if (userRatingReviewDTO.getId() != null) {
            throw new BadRequestAlertException("A new userRatingReview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserRatingReviewDTO result = userRatingReviewService.save(userRatingReviewDTO);
        return ResponseEntity.created(new URI("/api/user-rating-reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-rating-reviews} : Updates an existing userRatingReview.
     *
     * @param userRatingReviewDTO the userRatingReviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRatingReviewDTO,
     * or with status {@code 400 (Bad Request)} if the userRatingReviewDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userRatingReviewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-rating-reviews")
    public ResponseEntity<UserRatingReviewDTO> updateUserRatingReview(@RequestBody UserRatingReviewDTO userRatingReviewDTO) throws URISyntaxException {
        log.debug("REST request to update UserRatingReview : {}", userRatingReviewDTO);
        if (userRatingReviewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserRatingReviewDTO result = userRatingReviewService.save(userRatingReviewDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userRatingReviewDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-rating-reviews} : get all the userRatingReviews.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userRatingReviews in body.
     */
    @GetMapping("/user-rating-reviews")
    public ResponseEntity<List<UserRatingReviewDTO>> getAllUserRatingReviews(Pageable pageable) {
        log.debug("REST request to get a page of UserRatingReviews");
        Page<UserRatingReviewDTO> page = userRatingReviewService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-rating-reviews/:id} : get the "id" userRatingReview.
     *
     * @param id the id of the userRatingReviewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userRatingReviewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-rating-reviews/{id}")
    public ResponseEntity<UserRatingReviewDTO> getUserRatingReview(@PathVariable Long id) {
        log.debug("REST request to get UserRatingReview : {}", id);
        Optional<UserRatingReviewDTO> userRatingReviewDTO = userRatingReviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userRatingReviewDTO);
    }

    /**
     * {@code DELETE  /user-rating-reviews/:id} : delete the "id" userRatingReview.
     *
     * @param id the id of the userRatingReviewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-rating-reviews/{id}")
    public ResponseEntity<Void> deleteUserRatingReview(@PathVariable Long id) {
        log.debug("REST request to delete UserRatingReview : {}", id);
        userRatingReviewService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/user-rating-reviews?query=:query} : search for the userRatingReview corresponding
     * to the query.
     *
     * @param query the query of the userRatingReview search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-rating-reviews")
    public ResponseEntity<List<UserRatingReviewDTO>> searchUserRatingReviews(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserRatingReviews for query {}", query);
        Page<UserRatingReviewDTO> page = userRatingReviewService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
