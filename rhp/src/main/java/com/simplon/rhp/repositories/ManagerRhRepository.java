package com.simplon.rhp.repositories;

import com.simplon.rhp.entities.ManagerRh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ManagerRhRepository extends JpaRepository<ManagerRh, Long> {
}
