package com.diviso.graeshoppe.store.web.rest;

import com.diviso.graeshoppe.store.service.StoreAddressService;
import com.diviso.graeshoppe.store.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.store.service.dto.StoreAddressDTO;

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
 * REST controller for managing {@link com.diviso.graeshoppe.store.domain.StoreAddress}.
 */
@RestController
@RequestMapping("/api")
public class StoreAddressResource {

    private final Logger log = LoggerFactory.getLogger(StoreAddressResource.class);

    private static final String ENTITY_NAME = "storeStoreAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StoreAddressService storeAddressService;

    public StoreAddressResource(StoreAddressService storeAddressService) {
        this.storeAddressService = storeAddressService;
    }

    /**
     * {@code POST  /store-addresses} : Create a new storeAddress.
     *
     * @param storeAddressDTO the storeAddressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storeAddressDTO, or with status {@code 400 (Bad Request)} if the storeAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/store-addresses")
    public ResponseEntity<StoreAddressDTO> createStoreAddress(@RequestBody StoreAddressDTO storeAddressDTO) throws URISyntaxException {
        log.debug("REST request to save StoreAddress : {}", storeAddressDTO);
        if (storeAddressDTO.getId() != null) {
            throw new BadRequestAlertException("A new storeAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StoreAddressDTO result = storeAddressService.save(storeAddressDTO);
        return ResponseEntity.created(new URI("/api/store-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /store-addresses} : Updates an existing storeAddress.
     *
     * @param storeAddressDTO the storeAddressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storeAddressDTO,
     * or with status {@code 400 (Bad Request)} if the storeAddressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the storeAddressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/store-addresses")
    public ResponseEntity<StoreAddressDTO> updateStoreAddress(@RequestBody StoreAddressDTO storeAddressDTO) throws URISyntaxException {
        log.debug("REST request to update StoreAddress : {}", storeAddressDTO);
        if (storeAddressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StoreAddressDTO result = storeAddressService.save(storeAddressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, storeAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /store-addresses} : get all the storeAddresses.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of storeAddresses in body.
     */
    @GetMapping("/store-addresses")
    public ResponseEntity<List<StoreAddressDTO>> getAllStoreAddresses(Pageable pageable) {
        log.debug("REST request to get a page of StoreAddresses");
        Page<StoreAddressDTO> page = storeAddressService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /store-addresses/:id} : get the "id" storeAddress.
     *
     * @param id the id of the storeAddressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storeAddressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/store-addresses/{id}")
    public ResponseEntity<StoreAddressDTO> getStoreAddress(@PathVariable Long id) {
        log.debug("REST request to get StoreAddress : {}", id);
        Optional<StoreAddressDTO> storeAddressDTO = storeAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(storeAddressDTO);
    }

    /**
     * {@code DELETE  /store-addresses/:id} : delete the "id" storeAddress.
     *
     * @param id the id of the storeAddressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/store-addresses/{id}")
    public ResponseEntity<Void> deleteStoreAddress(@PathVariable Long id) {
        log.debug("REST request to delete StoreAddress : {}", id);
        storeAddressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/store-addresses?query=:query} : search for the storeAddress corresponding
     * to the query.
     *
     * @param query the query of the storeAddress search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/store-addresses")
    public ResponseEntity<List<StoreAddressDTO>> searchStoreAddresses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StoreAddresses for query {}", query);
        Page<StoreAddressDTO> page = storeAddressService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
