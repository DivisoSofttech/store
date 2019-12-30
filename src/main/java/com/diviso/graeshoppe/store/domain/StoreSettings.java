package com.diviso.graeshoppe.store.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A StoreSettings.
 */
@Entity
@Table(name = "store_settings")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "storesettings")
public class StoreSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "delivery_charge")
    private Double deliveryCharge;

    @Column(name = "service_charge")
    private Double serviceCharge;

    @Column(name = "order_accept_type")
    private String orderAcceptType;

    @Column(name = "is_inventory_required")
    private Boolean isInventoryRequired;

    @Column(name = "is_active")
    private Boolean isActive;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDeliveryCharge() {
        return deliveryCharge;
    }

    public StoreSettings deliveryCharge(Double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
        return this;
    }

    public void setDeliveryCharge(Double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public StoreSettings serviceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
        return this;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getOrderAcceptType() {
        return orderAcceptType;
    }

    public StoreSettings orderAcceptType(String orderAcceptType) {
        this.orderAcceptType = orderAcceptType;
        return this;
    }

    public void setOrderAcceptType(String orderAcceptType) {
        this.orderAcceptType = orderAcceptType;
    }

    public Boolean isIsInventoryRequired() {
        return isInventoryRequired;
    }

    public StoreSettings isInventoryRequired(Boolean isInventoryRequired) {
        this.isInventoryRequired = isInventoryRequired;
        return this;
    }

    public void setIsInventoryRequired(Boolean isInventoryRequired) {
        this.isInventoryRequired = isInventoryRequired;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public StoreSettings isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreSettings)) {
            return false;
        }
        return id != null && id.equals(((StoreSettings) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StoreSettings{" +
            "id=" + getId() +
            ", deliveryCharge=" + getDeliveryCharge() +
            ", serviceCharge=" + getServiceCharge() +
            ", orderAcceptType='" + getOrderAcceptType() + "'" +
            ", isInventoryRequired='" + isIsInventoryRequired() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
