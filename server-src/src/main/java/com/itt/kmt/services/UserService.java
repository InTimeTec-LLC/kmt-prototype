package com.itt.kmt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itt.kmt.models.User;
import com.itt.kmt.repositories.UserRepository;

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
     * @param id Id of the User.
     * @return User object matching the id.
     */
    public User getUserById(final String id) {
        return repository.findOne(id);
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
       User existingUser = getUserById(id);
       if (existingUser != null) {
           existingUser.setActive(false);
           repository.save(existingUser);    
        } else {
           throw new RuntimeException("user with the id does not exist");
        }
    }
    /**
     * Activates inactive User.
     * @param user user to be updated.
     * @param id id of the user to be updated.
     * @return user.
     */
    public User updateUser(final User user, final String id) {
        User existingUser = getUserById(id);

        if (existingUser != null && !existingUser.isActive()) {
            throw new RuntimeException("user is not active");
        } else if (existingUser != null) {

            if (existingUser.getFirstName().isEmpty()) {
                existingUser.setFirstName(user.getFirstName());
            }
            if (existingUser.getLastName().isEmpty()) {
                existingUser.setLastName(user.getLastName());
            }
            if (existingUser.getUserRole().isEmpty()) {
                existingUser.setUserRole(user.getUserRole());
            }
            return repository.save(existingUser);
        } else {
            throw new RuntimeException("user does not exist");
        }
    }
}