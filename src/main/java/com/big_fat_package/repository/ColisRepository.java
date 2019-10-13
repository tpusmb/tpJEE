package com.big_fat_package.repository;

import com.big_fat_package.model.Colis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("colisRepository")
public interface ColisRepository extends JpaRepository<Colis, Long> {
    Colis findByIdColis(int idColis);
}
