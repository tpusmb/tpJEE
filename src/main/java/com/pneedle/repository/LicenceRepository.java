package com.pneedle.repository;

import com.pneedle.model.Licence;
import com.pneedle.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("licenceRepository")
public interface LicenceRepository extends JpaRepository<Licence, Long> {
    Licence findByLicenceKey(String key);
}
