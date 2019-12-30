package com.diviso.graeshoppe.store.web.rest;

import com.diviso.graeshoppe.store.service.StoreTypeService;
import com.diviso.graeshoppe.store.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.store.service.dto.StoreTypeDTO;

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
 * REST controller for managing {@link com.diviso.graeshoppe.store.domain.StoreType}.
 */
@RestController
@RequestMapping("/api")
public class StoreTypeResource {

    private final Logger log = LoggerFactory.getLogger(StoreTypeResource.class);

    private static final String ENTITY_NAME = "storeStoreType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StoreTypeService storeTypeService;

    public StoreTypeResource(StoreTypeService storeTypeService) {
        this.storeTypeService = storeTypeService;
    }

    /**
     * {@code POST  /store-types} : Create a new storeType.
     *
     * @param storeTypeDTO the storeTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storeTypeDTO, or with status {@code 400 (Bad Request)} if the storeType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/store-types")
    public ResponseEntity<StoreTypeDTO> createStoreType(@RequestBody StoreTypeDTO storeTypeDTO) throws URISyntaxException {
        log.debug("REST request to save StoreType : {}", storeTypeDTO);
        if (storeTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new storeType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StoreTypeDTO result = storeTypeService.save(storeTypeDTO);
        return ResponseEntity.created(new URI("/api/store-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /store-types} : Updates an existing storeType.
     *
     * @param storeTypeDTO the storeTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storeTypeDTO,
     * or with status {@code 400 (Bad Request)} if the storeTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the storeTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/store-types")
    public ResponseEntity<StoreTypeDTO> updateStoreType(@RequestBody StoreTypeDTO storeTypeDTO) throws URISyntaxException {
        log.debug("REST request to update StoreType : {}", storeTypeDTO);
        if (storeTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StoreTypeDTO result = storeTypeService.save(storeTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, storeTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /store-types} : get all the storeTypes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of storeTypes in body.
     */
    @GetMapping("/store-types")
    public ResponseEntity<List<StoreTypeDTO>> getAllStoreTypes(Pageable pageable) {
        log.debug("REST request to get a page of StoreTypes");
        Page<StoreTypeDTO> page = storeTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /store-types/:id} : get the "id" storeType.
     *
     * @param id the id of the storeTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storeTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/store-types/{id}")
    public ResponseEntity<StoreTypeDTO> getStoreType(@PathVariable Long id) {
        log.debug("REST request to get StoreType : {}", id);
        Optional<StoreTypeDTO> storeTypeDTO = storeTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(storeTypeDTO);
    }

    /**
     * {@code DELETE  /store-types/:id} : delete the "id" storeType.
     *
     * @param id the id of the storeTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/store-types/{id}")
    public ResponseEntity<Void> deleteStoreType(@PathVariable Long id) {
        log.debug("REST request to delete StoreType : {}", id);
        storeTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/store-types?query=:query} : search for the storeType corresponding
     * to the query.
     *
     * @param query the query of the storeType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/store-types")
    public ResponseEntity<List<StoreTypeDTO>> searchStoreTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StoreTypes for query {}", query);
        Page<StoreTypeDTO> page = storeTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
