package com.itt.kmt.services;

import com.itt.kmt.models.Role;
import com.itt.kmt.models.User;
import com.itt.kmt.repositories.RoleRepository;
import com.itt.kmt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class that acts as an intermediary between controller and the
 * database for all basic CRUD operations. The business logic should reside in
 * service class.
 * @author Rakshit Rajeev
 */
@Service
public class UserService {
    /**
     * Instance of the basic Repository implementation.
     */
    @Autowired
    private UserRepository repository;

    /**
     * Instance of the role Repository implementation.
     */
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Gets the User given the email.
     *
     * @param email Email of the User.
     * @return User object matching the email.
     */
    public User getUserByEmail(final String email) {
        return repository.findByEmail(email);
    }

    /**
     * Gets the User given the id.
     *
     * @param id Id of the User.
     * @return User object matching the id.
     */
    public User getUserById(final String id) {
        return repository.findOne(id);
    }

    /**
     * Gets all the Users.
     *
     * @return List of all the users.
     */
    public List<User> getAllUsers() {
        return (List<User>) repository.findAll();
    }

    /**
     * Saves the User.
     *
     * @param user User object to be saved.
     * @return Users saved.
     */
    public User save(final User user) {
        User existingUser = getUserByEmail(user.getEmail());
        if (existingUser == null) {
            user.setStatus("A");
            return repository.save(user);
        } else {
            throw new RuntimeException("user already exists");
        }
    }

    /**
     * deactivates the User given the email.
     *
     * @param email Email of the User.
     */
    public void deleteUserByEmail(final String email) {
        User existingUser = getUserByEmail(email);
        if (existingUser != null) {
            existingUser.setStatus("D");
            repository.save(existingUser);
        } else {
            throw new RuntimeException("user with the email does not exist");
        }
    }

    /**
     * deletes User given the id.
     *
     * @param id Id of the User.
     */
    public void deleteUserById(final String id) {
        repository.delete(id);
    }

    /**
     * Activates inactive User.
     *
     * @param user user to be updated.
     * @param id   id of the user to be updated.
     * @return user.
     */
    public User updateUser(final User user, final String id) {
        User existingUser = getUserById(id);

        if (existingUser != null && existingUser.getStatus() != null && !existingUser.getStatus().equals("A")) {
            throw new RuntimeException("user is not active");
        } else if (existingUser != null) {
            existingUser.setStatus(user.getStatus());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setUserRole(user.getUserRole());
            return repository.save(existingUser);
        } else {
            throw new RuntimeException("user does not exist");
        }
    }

    /**
     * Activates all User Roles.
     * @return Roles.
     */
    public List<String> getUserRoles() {
        List<Role> roleList = (List<Role>) roleRepository.findAll();
        List<String> rolesList = new ArrayList<>();
        if (roleList != null) {
            for (Role role: roleList) {
                rolesList.add(role.getRole());
            }
        }
        return rolesList;
    }
}