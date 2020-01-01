package com.diviso.graeshoppe.store.service.impl;

import com.diviso.graeshoppe.store.service.ImageService;
import com.diviso.graeshoppe.store.service.StoreService;
import com.diviso.graeshoppe.store.domain.Store;
import com.diviso.graeshoppe.store.repository.StoreRepository;
import com.diviso.graeshoppe.store.repository.search.StoreSearchRepository;
import com.diviso.graeshoppe.store.repository.search.StoreSuggestionSearchRepository;
import com.diviso.graeshoppe.store.service.dto.StoreDTO;
import com.diviso.graeshoppe.store.service.dto.StoreSettingsDTO;
import com.diviso.graeshoppe.store.service.mapper.StoreMapper;
import com.diviso.graeshoppe.store.service.mapper.StoreSettingsMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.elasticsearch.index.query.QueryBuilders.*;
import com.diviso.graeshoppe.store.domain.search.StoreSuggestion;
/**
 * Service Implementation for managing {@link Store}.
 */
@Service
@Transactional
public class StoreServiceImpl implements StoreService {
	private final Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);

	private final StoreRepository storeRepository;

	private final StoreMapper storeMapper;
	
	@Autowired
	private ImageService imageService;
	
	
	@Autowired
	private StoreSettingsMapper storeSettingsMapper;

	private final StoreSearchRepository storeSearchRepository;
	
	@Autowired
	private  StoreSuggestionSearchRepository storeSuggestionSearchRepository;
	public StoreServiceImpl(StoreRepository storeRepository, StoreMapper storeMapper,
			StoreSearchRepository storeSearchRepository) {
		this.storeRepository = storeRepository;
		this.storeMapper = storeMapper;
		this.storeSearchRepository = storeSearchRepository;
	}

	/**
	 * Save a store.
	 *
	 * @param storeDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public StoreDTO save(StoreDTO storeDTO) {
		log.debug("Request to save Store : {}", storeDTO);
		Store store = storeMapper.toEntity(storeDTO);
		store.setId(1500l);
		store = storeRepository.save(store);
		StoreDTO result = storeMapper.toDto(store);
		StoreSuggestion  storeSuggestion = new StoreSuggestion();
		storeSuggestion.setId(result.getId());
		storeSuggestion.setSuggest(new Completion(new String[] {result.getRegNo()}));
		
		storeSearchRepository.save(store);
		storeSuggestionSearchRepository.save(storeSuggestion);
		return result;
	}
	
	@Override
	public StoreDTO update(StoreDTO storeDTO) {
		log.debug("Request to save Store : {}", storeDTO);
		Store store = storeMapper.toEntity(storeDTO);

		String imageLink  = imageService.saveFile("store", UUID.randomUUID().toString(), storeDTO.getImage());
		store.setImageLink(imageLink);
		store = storeRepository.save(store);
		StoreDTO result = storeMapper.toDto(store);
		storeSearchRepository.save(store);
		return result;
	}

	/**
	 * Get all the stores.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<StoreDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Stores");
		return storeRepository.findAll(pageable).map(storeMapper::toDto);
	}

	/**
	 * Get one store by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<StoreDTO> findOne(Long id) {
		log.debug("Request to get Store : {}", id);
		return storeRepository.findById(id).map(storeMapper::toDto);
	}

	/**
	 * Delete the store by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Store : {}", id);
		storeRepository.deleteById(id);
		storeSearchRepository.deleteById(id);
	}

	/**
	 * Search for the store corresponding to the query.
	 *
	 * @param query
	 *            the query of the search
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<StoreDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Stores for query {}", query);
		return storeSearchRepository.search(queryStringQuery(query), pageable).map(storeMapper::toDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.StoreService#saveStore(com.diviso.
	 * graeshoppe.domain.Store)
	 */
	@Override
	public Store saveStore(Store store) {
		log.debug("Request to save StoreDenormalized :{}", store);
		storeSearchRepository.save(store);
		return storeRepository.save(store);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.StoreService#findStoreSettingsByStoreId(
	 * java.lang.String)
	 */
	@Override
	public StoreSettingsDTO findStoreSettingsByStoreId(String storeId) {
		log.debug("Request to get Store : {}", storeId);
		return storeSettingsMapper.toDto(storeRepository.findStoreSettingsByStoreId(storeId));
	}

	@Override
	public Store findByRegNo(String regNo) {
		log.debug("Request to get Store : {}", regNo);
		return storeRepository.findByRegNo(regNo);
	}


	public boolean isRegisteredStore(String regNo) {
		return storeRepository.existsByRegNo(regNo);
	}
}