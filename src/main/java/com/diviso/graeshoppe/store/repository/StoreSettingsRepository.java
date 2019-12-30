package com.diviso.graeshoppe.store.repository;

import com.diviso.graeshoppe.store.domain.StoreSettings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StoreSettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreSettingsRepository extends JpaRepository<StoreSettings, Long> {

}
