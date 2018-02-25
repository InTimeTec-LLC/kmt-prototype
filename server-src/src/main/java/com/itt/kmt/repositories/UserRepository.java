package com.itt.jmtemplate.repositories;

import org.springframework.data.repository.CrudRepository;

import com.itt.jmtemplate.model.User;

public interface UserRepository extends CrudRepository<User, String> {
    /**
     * Finds a User object that matches the name parameter. Spring
     * automatically formulates appropriate query depending on the name of the
     * method. findByXXX() method would look for a match for XXX property and
     * return the object instance.
     *
     * @param email of the User
     * @return User Object matching the email parameter
     */
    User findByEmail(String email);
    /**
     * Finds all User objects. Spring
     * automatically formulates appropriate query depending on the name of the
     * method. findByXXX() method would look for a match for XXX property and
     * return the object instance.
     * @return Collection of User.
     */
    Iterable<User> findAll();
    /**
     * deletes the User specific to id. Spring
     * automatically formulates appropriate query depending on the name of the
     * method. findByXXX() method would look for a match for XXX property and
     * return the object instance.
     */
    void delete(String id);
}
