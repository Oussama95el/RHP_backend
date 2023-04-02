package com.simplon.rhp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AgentRh extends JpaRepository<AgentRh, Long> {
}
