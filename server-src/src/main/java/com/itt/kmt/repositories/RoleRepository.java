package com.itt.kmt.repositories;

import com.itt.kmt.models.Role;
import com.itt.kmt.models.User;
import org.springframework.data.repository.CrudRepository;

/**
 * RoleRepostory interface, declares the methods exposed by the repository.
 * Following default methods are provided by CrudRepository and can be used as
 * is without requiring any implementation. save, findOne, exists, findAll,
 * count, delete and deleteAll. Please refer to the javadocs for more details.
 */
public interface RoleRepository extends CrudRepository<Role, String> {
    /**
     * Finds a Role object that matches the name parameter. Spring
     * automatically formulates appropriate query depending on the name of the
     * method. findByXXX() method would look for a match for XXX property and
     * return the object instance.
     *
     * @param role of the User
     * @return Role Object matching the role parameter
     */
    User findByRole(String role);

}
