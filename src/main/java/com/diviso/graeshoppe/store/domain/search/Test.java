package com.diviso.graeshoppe.store.domain.search;
import org.springframework.data.annotation.Id;
import  org.springframework.data.elasticsearch.annotations.CompletionField;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.elasticsearch.core.completion.Completion;
/**
 * A Store.
 */

@org.springframework.data.elasticsearch.annotations.Document(indexName = "test")
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

  
    
    private String name;

    @CompletionField(maxInputLength = 100)
    private Completion suggest;


   

    public String getName() {
        return name;
    }

  

    public void setName(String name) {
        this.name = name;
    }

  
  

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

 
    @Override
	public String toString() {
		return "Test [id=" + id + ", name=" + name + ", suggest=" + suggest + "]";
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Completion getSuggest() {
		return suggest;
	}



	public void setSuggest(Completion suggest) {
		this.suggest = suggest;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
    public int hashCode() {
        return 31;
    }

  /*  @Override
    public String toString() {
        return "Store{" +
            "id=" + getId() +
            ", regNo='" + getRegNo() + "'" +
            ", name='" + getName() + "'" +
            ", info='" + getInfo() + "'" +
            ", minAmount=" + getMinAmount() +
            ", maxDeliveryTime='" + getMaxDeliveryTime() + "'" +
            "}";
    }*/
}
