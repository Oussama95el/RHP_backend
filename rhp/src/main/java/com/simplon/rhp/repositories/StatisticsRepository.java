package com.simplon.rhp.repositories;

import com.simplon.rhp.entities.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
}
