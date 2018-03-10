package com.itt.kmt.repositories;

import com.itt.kmt.models.Article;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * DocumentRepository interface, declares the methods exposed by the repository.
 * Following default methods are provided by CrudRepository and can be used as
 * is without requiring any implementation. save, findOne, exists, findAll,
 * count, delete and deleteAll. Please refer to the javadocs for more details.
 */

public interface ArticleRepository extends PagingAndSortingRepository<Article, String> {
    /**
     * Finds a List of Article object that matches the name parameter. Spring
     * automatically formulates appropriate query depending on the name of the
     * method. findByXXX() method would look for a match for XXX property and
     * return the object instance.
     *
     * @param createdById id to be searched in createdBy user.
     * @param page object to get paginated Article list.
     * @return Article Object matching the search parameter.
     */
    @Query("{'createdBy._id': ?0}")
    Page<Article> findByCreatedBy(ObjectId createdById, Pageable page);

    /**
     * Finds a List of Article object that matches the name parameter. Spring
     * automatically formulates appropriate query depending on the name of the
     * method. findByXXX() method would look for a match for XXX property and
     * return the object instance.
     *
     * @param createdById id to be searched in createdBy user.
     * @param aprroverId id to be searched in approver user.
     * @param page object to get paginated Article list.
     * @return Article Object matching the search parameter.
     */
    @Query("{'$or':[ {'createdBy._id': ?0}, {'approver._id':?0} ] }")
    Page<Article> findByCreatedByAndAndApprover(ObjectId createdById, ObjectId aprroverId, Pageable page);
}
