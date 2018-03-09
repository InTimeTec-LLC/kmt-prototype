package com.itt.kmt.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.itt.kmt.models.User;
/**
 * UserRepostory interface, declares the methods exposed by the repository.
 * Following default methods are provided by CrudRepository and can be used as
 * is without requiring any implementation. save, findOne, exists, findAll,
 * count, delete and deleteAll. Please refer to the javadocs for more details.
 */
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    /**
     * Finds a User object that matches the email parameter. Spring
     * automatically formulates appropriate query depending on the name of the
     * method. findByXXX() method would look for a match for XXX property and
     * return the object instance.
     *
     * @param email of the User
     * @return User Object matching the email parameter
     */
   User findByEmail(String email);
   /**
    * Finds list of User object that matches the userRole parameter. Spring
    * automatically formulates appropriate query depending on the name of the
    * method. findByXXX() method would look for a match for XXX property and
    * return the object instance.
    *
    * @param userRole of the User
    * @param active status of user
    * @return List<User> Object matching the userRole parameter
    */
   List<User> findByUserRoleAndActive(String userRole, boolean active);
   /**
    * Finds list of User object that matches the userRole parameter. Spring
    * automatically formulates appropriate query depending on the name of the
    * method. findByXXX() method would look for a match for XXX property and
    * return the object instance.
    *
    * @param userRole of the User
    * @return List<User> Object matching the userRole parameter
    */
   List<User> findByUserRole(String userRole);
   /**
    * Finds list of User object that matches the active parameter. Spring
    * automatically formulates appropriate query depending on the name of the
    * method. findByXXX() method would look for a match for XXX property and
    * return the object instance.
    *
    * @param active status of user
    * @return List<User> Object matching the userRole parameter
    */
   List<User> findByActive(boolean active);
   /**
    * Finds list of User object that matches the param0 parameter. Spring
    * automatically formulates appropriate query depending on the name of the
    * method. findByXXX() method would look for a match for XXX property and
    * return the object instance.
    *
    * @param param0 attribute of user
    * @return List<User> Object matching the userRole parameter
    */
   @Query("{ $or: [ {'firstName': { $regex: ?0 } }, {'lastName': { $regex: ?0 } }, {'email': { $regex: ?0 } } ]}")
   List<User> findByFirstNameOrLastNameOrEmail(String param0);

}
