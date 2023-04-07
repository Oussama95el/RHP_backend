package com.simplon.rhp.repositories;

import com.simplon.rhp.entities.AgentRh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRhRepository extends JpaRepository<AgentRh, Long> {
}
