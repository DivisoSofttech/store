package com.diviso.graeshoppe.store.repository;

import com.diviso.graeshoppe.store.domain.Store;
import com.diviso.graeshoppe.store.domain.StoreSettings;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Store entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

	@Query(value="Select distinct  settings  from Store s Join s.storeSettings  settings  Where s.regNo =:regNo ")
	public StoreSettings findStoreSettingsByStoreId(@Param("regNo")String regNo);
	
	public Store findByRegNo(String regNo);
	
	boolean existsByRegNo(String regNo);
}
