package com.diviso.graeshoppe.store.web.rest;

import com.diviso.graeshoppe.store.domain.Store;
import com.diviso.graeshoppe.store.repository.StoreRepository;
import com.diviso.graeshoppe.store.repository.search.StoreSearchRepository;
import com.diviso.graeshoppe.store.service.StoreService;
import com.diviso.graeshoppe.store.service.StoreSettingsService;
import com.diviso.graeshoppe.store.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.store.service.dto.StoreDTO;
import com.diviso.graeshoppe.store.service.dto.StoreSettingsDTO;

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

/**
 * REST controller for managing {@link com.diviso.graeshoppe.store.domain.Store}.
 */
@RestController
@RequestMapping("/api")
public class StoreResource {

	@Autowired
	StoreSearchRepository storeSearchRepository;

	@Autowired
	StoreRepository storeRepo;

	private final Logger log = LoggerFactory.getLogger(StoreResource.class);

	private static final String ENTITY_NAME = "storeStore";

	private final StoreService storeService;
	

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

	@Autowired
	private StoreSettingsService storeSettingsService;

	public StoreResource(StoreService storeService) {
		this.storeService = storeService;
	}

	/**
	 * POST /stores : Create a new store.
	 *
	 * @param storeDTO
	 *            the storeDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new storeDTO, or with status 400 (Bad Request) if the store has
	 *         already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/stores")
	public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) throws URISyntaxException {
		
		log.debug("REST request to save Store : {}", storeDTO);
		
		storeDTO.setTotalRating(0.0);

		if (storeDTO.getId() != null) {
			throw new BadRequestAlertException("A new store cannot already have an ID", ENTITY_NAME, "idexists");
		}
		//List<Store> stores = storeRepo.findAll();

	/*	stores.forEach(s -> {
			if (storeDTO.getRegNo().equals(s.getRegNo())) {

				throw new BadRequestAlertException("Already have a Store with the same name", ENTITY_NAME,
						"nameexists");
			}

		});*/
		
		if(storeService.isRegisteredStore(storeDTO.getRegNo())==true) {
			throw new BadRequestAlertException("Already have a Store with the same name", ENTITY_NAME,
					"nameexists");
		}
		if( storeDTO.getMinAmount()==null) {
			storeDTO.setMinAmount(0.0);
		}
		

		StoreSettingsDTO storeSettings = new StoreSettingsDTO();

		storeSettings.setOrderAcceptType("automatic");

		storeSettings.setDeliveryCharge(0.0);

		storeSettings.setServiceCharge(0.0);

		StoreSettingsDTO storeSettingsDTO = storeSettingsService.save(storeSettings);

		storeDTO.setStoreSettingsId(storeSettingsDTO.getId());

		StoreDTO result = storeService.save(storeDTO);

		return ResponseEntity.created(new URI("/api/stores/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName,true,ENTITY_NAME, result.getId().toString())).body(result);
	}

	@PostMapping("/stores-denormalized")
	public ResponseEntity<Store> createDeNormalizedStore(@RequestBody Store store) throws URISyntaxException {
		log.debug("REST request to save Store : {}", store);
		if (store.getId() != null) {
			throw new BadRequestAlertException("A new store cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Store result = storeService.saveStore(store);
		return ResponseEntity.created(new URI("/api/stores-denormalized/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /stores : Updates an existing store.
	 *
	 * @param storeDTO
	 *            the storeDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         storeDTO, or with status 400 (Bad Request) if the storeDTO is not
	 *         valid, or with status 500 (Internal Server Error) if the storeDTO
	 *         couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/stores")
	public ResponseEntity<StoreDTO> updateStore(@RequestBody StoreDTO storeDTO) throws URISyntaxException {
		log.debug("REST request to update Store : {}", storeDTO);
		if (storeDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		StoreDTO result = storeService.update(storeDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, storeDTO.getId().toString()))
				.body(result);
	}

	@PutMapping("/stores-denormalized")
	public ResponseEntity<Store> updateStoreDeNormalized(@RequestBody Store store) throws URISyntaxException {
		log.debug("REST request to update StoreDenormalized : {}", store);
		if (store.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Store result = storeService.saveStore(store);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, store.getId().toString()))
				.body(result);
	}

	/**
	 * GET /stores : get all the stores.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of stores in
	 *         body
	 */
	@GetMapping("/stores")
	public ResponseEntity<List<StoreDTO>> getAllStores(Pageable pageable) {
		log.debug("REST request to get a page of Stores");
		Page<StoreDTO> page = storeService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * GET /stores/:id : get the "id" store.
	 *
	 * @param id
	 *            the id of the storeDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         storeDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/stores/{id}")
	public ResponseEntity<StoreDTO> getStore(@PathVariable Long id) {
		log.debug("REST request to get Store : {}", id);
		Optional<StoreDTO> storeDTO = storeService.findOne(id);
		return ResponseUtil.wrapOrNotFound(storeDTO);
	}

	/**
	 * DELETE /stores/:id : delete the "id" store.
	 *
	 * @param id
	 *            the id of the storeDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/stores/{id}")
	public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
		log.debug("REST request to delete Store : {}", id);
		storeService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
	}


	@GetMapping("/storesettings/{storeId}")
	public StoreSettingsDTO findStoreSettingsByStoreId(@PathVariable String storeId){
		return storeService.findStoreSettingsByStoreId(storeId);
	}
	
	@GetMapping("/findByRegNo/{regNo}")
	public Store findByRegNo(@PathVariable String regNo){
		return storeService.findByRegNo(regNo);
	}
}
