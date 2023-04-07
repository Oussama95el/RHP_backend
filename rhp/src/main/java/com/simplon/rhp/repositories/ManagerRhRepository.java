package com.simplon.rhp.repositories;

import com.simplon.rhp.entities.ManagerRh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRhRepository extends JpaRepository<ManagerRh, Long> {
}
