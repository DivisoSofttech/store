package com.diviso.graeshoppe.store.web.rest;

import com.diviso.graeshoppe.store.StoreApp;
import com.diviso.graeshoppe.store.config.TestSecurityConfiguration;
import com.diviso.graeshoppe.store.domain.Type;
import com.diviso.graeshoppe.store.repository.TypeRepository;
import com.diviso.graeshoppe.store.repository.search.TypeSearchRepository;
import com.diviso.graeshoppe.store.service.TypeService;
import com.diviso.graeshoppe.store.service.dto.TypeDTO;
import com.diviso.graeshoppe.store.service.mapper.TypeMapper;
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
 * Integration tests for the {@link TypeResource} REST controller.
 */
@SpringBootTest(classes = {StoreApp.class, TestSecurityConfiguration.class})
public class TypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private TypeService typeService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.store.repository.search test package.
     *
     * @see com.diviso.graeshoppe.store.repository.search.TypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private TypeSearchRepository mockTypeSearchRepository;

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

    private MockMvc restTypeMockMvc;

    private Type type;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeResource typeResource = new TypeResource(typeService);
        this.restTypeMockMvc = MockMvcBuilders.standaloneSetup(typeResource)
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
    public static Type createEntity(EntityManager em) {
        Type type = new Type()
            .name(DEFAULT_NAME);
        return type;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Type createUpdatedEntity(EntityManager em) {
        Type type = new Type()
            .name(UPDATED_NAME);
        return type;
    }

    @BeforeEach
    public void initTest() {
        type = createEntity(em);
    }

    @Test
    @Transactional
    public void createType() throws Exception {
        int databaseSizeBeforeCreate = typeRepository.findAll().size();

        // Create the Type
        TypeDTO typeDTO = typeMapper.toDto(type);
        restTypeMockMvc.perform(post("/api/types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDTO)))
            .andExpect(status().isCreated());

        // Validate the Type in the database
        List<Type> typeList = typeRepository.findAll();
        assertThat(typeList).hasSize(databaseSizeBeforeCreate + 1);
        Type testType = typeList.get(typeList.size() - 1);
        assertThat(testType.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Type in Elasticsearch
        verify(mockTypeSearchRepository, times(1)).save(testType);
    }

    @Test
    @Transactional
    public void createTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeRepository.findAll().size();

        // Create the Type with an existing ID
        type.setId(1L);
        TypeDTO typeDTO = typeMapper.toDto(type);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeMockMvc.perform(post("/api/types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Type in the database
        List<Type> typeList = typeRepository.findAll();
        assertThat(typeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Type in Elasticsearch
        verify(mockTypeSearchRepository, times(0)).save(type);
    }


    @Test
    @Transactional
    public void getAllTypes() throws Exception {
        // Initialize the database
        typeRepository.saveAndFlush(type);

        // Get all the typeList
        restTypeMockMvc.perform(get("/api/types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(type.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getType() throws Exception {
        // Initialize the database
        typeRepository.saveAndFlush(type);

        // Get the type
        restTypeMockMvc.perform(get("/api/types/{id}", type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(type.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingType() throws Exception {
        // Get the type
        restTypeMockMvc.perform(get("/api/types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateType() throws Exception {
        // Initialize the database
        typeRepository.saveAndFlush(type);

        int databaseSizeBeforeUpdate = typeRepository.findAll().size();

        // Update the type
        Type updatedType = typeRepository.findById(type.getId()).get();
        // Disconnect from session so that the updates on updatedType are not directly saved in db
        em.detach(updatedType);
        updatedType
            .name(UPDATED_NAME);
        TypeDTO typeDTO = typeMapper.toDto(updatedType);

        restTypeMockMvc.perform(put("/api/types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDTO)))
            .andExpect(status().isOk());

        // Validate the Type in the database
        List<Type> typeList = typeRepository.findAll();
        assertThat(typeList).hasSize(databaseSizeBeforeUpdate);
        Type testType = typeList.get(typeList.size() - 1);
        assertThat(testType.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Type in Elasticsearch
        verify(mockTypeSearchRepository, times(1)).save(testType);
    }

    @Test
    @Transactional
    public void updateNonExistingType() throws Exception {
        int databaseSizeBeforeUpdate = typeRepository.findAll().size();

        // Create the Type
        TypeDTO typeDTO = typeMapper.toDto(type);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeMockMvc.perform(put("/api/types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Type in the database
        List<Type> typeList = typeRepository.findAll();
        assertThat(typeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Type in Elasticsearch
        verify(mockTypeSearchRepository, times(0)).save(type);
    }

    @Test
    @Transactional
    public void deleteType() throws Exception {
        // Initialize the database
        typeRepository.saveAndFlush(type);

        int databaseSizeBeforeDelete = typeRepository.findAll().size();

        // Delete the type
        restTypeMockMvc.perform(delete("/api/types/{id}", type.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Type> typeList = typeRepository.findAll();
        assertThat(typeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Type in Elasticsearch
        verify(mockTypeSearchRepository, times(1)).deleteById(type.getId());
    }

    @Test
    @Transactional
    public void searchType() throws Exception {
        // Initialize the database
        typeRepository.saveAndFlush(type);
        when(mockTypeSearchRepository.search(queryStringQuery("id:" + type.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(type), PageRequest.of(0, 1), 1));
        // Search the type
        restTypeMockMvc.perform(get("/api/_search/types?query=id:" + type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(type.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
}
