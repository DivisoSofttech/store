package com.diviso.graeshoppe.store.repository;

import com.diviso.graeshoppe.store.domain.StoreType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StoreType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreTypeRepository extends JpaRepository<StoreType, Long> {

}
