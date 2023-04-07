package com.simplon.rhp.repositories;

import com.simplon.rhp.entities.PaySlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaySlipRepository extends JpaRepository<PaySlip, Long> {
}
