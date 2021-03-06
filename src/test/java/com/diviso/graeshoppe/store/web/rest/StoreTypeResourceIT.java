package com.diviso.graeshoppe.store.web.rest;

import com.diviso.graeshoppe.store.StoreApp;
import com.diviso.graeshoppe.store.config.TestSecurityConfiguration;
import com.diviso.graeshoppe.store.domain.StoreType;
import com.diviso.graeshoppe.store.repository.StoreTypeRepository;
import com.diviso.graeshoppe.store.repository.search.StoreTypeSearchRepository;
import com.diviso.graeshoppe.store.service.StoreTypeService;
import com.diviso.graeshoppe.store.service.dto.StoreTypeDTO;
import com.diviso.graeshoppe.store.service.mapper.StoreTypeMapper;
import com.diviso.graeshoppe.store.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.diviso.graeshoppe.store.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StoreTypeResource} REST controller.
 */
@SpringBootTest(classes = {StoreApp.class, TestSecurityConfiguration.class})
public class StoreTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private StoreTypeRepository storeTypeRepository;

    @Autowired
    private StoreTypeMapper storeTypeMapper;

    @Autowired
    private StoreTypeService storeTypeService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.store.repository.search test package.
     *
     * @see com.diviso.graeshoppe.store.repository.search.StoreTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private StoreTypeSearchRepository mockStoreTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restStoreTypeMockMvc;

    private StoreType storeType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StoreTypeResource storeTypeResource = new StoreTypeResource(storeTypeService);
        this.restStoreTypeMockMvc = MockMvcBuilders.standaloneSetup(storeTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreType createEntity(EntityManager em) {
        StoreType storeType = new StoreType()
            .name(DEFAULT_NAME);
        return storeType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreType createUpdatedEntity(EntityManager em) {
        StoreType storeType = new StoreType()
            .name(UPDATED_NAME);
        return storeType;
    }

    @BeforeEach
    public void initTest() {
        storeType = createEntity(em);
    }

    @Test
    @Transactional
    public void createStoreType() throws Exception {
        int databaseSizeBeforeCreate = storeTypeRepository.findAll().size();

        // Create the StoreType
        StoreTypeDTO storeTypeDTO = storeTypeMapper.toDto(storeType);
        restStoreTypeMockMvc.perform(post("/api/store-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the StoreType in the database
        List<StoreType> storeTypeList = storeTypeRepository.findAll();
        assertThat(storeTypeList).hasSize(databaseSizeBeforeCreate + 1);
        StoreType testStoreType = storeTypeList.get(storeTypeList.size() - 1);
        assertThat(testStoreType.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the StoreType in Elasticsearch
        verify(mockStoreTypeSearchRepository, times(1)).save(testStoreType);
    }

    @Test
    @Transactional
    public void createStoreTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storeTypeRepository.findAll().size();

        // Create the StoreType with an existing ID
        storeType.setId(1L);
        StoreTypeDTO storeTypeDTO = storeTypeMapper.toDto(storeType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoreTypeMockMvc.perform(post("/api/store-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StoreType in the database
        List<StoreType> storeTypeList = storeTypeRepository.findAll();
        assertThat(storeTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the StoreType in Elasticsearch
        verify(mockStoreTypeSearchRepository, times(0)).save(storeType);
    }


    @Test
    @Transactional
    public void getAllStoreTypes() throws Exception {
        // Initialize the database
        storeTypeRepository.saveAndFlush(storeType);

        // Get all the storeTypeList
        restStoreTypeMockMvc.perform(get("/api/store-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getStoreType() throws Exception {
        // Initialize the database
        storeTypeRepository.saveAndFlush(storeType);

        // Get the storeType
        restStoreTypeMockMvc.perform(get("/api/store-types/{id}", storeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(storeType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingStoreType() throws Exception {
        // Get the storeType
        restStoreTypeMockMvc.perform(get("/api/store-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStoreType() throws Exception {
        // Initialize the database
        storeTypeRepository.saveAndFlush(storeType);

        int databaseSizeBeforeUpdate = storeTypeRepository.findAll().size();

        // Update the storeType
        StoreType updatedStoreType = storeTypeRepository.findById(storeType.getId()).get();
        // Disconnect from session so that the updates on updatedStoreType are not directly saved in db
        em.detach(updatedStoreType);
        updatedStoreType
            .name(UPDATED_NAME);
        StoreTypeDTO storeTypeDTO = storeTypeMapper.toDto(updatedStoreType);

        restStoreTypeMockMvc.perform(put("/api/store-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeTypeDTO)))
            .andExpect(status().isOk());

        // Validate the StoreType in the database
        List<StoreType> storeTypeList = storeTypeRepository.findAll();
        assertThat(storeTypeList).hasSize(databaseSizeBeforeUpdate);
        StoreType testStoreType = storeTypeList.get(storeTypeList.size() - 1);
        assertThat(testStoreType.getName()).isEqualTo(UPDATED_NAME);

        // Validate the StoreType in Elasticsearch
        verify(mockStoreTypeSearchRepository, times(1)).save(testStoreType);
    }

    @Test
    @Transactional
    public void updateNonExistingStoreType() throws Exception {
        int databaseSizeBeforeUpdate = storeTypeRepository.findAll().size();

        // Create the StoreType
        StoreTypeDTO storeTypeDTO = storeTypeMapper.toDto(storeType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreTypeMockMvc.perform(put("/api/store-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StoreType in the database
        List<StoreType> storeTypeList = storeTypeRepository.findAll();
        assertThat(storeTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StoreType in Elasticsearch
        verify(mockStoreTypeSearchRepository, times(0)).save(storeType);
    }

    @Test
    @Transactional
    public void deleteStoreType() throws Exception {
        // Initialize the database
        storeTypeRepository.saveAndFlush(storeType);

        int databaseSizeBeforeDelete = storeTypeRepository.findAll().size();

        // Delete the storeType
        restStoreTypeMockMvc.perform(delete("/api/store-types/{id}", storeType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StoreType> storeTypeList = storeTypeRepository.findAll();
        assertThat(storeTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StoreType in Elasticsearch
        verify(mockStoreTypeSearchRepository, times(1)).deleteById(storeType.getId());
    }

    @Test
    @Transactional
    public void searchStoreType() throws Exception {
        // Initialize the database
        storeTypeRepository.saveAndFlush(storeType);
        when(mockStoreTypeSearchRepository.search(queryStringQuery("id:" + storeType.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(storeType), PageRequest.of(0, 1), 1));
        // Search the storeType
        restStoreTypeMockMvc.perform(get("/api/_search/store-types?query=id:" + storeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
}
