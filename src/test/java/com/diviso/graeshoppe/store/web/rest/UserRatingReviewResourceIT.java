package com.diviso.graeshoppe.store.web.rest;

import com.diviso.graeshoppe.store.StoreApp;
import com.diviso.graeshoppe.store.config.TestSecurityConfiguration;
import com.diviso.graeshoppe.store.domain.UserRatingReview;
import com.diviso.graeshoppe.store.repository.UserRatingReviewRepository;
import com.diviso.graeshoppe.store.repository.search.UserRatingReviewSearchRepository;
import com.diviso.graeshoppe.store.service.UserRatingReviewService;
import com.diviso.graeshoppe.store.service.dto.UserRatingReviewDTO;
import com.diviso.graeshoppe.store.service.mapper.UserRatingReviewMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.diviso.graeshoppe.store.web.rest.TestUtil.sameInstant;
import static com.diviso.graeshoppe.store.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserRatingReviewResource} REST controller.
 */
@SpringBootTest(classes = {StoreApp.class, TestSecurityConfiguration.class})
public class UserRatingReviewResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_RATING = 1D;
    private static final Double UPDATED_RATING = 2D;

    private static final String DEFAULT_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_REVIEW = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private UserRatingReviewRepository userRatingReviewRepository;

    @Autowired
    private UserRatingReviewMapper userRatingReviewMapper;

    @Autowired
    private UserRatingReviewService userRatingReviewService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.store.repository.search test package.
     *
     * @see com.diviso.graeshoppe.store.repository.search.UserRatingReviewSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserRatingReviewSearchRepository mockUserRatingReviewSearchRepository;

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

    private MockMvc restUserRatingReviewMockMvc;

    private UserRatingReview userRatingReview;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserRatingReviewResource userRatingReviewResource = new UserRatingReviewResource(userRatingReviewService);
        this.restUserRatingReviewMockMvc = MockMvcBuilders.standaloneSetup(userRatingReviewResource)
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
    public static UserRatingReview createEntity(EntityManager em) {
        UserRatingReview userRatingReview = new UserRatingReview()
            .userName(DEFAULT_USER_NAME)
            .rating(DEFAULT_RATING)
            .review(DEFAULT_REVIEW)
            .date(DEFAULT_DATE);
        return userRatingReview;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRatingReview createUpdatedEntity(EntityManager em) {
        UserRatingReview userRatingReview = new UserRatingReview()
            .userName(UPDATED_USER_NAME)
            .rating(UPDATED_RATING)
            .review(UPDATED_REVIEW)
            .date(UPDATED_DATE);
        return userRatingReview;
    }

    @BeforeEach
    public void initTest() {
        userRatingReview = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserRatingReview() throws Exception {
        int databaseSizeBeforeCreate = userRatingReviewRepository.findAll().size();

        // Create the UserRatingReview
        UserRatingReviewDTO userRatingReviewDTO = userRatingReviewMapper.toDto(userRatingReview);
        restUserRatingReviewMockMvc.perform(post("/api/user-rating-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userRatingReviewDTO)))
            .andExpect(status().isCreated());

        // Validate the UserRatingReview in the database
        List<UserRatingReview> userRatingReviewList = userRatingReviewRepository.findAll();
        assertThat(userRatingReviewList).hasSize(databaseSizeBeforeCreate + 1);
        UserRatingReview testUserRatingReview = userRatingReviewList.get(userRatingReviewList.size() - 1);
        assertThat(testUserRatingReview.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testUserRatingReview.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testUserRatingReview.getReview()).isEqualTo(DEFAULT_REVIEW);
        assertThat(testUserRatingReview.getDate()).isEqualTo(DEFAULT_DATE);

        // Validate the UserRatingReview in Elasticsearch
        verify(mockUserRatingReviewSearchRepository, times(1)).save(testUserRatingReview);
    }

    @Test
    @Transactional
    public void createUserRatingReviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userRatingReviewRepository.findAll().size();

        // Create the UserRatingReview with an existing ID
        userRatingReview.setId(1L);
        UserRatingReviewDTO userRatingReviewDTO = userRatingReviewMapper.toDto(userRatingReview);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserRatingReviewMockMvc.perform(post("/api/user-rating-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userRatingReviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserRatingReview in the database
        List<UserRatingReview> userRatingReviewList = userRatingReviewRepository.findAll();
        assertThat(userRatingReviewList).hasSize(databaseSizeBeforeCreate);

        // Validate the UserRatingReview in Elasticsearch
        verify(mockUserRatingReviewSearchRepository, times(0)).save(userRatingReview);
    }


    @Test
    @Transactional
    public void getAllUserRatingReviews() throws Exception {
        // Initialize the database
        userRatingReviewRepository.saveAndFlush(userRatingReview);

        // Get all the userRatingReviewList
        restUserRatingReviewMockMvc.perform(get("/api/user-rating-reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRatingReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].review").value(hasItem(DEFAULT_REVIEW)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }
    
    @Test
    @Transactional
    public void getUserRatingReview() throws Exception {
        // Initialize the database
        userRatingReviewRepository.saveAndFlush(userRatingReview);

        // Get the userRatingReview
        restUserRatingReviewMockMvc.perform(get("/api/user-rating-reviews/{id}", userRatingReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userRatingReview.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()))
            .andExpect(jsonPath("$.review").value(DEFAULT_REVIEW))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingUserRatingReview() throws Exception {
        // Get the userRatingReview
        restUserRatingReviewMockMvc.perform(get("/api/user-rating-reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserRatingReview() throws Exception {
        // Initialize the database
        userRatingReviewRepository.saveAndFlush(userRatingReview);

        int databaseSizeBeforeUpdate = userRatingReviewRepository.findAll().size();

        // Update the userRatingReview
        UserRatingReview updatedUserRatingReview = userRatingReviewRepository.findById(userRatingReview.getId()).get();
        // Disconnect from session so that the updates on updatedUserRatingReview are not directly saved in db
        em.detach(updatedUserRatingReview);
        updatedUserRatingReview
            .userName(UPDATED_USER_NAME)
            .rating(UPDATED_RATING)
            .review(UPDATED_REVIEW)
            .date(UPDATED_DATE);
        UserRatingReviewDTO userRatingReviewDTO = userRatingReviewMapper.toDto(updatedUserRatingReview);

        restUserRatingReviewMockMvc.perform(put("/api/user-rating-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userRatingReviewDTO)))
            .andExpect(status().isOk());

        // Validate the UserRatingReview in the database
        List<UserRatingReview> userRatingReviewList = userRatingReviewRepository.findAll();
        assertThat(userRatingReviewList).hasSize(databaseSizeBeforeUpdate);
        UserRatingReview testUserRatingReview = userRatingReviewList.get(userRatingReviewList.size() - 1);
        assertThat(testUserRatingReview.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUserRatingReview.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testUserRatingReview.getReview()).isEqualTo(UPDATED_REVIEW);
        assertThat(testUserRatingReview.getDate()).isEqualTo(UPDATED_DATE);

        // Validate the UserRatingReview in Elasticsearch
        verify(mockUserRatingReviewSearchRepository, times(1)).save(testUserRatingReview);
    }

    @Test
    @Transactional
    public void updateNonExistingUserRatingReview() throws Exception {
        int databaseSizeBeforeUpdate = userRatingReviewRepository.findAll().size();

        // Create the UserRatingReview
        UserRatingReviewDTO userRatingReviewDTO = userRatingReviewMapper.toDto(userRatingReview);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRatingReviewMockMvc.perform(put("/api/user-rating-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userRatingReviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserRatingReview in the database
        List<UserRatingReview> userRatingReviewList = userRatingReviewRepository.findAll();
        assertThat(userRatingReviewList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UserRatingReview in Elasticsearch
        verify(mockUserRatingReviewSearchRepository, times(0)).save(userRatingReview);
    }

    @Test
    @Transactional
    public void deleteUserRatingReview() throws Exception {
        // Initialize the database
        userRatingReviewRepository.saveAndFlush(userRatingReview);

        int databaseSizeBeforeDelete = userRatingReviewRepository.findAll().size();

        // Delete the userRatingReview
        restUserRatingReviewMockMvc.perform(delete("/api/user-rating-reviews/{id}", userRatingReview.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserRatingReview> userRatingReviewList = userRatingReviewRepository.findAll();
        assertThat(userRatingReviewList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UserRatingReview in Elasticsearch
        verify(mockUserRatingReviewSearchRepository, times(1)).deleteById(userRatingReview.getId());
    }

    @Test
    @Transactional
    public void searchUserRatingReview() throws Exception {
        // Initialize the database
        userRatingReviewRepository.saveAndFlush(userRatingReview);
        when(mockUserRatingReviewSearchRepository.search(queryStringQuery("id:" + userRatingReview.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(userRatingReview), PageRequest.of(0, 1), 1));
        // Search the userRatingReview
        restUserRatingReviewMockMvc.perform(get("/api/_search/user-rating-reviews?query=id:" + userRatingReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRatingReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].review").value(hasItem(DEFAULT_REVIEW)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }
}
