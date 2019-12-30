package com.diviso.graeshoppe.store.web.rest;

import com.diviso.graeshoppe.store.service.TypeService;
import com.diviso.graeshoppe.store.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.store.service.dto.TypeDTO;

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
 * REST controller for managing {@link com.diviso.graeshoppe.store.domain.Type}.
 */
@RestController
@RequestMapping("/api")
public class TypeResource {

    private final Logger log = LoggerFactory.getLogger(TypeResource.class);

    private static final String ENTITY_NAME = "storeType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeService typeService;

    public TypeResource(TypeService typeService) {
        this.typeService = typeService;
    }

    /**
     * {@code POST  /types} : Create a new type.
     *
     * @param typeDTO the typeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeDTO, or with status {@code 400 (Bad Request)} if the type has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/types")
    public ResponseEntity<TypeDTO> createType(@RequestBody TypeDTO typeDTO) throws URISyntaxException {
        log.debug("REST request to save Type : {}", typeDTO);
        if (typeDTO.getId() != null) {
            throw new BadRequestAlertException("A new type cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeDTO result = typeService.save(typeDTO);
        return ResponseEntity.created(new URI("/api/types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /types} : Updates an existing type.
     *
     * @param typeDTO the typeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeDTO,
     * or with status {@code 400 (Bad Request)} if the typeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/types")
    public ResponseEntity<TypeDTO> updateType(@RequestBody TypeDTO typeDTO) throws URISyntaxException {
        log.debug("REST request to update Type : {}", typeDTO);
        if (typeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeDTO result = typeService.save(typeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /types} : get all the types.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of types in body.
     */
    @GetMapping("/types")
    public ResponseEntity<List<TypeDTO>> getAllTypes(Pageable pageable) {
        log.debug("REST request to get a page of Types");
        Page<TypeDTO> page = typeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /types/:id} : get the "id" type.
     *
     * @param id the id of the typeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/types/{id}")
    public ResponseEntity<TypeDTO> getType(@PathVariable Long id) {
        log.debug("REST request to get Type : {}", id);
        Optional<TypeDTO> typeDTO = typeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeDTO);
    }

    /**
     * {@code DELETE  /types/:id} : delete the "id" type.
     *
     * @param id the id of the typeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/types/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        log.debug("REST request to delete Type : {}", id);
        typeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/types?query=:query} : search for the type corresponding
     * to the query.
     *
     * @param query the query of the type search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/types")
    public ResponseEntity<List<TypeDTO>> searchTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Types for query {}", query);
        Page<TypeDTO> page = typeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
