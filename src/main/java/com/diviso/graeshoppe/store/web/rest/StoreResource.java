package com.diviso.graeshoppe.store.web.rest;
import org.springframework.data.elasticsearch.core.completion.Completion;
//import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import com.diviso.graeshoppe.store.domain.search.Test;
import com.diviso.graeshoppe.store.service.StoreService;
import com.diviso.graeshoppe.store.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.store.service.dto.StoreDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;
import com.diviso.graeshoppe.store.repository.search.TestSearchRepository;
/**
 * REST controller for managing {@link com.diviso.graeshoppe.store.domain.Store}.
 */
@RestController
@RequestMapping("/api")
public class StoreResource {
	@Autowired
	TestSearchRepository testSearchRepository;
    private final Logger log = LoggerFactory.getLogger(StoreResource.class);

    private static final String ENTITY_NAME = "storeStore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StoreService storeService;

    public StoreResource(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/test")
    public Test createTest(/*@RequestBody Test test */) {
    	Test test = new Test();
    	String[] in= new String[]{"rafeek"};
    	Completion c1= new Completion(in);
    	test.setId(20l);
    	test.setName("rafeek");
    	test.setSuggest(c1);
    	System.out.println("qqqqqqqqqqqqqqqqqqqqqqqq"+test.getName());
    return	 testSearchRepository.save(test);
    	 
    	
    	
    	/*Test test2 = new Test();
    	String[] input2= {"rafeek","karthi"};
    	Completion c2= new Completion(input2);
    	test.setName("rafeek");
    	test.setSuggest(c2);
    	return testSearchRepository.save(test2);*/
    }
    
    
    
    
    
    /**
     * {@code POST  /stores} : Create a new store.
     *
     * @param storeDTO the storeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storeDTO, or with status {@code 400 (Bad Request)} if the store has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stores")
    public ResponseEntity<StoreDTO> createStore(@Valid @RequestBody StoreDTO storeDTO) throws URISyntaxException {
        log.debug("REST request to save Store : {}", storeDTO);
        if (storeDTO.getId() != null) {
            throw new BadRequestAlertException("A new store cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StoreDTO result = storeService.save(storeDTO);
        return ResponseEntity.created(new URI("/api/stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stores} : Updates an existing store.
     *
     * @param storeDTO the storeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storeDTO,
     * or with status {@code 400 (Bad Request)} if the storeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the storeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stores")
    public ResponseEntity<StoreDTO> updateStore(@Valid @RequestBody StoreDTO storeDTO) throws URISyntaxException {
        log.debug("REST request to update Store : {}", storeDTO);
        if (storeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StoreDTO result = storeService.save(storeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, storeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stores} : get all the stores.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stores in body.
     */
    @GetMapping("/stores")
    public ResponseEntity<List<StoreDTO>> getAllStores(Pageable pageable) {
        log.debug("REST request to get a page of Stores");
        Page<StoreDTO> page = storeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stores/:id} : get the "id" store.
     *
     * @param id the id of the storeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stores/{id}")
    public ResponseEntity<StoreDTO> getStore(@PathVariable Long id) {
        log.debug("REST request to get Store : {}", id);
        Optional<StoreDTO> storeDTO = storeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(storeDTO);
    }

    /**
     * {@code DELETE  /stores/:id} : delete the "id" store.
     *
     * @param id the id of the storeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stores/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        log.debug("REST request to delete Store : {}", id);
        storeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/stores?query=:query} : search for the store corresponding
     * to the query.
     *
     * @param query the query of the store search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/stores")
    public ResponseEntity<List<StoreDTO>> searchStores(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Stores for query {}", query);
        Page<StoreDTO> page = storeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
