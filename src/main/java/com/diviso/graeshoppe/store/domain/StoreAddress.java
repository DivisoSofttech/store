package com.diviso.graeshoppe.store.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A StoreAddress.
 */
@Entity
@Table(name = "store_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "storeaddress")
public class StoreAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "house_no_or_building_name")
    private String houseNoOrBuildingName;

    @Column(name = "road_name_area_or_street")
    private String roadNameAreaOrStreet;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "address_type")
    private String addressType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPincode() {
        return pincode;
    }

    public StoreAddress pincode(String pincode) {
        this.pincode = pincode;
        return this;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getHouseNoOrBuildingName() {
        return houseNoOrBuildingName;
    }

    public StoreAddress houseNoOrBuildingName(String houseNoOrBuildingName) {
        this.houseNoOrBuildingName = houseNoOrBuildingName;
        return this;
    }

    public void setHouseNoOrBuildingName(String houseNoOrBuildingName) {
        this.houseNoOrBuildingName = houseNoOrBuildingName;
    }

    public String getRoadNameAreaOrStreet() {
        return roadNameAreaOrStreet;
    }

    public StoreAddress roadNameAreaOrStreet(String roadNameAreaOrStreet) {
        this.roadNameAreaOrStreet = roadNameAreaOrStreet;
        return this;
    }

    public void setRoadNameAreaOrStreet(String roadNameAreaOrStreet) {
        this.roadNameAreaOrStreet = roadNameAreaOrStreet;
    }

    public String getCity() {
        return city;
    }

    public StoreAddress city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public StoreAddress state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLandmark() {
        return landmark;
    }

    public StoreAddress landmark(String landmark) {
        this.landmark = landmark;
        return this;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAddressType() {
        return addressType;
    }

    public StoreAddress addressType(String addressType) {
        this.addressType = addressType;
        return this;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreAddress)) {
            return false;
        }
        return id != null && id.equals(((StoreAddress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StoreAddress{" +
            "id=" + getId() +
            ", pincode='" + getPincode() + "'" +
            ", houseNoOrBuildingName='" + getHouseNoOrBuildingName() + "'" +
            ", roadNameAreaOrStreet='" + getRoadNameAreaOrStreet() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", landmark='" + getLandmark() + "'" +
            ", addressType='" + getAddressType() + "'" +
            "}";
    }
}
