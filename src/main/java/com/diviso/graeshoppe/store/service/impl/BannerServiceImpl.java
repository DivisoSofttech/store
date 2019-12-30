package com.diviso.graeshoppe.store.service.impl;

import com.diviso.graeshoppe.store.service.BannerService;
import com.diviso.graeshoppe.store.service.ImageService;
import com.diviso.graeshoppe.store.domain.Banner;
import com.diviso.graeshoppe.store.repository.BannerRepository;
import com.diviso.graeshoppe.store.repository.search.BannerSearchRepository;
import com.diviso.graeshoppe.store.service.dto.BannerDTO;
import com.diviso.graeshoppe.store.service.mapper.BannerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Banner}.
 */
@Service
@Transactional
public class BannerServiceImpl implements BannerService {

private final Logger log = LoggerFactory.getLogger(BannerServiceImpl.class);
    
	@Autowired
	private ImageService imageService;
    

    private final BannerRepository bannerRepository;

    private final BannerMapper bannerMapper;

    private final BannerSearchRepository bannerSearchRepository;

    public BannerServiceImpl(BannerRepository bannerRepository, BannerMapper bannerMapper, BannerSearchRepository bannerSearchRepository) {
        this.bannerRepository = bannerRepository;
        this.bannerMapper = bannerMapper;
        this.bannerSearchRepository = bannerSearchRepository;
    }

    /**
     * Save a banner.
     *
     * @param bannerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BannerDTO save(BannerDTO bannerDTO) {
        log.debug("Request to save Banner : {}", bannerDTO);
        Banner banner = bannerMapper.toEntity(bannerDTO);
		String imageLink = imageService.saveFile("banner", UUID.randomUUID().toString(), bannerDTO.getFile());
		banner.setImageLink(imageLink);
		banner = bannerRepository.save(banner);
        BannerDTO result1 = bannerMapper.toDto(banner);
        bannerSearchRepository.save(banner);
        
        return  updateToEs(result1);
    }

    private BannerDTO updateToEs(BannerDTO bannerDTO) {
    	Banner banner = bannerMapper.toEntity(bannerDTO);
         banner = bannerRepository.save(banner);
        
         BannerDTO result= bannerMapper.toDto(banner);
          bannerSearchRepository.save(banner);
         return result;
    }
  
    /**
     * Get all the banners.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BannerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Banners");
        return bannerRepository.findAll(pageable)
            .map(bannerMapper::toDto);
    }


    /**
     * Get one banner by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BannerDTO> findOne(Long id) {
        log.debug("Request to get Banner : {}", id);
        return bannerRepository.findById(id)
            .map(bannerMapper::toDto);
    }

    /**
     * Delete the banner by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Banner : {}", id);        bannerRepository.deleteById(id);
        bannerSearchRepository.deleteById(id);
    }

    /**
     * Search for the banner corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BannerDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Banners for query {}", query);
        return bannerSearchRepository.search(queryStringQuery(query), pageable)
            .map(bannerMapper::toDto);
    }
}
