package com.pneedle.service;

import com.pneedle.model.RentRequest;
import com.pneedle.model.User;
import com.pneedle.repository.RentRequestRepository;
import com.pneedle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("rentRequestService")
public class RentRequestService {

    private UserRepository userRepository;
    private RentRequestRepository rentRequestRepository;

    @Autowired
    public RentRequestService(@Qualifier("userRepository") UserRepository userRepository,
                              @Qualifier("rentRequestRepository") RentRequestRepository rentRequestRepository) {
        this.userRepository = userRepository;
        this.rentRequestRepository = rentRequestRepository;
    }

    public RentRequest findByRentRequestID(int id) {
        return rentRequestRepository.findByRentRequestID(id);
    }

    public List<RentRequest> findAll() {
        return rentRequestRepository.findAll();
    }

    /**
     * Save the rent request in the database. Note the email address needs to be verified.
     * We save the new rent_request before returning it to make sure his ID has been initialized.
     *
     * @param rent_request rent request to add
     * @param target_user user
     * @return the new rent request add
     */
    public RentRequest saveRentRequest(RentRequest rent_request, User target_user) {
        rent_request.setUser_email(target_user.getEmail());
        // update the data base
        RentRequest saved_rentRequest = rentRequestRepository.save(rent_request);
        // add the request to the user
        target_user.add_new_rent_request(saved_rentRequest);
        userRepository.save(target_user);
        return saved_rentRequest;
    }

    /**
     * Edit the rent request in the data base.
     *
     * @param rent_request rent request to edit
     * @return the new rent request add
     */
    public RentRequest editRentRequest(RentRequest rent_request) {
        return rentRequestRepository.save(rent_request);
    }

}