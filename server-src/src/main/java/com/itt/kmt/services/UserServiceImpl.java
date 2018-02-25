package com.itt.jmtemplate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itt.jmtemplate.model.Login;
import com.itt.jmtemplate.model.User;
import com.itt.jmtemplate.repositories.UserRepository;

/**
 * Service class that acts as an intermediary between controller and the
 * database for all basic CRUD operations. The business logic should reside in
 * service class.
 * 
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
    	User user = repository.findByEmail(email);
    	return user;
    }
    /**
     * Gets all the Users 
     * @return User object matching the email.
     */
    public List<User> getAllUsers() {
    	List<User> users = (List<User>) repository.findAll();
    	return users;
    }
    /**
     * Saves the User.
     * @param user User object to be saved.
     * @return Users saved.
     */
    public User save(final User user) {
	User existingUser = getUserByEmail(user.getEmail());
	if(existingUser == null) {
	    return repository.save(user);
	}
        return existingUser;
    }
    /**
     * checks the User is a valid user.
     * @param login object.
     * @return true if the user is a valid user.
     */
    public boolean isValidUser(final Login login) {
    	return getUserByEmail(login.getEmail()) != null;
    }
    /**
     * deletes User given the email.
     * @param email Email of the User.
     */
    public void deleteUserByEmail(final String email) {
    	User existingUser = getUserByEmail(email);
    	if(existingUser != null) {
    	   existingUser.setIsActive(false);
           repository.save(existingUser);    
    	}
    }
    /**
     * deletes User given the id.
     * @param id Id of the User.
     */
    public void deleteUserById(final String id) {
    	repository.delete(id);
    }
    /**
     * updates User.
     * @param user user to be updated.
     * @return 
     */
    public User updateUser(final User user) {
    	User existingUser = getUserByEmail(user.getEmail());

    	if(existingUser != null) {
    	   existingUser.setIsActive(user.getIsActive());
    	   existingUser.setFirstname(user.getFirstname());
    	   existingUser.setLastname(user.getLastname());
    	   existingUser.setPhone(user.getPhone());
    	   existingUser.setRoles(user.getRoles());
    	   existingUser.setAddress(user.getAddress());
           return repository.save(existingUser);
    	}
    	return existingUser;
    }
}