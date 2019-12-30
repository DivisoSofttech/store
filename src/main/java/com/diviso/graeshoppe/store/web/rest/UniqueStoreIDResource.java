package com.diviso.graeshoppe.store.web.rest;

import com.diviso.graeshoppe.store.service.UniqueStoreIDService;
import com.diviso.graeshoppe.store.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.store.service.dto.UniqueStoreIDDTO;

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
 * REST controller for managing {@link com.diviso.graeshoppe.store.domain.UniqueStoreID}.
 */
@RestController
@RequestMapping("/api")
public class UniqueStoreIDResource {

    private final Logger log = LoggerFactory.getLogger(UniqueStoreIDResource.class);

    private static final String ENTITY_NAME = "storeUniqueStoreId";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UniqueStoreIDService uniqueStoreIDService;

    public UniqueStoreIDResource(UniqueStoreIDService uniqueStoreIDService) {
        this.uniqueStoreIDService = uniqueStoreIDService;
    }

    /**
     * {@code POST  /unique-store-ids} : Create a new uniqueStoreID.
     *
     * @param uniqueStoreIDDTO the uniqueStoreIDDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uniqueStoreIDDTO, or with status {@code 400 (Bad Request)} if the uniqueStoreID has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unique-store-ids")
    public ResponseEntity<UniqueStoreIDDTO> createUniqueStoreID(@RequestBody UniqueStoreIDDTO uniqueStoreIDDTO) throws URISyntaxException {
        log.debug("REST request to save UniqueStoreID : {}", uniqueStoreIDDTO);
        if (uniqueStoreIDDTO.getId() != null) {
            throw new BadRequestAlertException("A new uniqueStoreID cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UniqueStoreIDDTO result = uniqueStoreIDService.save(uniqueStoreIDDTO);
        return ResponseEntity.created(new URI("/api/unique-store-ids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unique-store-ids} : Updates an existing uniqueStoreID.
     *
     * @param uniqueStoreIDDTO the uniqueStoreIDDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uniqueStoreIDDTO,
     * or with status {@code 400 (Bad Request)} if the uniqueStoreIDDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uniqueStoreIDDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unique-store-ids")
    public ResponseEntity<UniqueStoreIDDTO> updateUniqueStoreID(@RequestBody UniqueStoreIDDTO uniqueStoreIDDTO) throws URISyntaxException {
        log.debug("REST request to update UniqueStoreID : {}", uniqueStoreIDDTO);
        if (uniqueStoreIDDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UniqueStoreIDDTO result = uniqueStoreIDService.save(uniqueStoreIDDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uniqueStoreIDDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unique-store-ids} : get all the uniqueStoreIDS.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uniqueStoreIDS in body.
     */
    @GetMapping("/unique-store-ids")
    public ResponseEntity<List<UniqueStoreIDDTO>> getAllUniqueStoreIDS(Pageable pageable) {
        log.debug("REST request to get a page of UniqueStoreIDS");
        Page<UniqueStoreIDDTO> page = uniqueStoreIDService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /unique-store-ids/:id} : get the "id" uniqueStoreID.
     *
     * @param id the id of the uniqueStoreIDDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uniqueStoreIDDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unique-store-ids/{id}")
    public ResponseEntity<UniqueStoreIDDTO> getUniqueStoreID(@PathVariable Long id) {
        log.debug("REST request to get UniqueStoreID : {}", id);
        Optional<UniqueStoreIDDTO> uniqueStoreIDDTO = uniqueStoreIDService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uniqueStoreIDDTO);
    }

    /**
     * {@code DELETE  /unique-store-ids/:id} : delete the "id" uniqueStoreID.
     *
     * @param id the id of the uniqueStoreIDDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unique-store-ids/{id}")
    public ResponseEntity<Void> deleteUniqueStoreID(@PathVariable Long id) {
        log.debug("REST request to delete UniqueStoreID : {}", id);
        uniqueStoreIDService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/unique-store-ids?query=:query} : search for the uniqueStoreID corresponding
     * to the query.
     *
     * @param query the query of the uniqueStoreID search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/unique-store-ids")
    public ResponseEntity<List<UniqueStoreIDDTO>> searchUniqueStoreIDS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UniqueStoreIDS for query {}", query);
        Page<UniqueStoreIDDTO> page = uniqueStoreIDService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
