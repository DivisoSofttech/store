package com.diviso.graeshoppe.store.repository;

import com.diviso.graeshoppe.store.domain.StoreAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StoreAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreAddressRepository extends JpaRepository<StoreAddress, Long> {

}
