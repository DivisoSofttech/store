package com.diviso.graeshoppe.store.web.rest;

import com.diviso.graeshoppe.store.service.DeliveryInfoService;
import com.diviso.graeshoppe.store.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.store.service.dto.DeliveryInfoDTO;

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
 * REST controller for managing {@link com.diviso.graeshoppe.store.domain.DeliveryInfo}.
 */
@RestController
@RequestMapping("/api")
public class DeliveryInfoResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryInfoResource.class);

    private static final String ENTITY_NAME = "storeDeliveryInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeliveryInfoService deliveryInfoService;

    public DeliveryInfoResource(DeliveryInfoService deliveryInfoService) {
        this.deliveryInfoService = deliveryInfoService;
    }

    /**
     * {@code POST  /delivery-infos} : Create a new deliveryInfo.
     *
     * @param deliveryInfoDTO the deliveryInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deliveryInfoDTO, or with status {@code 400 (Bad Request)} if the deliveryInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delivery-infos")
    public ResponseEntity<DeliveryInfoDTO> createDeliveryInfo(@RequestBody DeliveryInfoDTO deliveryInfoDTO) throws URISyntaxException {
        log.debug("REST request to save DeliveryInfo : {}", deliveryInfoDTO);
        if (deliveryInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new deliveryInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryInfoDTO result = deliveryInfoService.save(deliveryInfoDTO);
        return ResponseEntity.created(new URI("/api/delivery-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delivery-infos} : Updates an existing deliveryInfo.
     *
     * @param deliveryInfoDTO the deliveryInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deliveryInfoDTO,
     * or with status {@code 400 (Bad Request)} if the deliveryInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deliveryInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delivery-infos")
    public ResponseEntity<DeliveryInfoDTO> updateDeliveryInfo(@RequestBody DeliveryInfoDTO deliveryInfoDTO) throws URISyntaxException {
        log.debug("REST request to update DeliveryInfo : {}", deliveryInfoDTO);
        if (deliveryInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeliveryInfoDTO result = deliveryInfoService.save(deliveryInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deliveryInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /delivery-infos} : get all the deliveryInfos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deliveryInfos in body.
     */
    @GetMapping("/delivery-infos")
    public ResponseEntity<List<DeliveryInfoDTO>> getAllDeliveryInfos(Pageable pageable) {
        log.debug("REST request to get a page of DeliveryInfos");
        Page<DeliveryInfoDTO> page = deliveryInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /delivery-infos/:id} : get the "id" deliveryInfo.
     *
     * @param id the id of the deliveryInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deliveryInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delivery-infos/{id}")
    public ResponseEntity<DeliveryInfoDTO> getDeliveryInfo(@PathVariable Long id) {
        log.debug("REST request to get DeliveryInfo : {}", id);
        Optional<DeliveryInfoDTO> deliveryInfoDTO = deliveryInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deliveryInfoDTO);
    }

    /**
     * {@code DELETE  /delivery-infos/:id} : delete the "id" deliveryInfo.
     *
     * @param id the id of the deliveryInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delivery-infos/{id}")
    public ResponseEntity<Void> deleteDeliveryInfo(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryInfo : {}", id);
        deliveryInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/delivery-infos?query=:query} : search for the deliveryInfo corresponding
     * to the query.
     *
     * @param query the query of the deliveryInfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/delivery-infos")
    public ResponseEntity<List<DeliveryInfoDTO>> searchDeliveryInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DeliveryInfos for query {}", query);
        Page<DeliveryInfoDTO> page = deliveryInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
