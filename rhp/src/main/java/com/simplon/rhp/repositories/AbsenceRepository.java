package com.simplon.rhp.repositories;

import com.simplon.rhp.entities.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
}
