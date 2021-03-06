package com.diviso.graeshoppe.store.domain.search;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;

import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.completion.Completion;
@org.springframework.data.elasticsearch.annotations.Document(indexName = "storesuggestion")
public class StoreSuggestion {
@Id
	 @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
	    private Long id;
	@CompletionField(maxInputLength=100)
	private Completion suggest;
	
	
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
	@Override
	public String toString() {
		return "StoreSuggestion [id=" + id + ", suggest=" + suggest + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoreSuggestion other = (StoreSuggestion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
