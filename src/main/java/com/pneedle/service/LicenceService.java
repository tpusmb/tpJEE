package com.pneedle.service;

import com.pneedle.model.Licence;
import com.pneedle.model.User;
import com.pneedle.repository.LicenceRepository;
import com.pneedle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("licenceService")
public class LicenceService {

    private UserRepository userRepository;
    private LicenceRepository licenceRepository;

    @Autowired
    public LicenceService(@Qualifier("userRepository") UserRepository userRepository,
                          @Qualifier("licenceRepository") LicenceRepository licenceRepository) {
        this.userRepository = userRepository;
        this.licenceRepository = licenceRepository;
    }

    public Licence findByLicenceKey(String key) {
        return licenceRepository.findByLicenceKey(key);
    }

    public List<Licence> findAll() {
        return licenceRepository.findAll();
    }

    /**
     * Save the licence in the data base. Note the email address need to be verify
     *
     * @param licence     licence to add
     * @param target_user destination user
     * @return the new licence add
     */
    public Licence saveLicence(Licence licence, User target_user) {

        licence.generateLicenceKey();
        // add the licence to the user
        target_user.add_new_licence(licence);
        // update the data base
        userRepository.save(target_user);
        return licenceRepository.save(licence);
    }

    /**
     * Edit the licence in the data base.
     *
     * @param licence     licence to edit
     * @return the new licence add
     */
    public Licence editLicence(Licence licence) {
        return licenceRepository.save(licence);
    }

}