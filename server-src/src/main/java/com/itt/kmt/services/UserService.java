package com.itt.kmt.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.itt.kmt.jwt.JWTUtil;
import com.itt.kmt.models.Role;
import com.itt.kmt.models.User;
import com.itt.kmt.repositories.RoleRepository;
import com.itt.kmt.repositories.UserRepository;
import com.itt.kmt.validators.UserValidator;
import com.itt.utility.Constants;
import com.itt.utility.EmailConstants;

import lombok.extern.slf4j.Slf4j;


/**
 * Service class that acts as an intermediary between controller and the
 * database for all basic CRUD operations. The business logic should reside in
 * service class.
 * @author Rakshit Rajeev
 */
@Slf4j
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
     * @param email Email of the User.
     * @param page Page consisting Users.
     * @return List of all the users.
     */
    public Page<User> getAllUsers(final String email, final Pageable page) {
        return repository.findAll(email, page);
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
     * Gets all the users by role and status.
     * @param role role by which active users are retrieved.
     * @param active status of users to be retrieved.
     * @param loggedInUserEmail email of logged in user.
     * @param page Page consisting users.
     * @return Page<User> page of users.
     */
    public Page<User> getAllUsersByRolesAndStatus(final String role, final boolean active, 
                   final String loggedInUserEmail, final Pageable page) {

        Page<User> users = repository.findByUserRoleAndActive(role, active, loggedInUserEmail, page);

        return users;
    }
    /**
     * Gets all the users by role.
     * @param role role by which users are retrieved.
     * @param loggedInUserEmail email of logged in user.
     * @param page Page consisting users.
     * @return Page<User> page of users.
     */
    public  Page<User> getAllUsersByRoles(final String role, final String loggedInUserEmail, final Pageable page) {
        Page<User> users = repository.findByUserRole(role, loggedInUserEmail, page);
        return users;
    }
    /**
     * Gets all the Users by attributes passed.
     * @param search parameter by which users are filtered.
     * @param role role by which active users are retrieved.
     * @param status status by which active users are retrieved.
     * @param loggedInUserEmail email of logged in user.
     * @param page Page consisting users.
     * @return Page<User> page of users.
     */
    public Page<User> filterUsersByStatusAndRole(final String search, final String role, final Boolean status, 
              final String loggedInUserEmail, final Pageable page) {

        Page<User> users = null;

        if ((role == null || role.equals(Constants.EMPTY_STRING)) && status == null 
               && (search == null || search.equals(Constants.EMPTY_STRING))) {

            users = getAllUsers(loggedInUserEmail, page);

        } else if ((role != null && !Constants.EMPTY_STRING.equals(role)) 
                && (search == null || search.equals(Constants.EMPTY_STRING))) {

            if (status != null) {
               users = getAllUsersByRolesAndStatus(role, status, loggedInUserEmail, page);
            } else {
               users = getAllUsersByRoles(role, loggedInUserEmail, page);
            }

        } else if (status != null && (search == null || search.equals(Constants.EMPTY_STRING))) {
            users =  repository.findByActive(status, loggedInUserEmail, page);

        } else if (search != null && !Constants.EMPTY_STRING.equals(search)) {

            if ((role != null && !Constants.EMPTY_STRING.equals(role)) && status != null) {

                users = repository.findByFirstNameOrLastNameOrEmailAndActiveAndUserRole(search, loggedInUserEmail, 
                          status, role, page);

            } else if ((role == null || role.equals(Constants.EMPTY_STRING)) && status != null) {

                users = repository.findByFirstNameOrLastNameOrEmailAndActive(search, loggedInUserEmail, status, page);

            } else if ((role != null && !Constants.EMPTY_STRING.equals(role)) && status == null) {

                users = repository.findByFirstNameOrLastNameOrEmailAndUserRole(search, loggedInUserEmail, role, page);

            } else {
                users = repository.findByFirstNameOrLastNameOrEmail(search, loggedInUserEmail, page);
            }

        }
        return users;
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
            try {
                mailService.sendUserCreatedMail(savedUser.getId(), EmailConstants.PARAM_PORTAL_LOGIN_LINK);
            } catch (MailException | InterruptedException e) {
                log.error(e.getMessage());
            }
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
           try {
            mailService.sendUserActivateMail(existingUser, isActive);
        } catch (MailException | InterruptedException e) {
            log.error(e.getMessage());
        }
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

            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setUserRole(user.getUserRole());
            existingUser.setPassword(user.getPassword());

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
    /**
     * Sets the user session.
     *
     * @param jwtToken the jwt token
     * @param status the status
     */
    public void setUserSession(final String jwtToken, final boolean status) {
        User loggedInUser = getLoggedInUser(jwtToken);
        
        if (loggedInUser != null) {
            loggedInUser.setSession(status);
            repository.save(loggedInUser);
         } else {
             throw new RuntimeException("user doesnot exist");
         }
     }
}