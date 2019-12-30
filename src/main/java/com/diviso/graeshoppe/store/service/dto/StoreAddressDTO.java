package com.diviso.graeshoppe.store.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.diviso.graeshoppe.store.domain.StoreAddress} entity.
 */
public class StoreAddressDTO implements Serializable {

    private Long id;

    private String pincode;

    private String houseNoOrBuildingName;

    private String roadNameAreaOrStreet;

    private String city;

    private String state;

    private String landmark;

    private String addressType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getHouseNoOrBuildingName() {
        return houseNoOrBuildingName;
    }

    public void setHouseNoOrBuildingName(String houseNoOrBuildingName) {
        this.houseNoOrBuildingName = houseNoOrBuildingName;
    }

    public String getRoadNameAreaOrStreet() {
        return roadNameAreaOrStreet;
    }

    public void setRoadNameAreaOrStreet(String roadNameAreaOrStreet) {
        this.roadNameAreaOrStreet = roadNameAreaOrStreet;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StoreAddressDTO storeAddressDTO = (StoreAddressDTO) o;
        if (storeAddressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), storeAddressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StoreAddressDTO{" +
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
