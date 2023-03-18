package com.simplon.rhp.services;

import com.simplon.rhp.Exeptions.UsernameExistException;
import com.simplon.rhp.entities.Role;
import com.simplon.rhp.entities.User;
import com.simplon.rhp.repositories.RoleRepository;
import com.simplon.rhp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder bcryptEncoder;

    public Role createRoleIfNotFound(String name) {
        Role role = roleRepository.findByName(name).orElse(null);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }

    public boolean checkIfUsernameExist(String username) throws UsernameExistException {
        User user = userRepository.findByEmail(username).orElse(null);
        if (user != null) {
            throw new UsernameExistException("this username is already used");
        }
        return false;
    }

    public User save(User user) throws UsernameExistException {
        if (checkIfUsernameExist(user.getEmail())) {
            throw new UsernameExistException("this username is already used");
        }
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        user.setRoles(
                user.getRoles()
                        .stream()
                        .map(r -> {
                            Role role = createRoleIfNotFound(r.getName());
                            if (role == null) {
                                role = roleRepository.findByName(r.getName()).get();
                            }
                            return role;
                        }).collect(Collectors.toList())
        );
        return userRepository.save(user);
    }

}
