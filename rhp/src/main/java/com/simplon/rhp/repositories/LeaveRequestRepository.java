package com.simplon.rhp.repositories;

import com.simplon.rhp.models.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
}
