package com.pneedle.repository;

import com.pneedle.model.RentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("rentRequestRepository")
public interface RentRequestRepository extends JpaRepository<RentRequest, Long> {
    RentRequest findByRentRequestID(int id);
}
