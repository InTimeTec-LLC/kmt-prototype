package com.itt.kmt.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.itt.kmt.jwt.JWTUtil;
import com.itt.kmt.models.Role;
import com.itt.kmt.models.User;
import com.itt.kmt.repositories.RoleRepository;
import com.itt.kmt.repositories.UserRepository;
import com.itt.kmt.validators.UserValidator;
import com.itt.utility.EmailConstants;

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
     * Instance of mail service.
     */
    @Autowired
    private MailService mailService;
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
     * Gets the logged in User.
     * @param jwtToken jwtToken of the logged in User.
     * @return User who has logged in.
     */
    public User getLoggedInUser(final String jwtToken) {
        String email = JWTUtil.getemail(jwtToken);
        return getUserByEmail(email);
    }
    /**
     * Gets all the active admins and managers.
     * @param roles roles by which active users are retrieved.
     * @return List of all the users.
     */
    public List<User> getAllActiveUsersByRoles(final List<String> roles) {
        List<User> listOfActiveUsersByRoles = new ArrayList<User>();
        for (String role : roles) {
            List<User> users = repository.findByUserRole(role, true);
            listOfActiveUsersByRoles.addAll(users);
        }
        return listOfActiveUsersByRoles;
    }
    /**
     * Saves the User.
     * @param user User object to be saved.
     * @return Users saved.
     */
    public User save(final User user) {
        User existingUser = getUserByEmail(user.getEmail());
        if (existingUser == null) {
            user.setDateJoined(new Date());
            user.setActive(true);
            User savedUser = repository.save(user);
            mailService.sendUserCreatedMail(savedUser.getId(), EmailConstants.PARAM_PORTAL_LOGIN_LINK);
            return savedUser;
        } else {
            throw new RuntimeException("user already exists");
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
            throw new RuntimeException("Operation not permitted");
        }
    }
    /**
     * Updates User.
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

    /**
     * Get the User by ID.
     * @param id Id of the User.
     * @return User.
     */
    public User getUserByID(final String id) {
        User user = repository.findOne(id);
        if (user == null) {
            throw new RuntimeException("user with the id does not exist");
        }
        return user;
    }
    
    /**
     * Validate user.
     *
     * @param user the user
     * @param result the result
     * @return the string
     */
    public String validateUser(final User user, final BindingResult result) {
        
        UserValidator userValidator = new UserValidator();
        userValidator.validate(user, result);
        String errorMsg = "";
        
        if (result.hasErrors()) {
            
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                if (errorMsg.isEmpty()) {
                    errorMsg = error.getField() + " - " + error.getDefaultMessage();
                    continue;
                }
                
                errorMsg = errorMsg + "," + error.getField() + " - " + error.getDefaultMessage();
            }
        }
        return errorMsg;
    }
}