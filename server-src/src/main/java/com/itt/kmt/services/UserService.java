package com.itt.kmt.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
     * @param active status of users to be retrieved.
     * @return List of all the users.
     */
    public List<User> getAllUsersByRolesAndStatus(final List<String> roles, final boolean active) {
        List<User> listOfUsersByRolesAndStatus = new ArrayList<User>();
        for (String role : roles) {
            List<User> users = repository.findByUserRoleAndActive(role, active);
            listOfUsersByRolesAndStatus.addAll(users);
        }
        return listOfUsersByRolesAndStatus;
    }
    /**
     * Gets all the active admins and managers.
     * @param roles roles by which active users are retrieved.
     * @return List of all the users.
     */
    public List<User> getAllUsersByRoles(final List<String> roles) {
        List<User> listOfUsersByRoles = new ArrayList<User>();
        for (String role : roles) {
            List<User> users = repository.findByUserRole(role);
            listOfUsersByRoles.addAll(users);
        }
        return listOfUsersByRoles;
    }
    /**
     * Gets all the Users by attribute passed.
     * @param userAttribute userAttribute by which active users are retrieved.
     * @return List of all the users.
     */
    public List<User> searchUsersByUserAttribute(final String userAttribute) {

        return repository.findByFirstNameOrLastNameOrEmail(userAttribute);
    }
    /**
     * Gets all the Users by attributes passed.
     * @param search parameter by which users are filtered.
     * @param role role by which active users are retrieved.
     * @param status status by which active users are retrieved.
     * @return List of all the users.
     */
    public List<User> filterUsersByStatusAndRole(final String search, final String role, final Boolean status) {

        List<User> users = null;
        List<String> roles = new ArrayList<String>();
        roles.add(role);

        if (role == null && status == null && search == null) {

            users = getAllUsers();

        } else if (role != null && search == null) {

            if (status != null) {
               users = getAllUsersByRolesAndStatus(roles, status);
            } else {
               users = getAllUsersByRoles(roles);
            }
        } else if (status != null && search == null) {
            users =  repository.findByActive(status);

        } else if (search != null) {
            users = repository.findByFirstNameOrLastNameOrEmail(search);
            List<User> filteredUsers = new ArrayList<User>();
            for (User user : users) {
                if (role != null && status != null) {

                    if (user.getUserRole().equals(role) && user.isActive() == status) {
                        filteredUsers.add(user);
                    }
                } else if (role == null && status != null) {
                    if (user.isActive() == status) {
                        filteredUsers.add(user);
                    }
                } else if (role != null && status == null) {
                    if (user.getUserRole().equals(role)) {
                        filteredUsers.add(user);
                    }
                } else {
                    filteredUsers = users;
                    break;
                }
            }
            users = filteredUsers;

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
    /**
     * Gets all the users based on page number and size.
     * @param users list of users to be filtered.
     * @param page number.
     * @param pageSize the number of elements to be retrieved.
     * @return List of users.
     */
    public List<User> arrangeUsersByCreatedDate(final List<User> users, 
           final Integer page, final Integer pageSize) {
        List<User> usersList = new ArrayList<User>();
        Collections.sort(users, new Comparator<User>() {
            public int compare(final User m1, final User m2) {
                return m2.getDateJoined().compareTo(m1.getDateJoined());
            }
        });
        if (page != null) {
            for (int i = page * Constants.PAGE_SIZE; i < page * Constants.PAGE_SIZE + Constants.PAGE_SIZE - 1; i++) {
                if (i < users.size()) {
                    usersList.add(users.get(i));
                }
                if (pageSize != null && i == pageSize - 1) {
                    break;
                }
            }
        } else {
            usersList = users;
        }
        return usersList;
    }
}