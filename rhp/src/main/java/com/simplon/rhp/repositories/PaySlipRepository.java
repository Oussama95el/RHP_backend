package com.simplon.rhp.repositories;

import com.simplon.rhp.models.PaySlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PaySlipRepository extends JpaRepository<PaySlip, Long> {
}
