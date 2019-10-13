package com.big_fat_package.service;

import com.big_fat_package.model.Colis;
import com.big_fat_package.repository.ColisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("colisService")
public class ColisService {

    private ColisRepository colisRepository;

    @Autowired
    public ColisService(@Qualifier("colisRepository") ColisRepository colisRepository) {
        this.colisRepository = colisRepository;
    }

    public Colis findByIdColis(int idColis) {
        return colisRepository.findByIdColis(idColis);
    }

    public List<Colis> findAll() {
        return colisRepository.findAll();
    }

    /**
     * Add new colis into database
     *
     * @param colis colis to add
     * @return the new Colis
     */
    public Colis saveColis(Colis colis) {
        return colisRepository.save(colis);
    }

    /**
     * Edit the colis in the data base.
     *
     * @param colis colis to edit
     * @return the new colis add
     */
    public Colis editColis(Colis colis) {
        return colisRepository.save(colis);
    }

    /**
     * Delete a colis into the database
     * @param colis colis to remove
     */
    public void deleteColis(Colis colis){
        colisRepository.delete(colis);
    }
}