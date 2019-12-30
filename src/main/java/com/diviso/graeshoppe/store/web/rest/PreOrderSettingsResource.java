package com.diviso.graeshoppe.store.web.rest;

import com.diviso.graeshoppe.store.service.PreOrderSettingsService;
import com.diviso.graeshoppe.store.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.store.service.dto.PreOrderSettingsDTO;

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
 * REST controller for managing {@link com.diviso.graeshoppe.store.domain.PreOrderSettings}.
 */
@RestController
@RequestMapping("/api")
public class PreOrderSettingsResource {

    private final Logger log = LoggerFactory.getLogger(PreOrderSettingsResource.class);

    private static final String ENTITY_NAME = "storePreOrderSettings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreOrderSettingsService preOrderSettingsService;

    public PreOrderSettingsResource(PreOrderSettingsService preOrderSettingsService) {
        this.preOrderSettingsService = preOrderSettingsService;
    }

    /**
     * {@code POST  /pre-order-settings} : Create a new preOrderSettings.
     *
     * @param preOrderSettingsDTO the preOrderSettingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preOrderSettingsDTO, or with status {@code 400 (Bad Request)} if the preOrderSettings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pre-order-settings")
    public ResponseEntity<PreOrderSettingsDTO> createPreOrderSettings(@RequestBody PreOrderSettingsDTO preOrderSettingsDTO) throws URISyntaxException {
        log.debug("REST request to save PreOrderSettings : {}", preOrderSettingsDTO);
        if (preOrderSettingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new preOrderSettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreOrderSettingsDTO result = preOrderSettingsService.save(preOrderSettingsDTO);
        return ResponseEntity.created(new URI("/api/pre-order-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pre-order-settings} : Updates an existing preOrderSettings.
     *
     * @param preOrderSettingsDTO the preOrderSettingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preOrderSettingsDTO,
     * or with status {@code 400 (Bad Request)} if the preOrderSettingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preOrderSettingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pre-order-settings")
    public ResponseEntity<PreOrderSettingsDTO> updatePreOrderSettings(@RequestBody PreOrderSettingsDTO preOrderSettingsDTO) throws URISyntaxException {
        log.debug("REST request to update PreOrderSettings : {}", preOrderSettingsDTO);
        if (preOrderSettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PreOrderSettingsDTO result = preOrderSettingsService.save(preOrderSettingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preOrderSettingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pre-order-settings} : get all the preOrderSettings.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preOrderSettings in body.
     */
    @GetMapping("/pre-order-settings")
    public ResponseEntity<List<PreOrderSettingsDTO>> getAllPreOrderSettings(Pageable pageable) {
        log.debug("REST request to get a page of PreOrderSettings");
        Page<PreOrderSettingsDTO> page = preOrderSettingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pre-order-settings/:id} : get the "id" preOrderSettings.
     *
     * @param id the id of the preOrderSettingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preOrderSettingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pre-order-settings/{id}")
    public ResponseEntity<PreOrderSettingsDTO> getPreOrderSettings(@PathVariable Long id) {
        log.debug("REST request to get PreOrderSettings : {}", id);
        Optional<PreOrderSettingsDTO> preOrderSettingsDTO = preOrderSettingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preOrderSettingsDTO);
    }

    /**
     * {@code DELETE  /pre-order-settings/:id} : delete the "id" preOrderSettings.
     *
     * @param id the id of the preOrderSettingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pre-order-settings/{id}")
    public ResponseEntity<Void> deletePreOrderSettings(@PathVariable Long id) {
        log.debug("REST request to delete PreOrderSettings : {}", id);
        preOrderSettingsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/pre-order-settings?query=:query} : search for the preOrderSettings corresponding
     * to the query.
     *
     * @param query the query of the preOrderSettings search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/pre-order-settings")
    public ResponseEntity<List<PreOrderSettingsDTO>> searchPreOrderSettings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PreOrderSettings for query {}", query);
        Page<PreOrderSettingsDTO> page = preOrderSettingsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
