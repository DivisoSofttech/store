package com.diviso.graeshoppe.store.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Store.
 */
@Entity
@Table(name = "store")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "store")
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "reg_no")
    private String regNo;

    @Column(name = "name")
    private String name;

    @Column(name = "total_rating")
    private Double totalRating;

    @Column(name = "location")
    private String location;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "contact_no")
    private Long contactNo;

    @Column(name = "opening_time")
    private Instant openingTime;

    @Column(name = "email")
    private String email;

    @Column(name = "closing_time")
    private Instant closingTime;

    @Column(name = "info")
    private String info;

    @Column(name = "min_amount")
    private Double minAmount;

    @Column(name = "max_delivery_time")
    private Instant maxDeliveryTime;

    @NotNull
    @Column(name = "store_unique_id", nullable = false)
    private String storeUniqueId;

    @NotNull
    @Column(name = "image_link", nullable = false)
    private String imageLink;

    @OneToOne
    @JoinColumn(unique = true)
    private StoreAddress storeAddress;

    @OneToOne
    @JoinColumn(unique = true)
    private StoreSettings storeSettings;

    @OneToOne
    @JoinColumn(unique = true)
    private PreOrderSettings preOrderSettings;

    @OneToMany(mappedBy = "store")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StoreType> storeTypes = new HashSet<>();

    @OneToMany(mappedBy = "store")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Banner> banners = new HashSet<>();

    @OneToMany(mappedBy = "store")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeliveryInfo> deliveryInfos = new HashSet<>();

    @OneToMany(mappedBy = "store")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserRatingReview> userRatingReviews = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public Store regNo(String regNo) {
        this.regNo = regNo;
        return this;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getName() {
        return name;
    }

    public Store name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotalRating() {
        return totalRating;
    }

    public Store totalRating(Double totalRating) {
        this.totalRating = totalRating;
        return this;
    }

    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }

    public String getLocation() {
        return location;
    }

    public Store location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationName() {
        return locationName;
    }

    public Store locationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getContactNo() {
        return contactNo;
    }

    public Store contactNo(Long contactNo) {
        this.contactNo = contactNo;
        return this;
    }

    public void setContactNo(Long contactNo) {
        this.contactNo = contactNo;
    }

    public Instant getOpeningTime() {
        return openingTime;
    }

    public Store openingTime(Instant openingTime) {
        this.openingTime = openingTime;
        return this;
    }

    public void setOpeningTime(Instant openingTime) {
        this.openingTime = openingTime;
    }

    public String getEmail() {
        return email;
    }

    public Store email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getClosingTime() {
        return closingTime;
    }

    public Store closingTime(Instant closingTime) {
        this.closingTime = closingTime;
        return this;
    }

    public void setClosingTime(Instant closingTime) {
        this.closingTime = closingTime;
    }

    public String getInfo() {
        return info;
    }

    public Store info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public Store minAmount(Double minAmount) {
        this.minAmount = minAmount;
        return this;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Instant getMaxDeliveryTime() {
        return maxDeliveryTime;
    }

    public Store maxDeliveryTime(Instant maxDeliveryTime) {
        this.maxDeliveryTime = maxDeliveryTime;
        return this;
    }

    public void setMaxDeliveryTime(Instant maxDeliveryTime) {
        this.maxDeliveryTime = maxDeliveryTime;
    }

    public String getStoreUniqueId() {
        return storeUniqueId;
    }

    public Store storeUniqueId(String storeUniqueId) {
        this.storeUniqueId = storeUniqueId;
        return this;
    }

    public void setStoreUniqueId(String storeUniqueId) {
        this.storeUniqueId = storeUniqueId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public Store imageLink(String imageLink) {
        this.imageLink = imageLink;
        return this;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public StoreAddress getStoreAddress() {
        return storeAddress;
    }

    public Store storeAddress(StoreAddress storeAddress) {
        this.storeAddress = storeAddress;
        return this;
    }

    public void setStoreAddress(StoreAddress storeAddress) {
        this.storeAddress = storeAddress;
    }

    public StoreSettings getStoreSettings() {
        return storeSettings;
    }

    public Store storeSettings(StoreSettings storeSettings) {
        this.storeSettings = storeSettings;
        return this;
    }

    public void setStoreSettings(StoreSettings storeSettings) {
        this.storeSettings = storeSettings;
    }

    public PreOrderSettings getPreOrderSettings() {
        return preOrderSettings;
    }

    public Store preOrderSettings(PreOrderSettings preOrderSettings) {
        this.preOrderSettings = preOrderSettings;
        return this;
    }

    public void setPreOrderSettings(PreOrderSettings preOrderSettings) {
        this.preOrderSettings = preOrderSettings;
    }

    public Set<StoreType> getStoreTypes() {
        return storeTypes;
    }

    public Store storeTypes(Set<StoreType> storeTypes) {
        this.storeTypes = storeTypes;
        return this;
    }

    public Store addStoreType(StoreType storeType) {
        this.storeTypes.add(storeType);
        storeType.setStore(this);
        return this;
    }

    public Store removeStoreType(StoreType storeType) {
        this.storeTypes.remove(storeType);
        storeType.setStore(null);
        return this;
    }

    public void setStoreTypes(Set<StoreType> storeTypes) {
        this.storeTypes = storeTypes;
    }

    public Set<Banner> getBanners() {
        return banners;
    }

    public Store banners(Set<Banner> banners) {
        this.banners = banners;
        return this;
    }

    public Store addBanner(Banner banner) {
        this.banners.add(banner);
        banner.setStore(this);
        return this;
    }

    public Store removeBanner(Banner banner) {
        this.banners.remove(banner);
        banner.setStore(null);
        return this;
    }

    public void setBanners(Set<Banner> banners) {
        this.banners = banners;
    }

    public Set<DeliveryInfo> getDeliveryInfos() {
        return deliveryInfos;
    }

    public Store deliveryInfos(Set<DeliveryInfo> deliveryInfos) {
        this.deliveryInfos = deliveryInfos;
        return this;
    }

    public Store addDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfos.add(deliveryInfo);
        deliveryInfo.setStore(this);
        return this;
    }

    public Store removeDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfos.remove(deliveryInfo);
        deliveryInfo.setStore(null);
        return this;
    }

    public void setDeliveryInfos(Set<DeliveryInfo> deliveryInfos) {
        this.deliveryInfos = deliveryInfos;
    }

    public Set<UserRatingReview> getUserRatingReviews() {
        return userRatingReviews;
    }

    public Store userRatingReviews(Set<UserRatingReview> userRatingReviews) {
        this.userRatingReviews = userRatingReviews;
        return this;
    }

    public Store addUserRatingReview(UserRatingReview userRatingReview) {
        this.userRatingReviews.add(userRatingReview);
        userRatingReview.setStore(this);
        return this;
    }

    public Store removeUserRatingReview(UserRatingReview userRatingReview) {
        this.userRatingReviews.remove(userRatingReview);
        userRatingReview.setStore(null);
        return this;
    }

    public void setUserRatingReviews(Set<UserRatingReview> userRatingReviews) {
        this.userRatingReviews = userRatingReviews;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Store)) {
            return false;
        }
        return id != null && id.equals(((Store) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Store{" +
            "id=" + getId() +
            ", regNo='" + getRegNo() + "'" +
            ", name='" + getName() + "'" +
            ", totalRating=" + getTotalRating() +
            ", location='" + getLocation() + "'" +
            ", locationName='" + getLocationName() + "'" +
            ", contactNo=" + getContactNo() +
            ", openingTime='" + getOpeningTime() + "'" +
            ", email='" + getEmail() + "'" +
            ", closingTime='" + getClosingTime() + "'" +
            ", info='" + getInfo() + "'" +
            ", minAmount=" + getMinAmount() +
            ", maxDeliveryTime='" + getMaxDeliveryTime() + "'" +
            ", storeUniqueId='" + getStoreUniqueId() + "'" +
            ", imageLink='" + getImageLink() + "'" +
            "}";
    }
}
