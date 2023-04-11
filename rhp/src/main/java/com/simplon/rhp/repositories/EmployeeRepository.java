package com.simplon.rhp.repositories;

import com.simplon.rhp.entities.Employee;
import com.simplon.rhp.entities.Profile;
import com.simplon.rhp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findEmployeeByUser(User id);

    Employee findEmployeeByProfile(Profile profile);
}
