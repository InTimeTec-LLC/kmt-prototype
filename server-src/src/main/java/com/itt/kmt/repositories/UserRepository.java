
package com.itt.kmt.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.itt.kmt.models.User;

/**
 * The Interface UserRepository.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

    /**
     * Finds a User object that matches the name parameter. Spring automatically
     * formulates appropriate query depending on the name of the method.
     * findByXXX() method would look for a match for XXX property and return the
     * object instance.
     *
     * @param email of the User
     * @return User Object matching the email parameter
     */
    User findByEmail(String email);

    /**
     * Finds all User objects. Spring automatically formulates appropriate query
     * depending on the name of the method. findByXXX() method would look for a
     * match for XXX property and return the object instance.
     * 
     * @return Collection of User.
     */
    Iterable<User> findAll();

    /**
     * deletes the User specific to id. Spring automatically formulates
     * appropriate query depending on the name of the method. findByXXX() method
     * would look for a match for XXX property and return the object instance.
     *
     * @param id the id
     */
    void delete(String id);
}
