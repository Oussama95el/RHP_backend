package com.simplon.rhp.repositories;

import com.simplon.rhp.entities.AgentRh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AgentRhRepository extends JpaRepository<AgentRh, Long> {
}
