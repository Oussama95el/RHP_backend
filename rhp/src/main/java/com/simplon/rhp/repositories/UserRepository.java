package com.simplon.rhp.repositories;

import com.simplon.rhp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public class UserRepository  extends JpaRepository<User,Long> {
}
