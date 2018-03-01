package com.itt.kmt.services;

import com.itt.kmt.models.Role;
import com.itt.kmt.models.User;
import com.itt.kmt.repositories.RoleRepository;
import com.itt.kmt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Instance of the basic User Repository implementation.
     */
    @Autowired
    private UserRepository repository;

    /**
     * Instance of the basic role Repository implementation.
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
     * Gets all the Users.
     * @return List of all the users.
     */
    public List<User> getAllUsers() {
        return (List<User>) repository.findAll();
    }
    /**
     * Saves the User.
     * @param user User object to be saved.
     * @return Users saved.
     */
    public User save(final User user) {
        User existingUser = getUserByEmail(user.getEmail());
        if (existingUser == null) {
            user.setActive(true);
            return repository.save(user);
        } else {
            throw new RuntimeException("user already exists");
        }
    }
    /**
     * deletes User given the id.
     * @param id Id of the User.
     */
    public void deleteUserById(final String id) {
       User existingUser = repository.findOne(id);
       if (existingUser != null) {
           existingUser.setActive(false);

           repository.save(existingUser);
        } else {
           throw new RuntimeException("user with the id does not exist");
        }
    }
    /**
     * Changes the User status given the id and isActive status to be updated.
     * @param id Id of the User.
     * @param isActive isActive status of the User.
     * @return User.
     */
    public User changeUserStatus(final String id, final boolean isActive) {
       User existingUser = repository.findOne(id);

       if (existingUser != null && existingUser.isActive() != isActive) {
           existingUser.setActive(isActive);
           return repository.save(existingUser);
        } else if (existingUser == null) {
            throw new RuntimeException("user with the id does not exist");
        } else {
            throw new RuntimeException("Operation not supported");
        }
    }
    /**
     * Activates inactive User.
     * @param user user to be updated.
     * @param id id of the user to be updated.
     * @return user.
     */
    public User updateUser(final User user, final String id) {
        User existingUser = repository.findOne(id);

        if (existingUser != null && !existingUser.isActive()) {
            throw new RuntimeException("user is not active");
        } else if (existingUser != null) {

            if (!user.getFirstName().isEmpty()) {
                existingUser.setFirstName(user.getFirstName());
            }
            if (!user.getLastName().isEmpty()) {
                existingUser.setLastName(user.getLastName());
            }
            if (!user.getUserRole().isEmpty()) {
                existingUser.setUserRole(user.getUserRole());
            }
            return repository.save(existingUser);
        } else {
            throw new RuntimeException("user does not exist");
        }
    }

    /**
     * Get all the User Roles.
     * @return List of all the User Roles.
     */
    public List<Role> getUserRoles() {
       return (List<Role>) roleRepository.findAll();
    }
}