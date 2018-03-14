package com.itt.kmt.repositories;

import com.itt.kmt.models.Article;
import com.itt.kmt.models.KBArticle;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


/**
 * DocumentRepository interface, declares the methods exposed by the repository.
 * Following default methods are provided by CrudRepository and can be used as
 * is without requiring any implementation. save, findOne, exists, findAll,
 * count, delete and deleteAll. Please refer to the javadocs for more details.
 */

public interface ArticleRepository extends PagingAndSortingRepository<Article, String> {
    /**
     * Implements a custom method that returns a pageable Article.
     * @param createdById id to be searched in createdBy user.
     * @param page object to get paginated Article list.
     * @return Article Object matching the search parameter.
     */
    @Query("{'createdBy._id': ?0}")
    Page<Article> findByCreatedBy(ObjectId createdById, Pageable page);

    /**
     * Implements a custom method that returns a pageable Article.
     * @param createdById id to be searched in createdBy user.
     * @param aprroverId id to be searched in approver user.
     * @param page object to get paginated Article list.
     * @return Article Object matching the search parameter.
     */
    @Query("{'$or':[ {'createdBy._id': ?0}, {'approver._id':?0} ] }")
    Page<Article> findByCreatedByAndAndApprover(ObjectId createdById, ObjectId aprroverId, Pageable page);

    /**
     * Implements a custom method that returns a pageable Article.
     * @param createdById id to be searched in createdBy user.
     * @param types article type to be searched.
     * @param status , to get article based on article published or unpublished.
     * @param search , key to search article.  
     * @param page object to get paginated Article list.
     * @return Article Object matching the search parameter.
     */
    @Query("{'$and':[ {'createdBy._id': ?0}, { 'articleType._id': { $in: ?1  } }, {approved: { $in: ?2  }},"
            + " { 'title' : {$regex:?3, $options: 'i' } }] }")
    Page<Article> findArticlesByCreatedBy(ObjectId createdById, List<ObjectId> types,
            List<Boolean> status, String search , Pageable page);

    /**
     * Implements a custom method that returns a pageable Article.
     * @param filter , the filter to be applied
     * @param createdById id to be searched in createdBy user.
     * @param types article type to be searched.
     * @param status , to get article based on article published or unpublished.
     * @param search , key to search article.  
     * @param page object to get paginated Article list.
     * @return Article Object matching the search parameter.
     */
    @Query("{'$and':[ {?0: ?1}, { 'articleType._id': { $in: ?2  } }, {approved: { $in: ?3  }}, "
            + "{ 'title' : {$regex:?4, $options: 'i' } }] }")
    Page<Article> findArticlesByFilter(String filter , ObjectId createdById, List<ObjectId> types,
            List<Boolean> status, String search , Pageable page);

    /**
     * Implements a custom method that returns a pageable Article.
     * @param createdById id to be searched in createdBy user.
     * @param type article type to be searched.
     * @param status , to get article based on article published or unpublished.
     * @param search , key to search article.  
     * @param page object to get paginated Article list.
     * @return Article Object matching the search parameter.
     */
    @Query("{'$and':[ {'approver._id': ?0}, {'articleType._id': {$regex:?1, $options:'i'}},"
            + "{approved: ?2}, { 'title' : {$regex:?3, $options:'i'} }] }")
    Page<Article> findArticlesByApprover(ObjectId createdById, ObjectId type, Boolean status, 
            String search, Pageable page);

    /**
     * Implements a custom method that returns a pageable Article.
     * @param types article type to be searched.
     * @param status , to get article based on article published or unpublished.
     * @param search , key to search article.  
     * @param page object to get paginated Article list.
     * @return Article Object matching the search parameter.
     */
    @Query("{'$and':[ { 'articleType._id': { $in: ?0  } }"
            + ", {approved: { $in: ?1  }}, { 'title' : {$regex:?2, $options: 'i' } }] }")
    Page<Article> findAllArticles(List<ObjectId> types, List<Boolean> status, String search, Pageable page);

    /**
     * Implements a custom method that returns a pageable Article.
     * @param createdById id to be searched in createdBy user.
     * @param type article type to be searched.
     * @param status , to get article based on article published or unpublished.
     * @param search , key to search article.  
     * @param page object to get paginated Article list.
     * @return Article Object matching the search parameter.
     */
    @Query("{'$or':[ {'$and':[ {'createdBy._id': ?0}, { 'articleType._id': { $in: ?1  } }, {approved: { $in: ?2  }},"
            + " { 'title' : {$regex:?3, $options: 'i' } }] }"
            + ", {'$and':[ {'approver._id': ?0}, { 'articleType._id': { $in: ?1  } },"
            + " {approved: { $in: ?2  }}, { 'title' : {$regex:?3, $options: 'i' } }] } ] }")
    Page<Article> findAllArticles(ObjectId createdById, List<ObjectId> type, List<Boolean> status, String search,
            Pageable page);


    /**
     * Finds a List of Article object that matches the name parameter. Spring
     * automatically formulates appropriate query depending on the name of the
     * method. findByXXX() method would look for a match for XXX property and
     * return the object instance.
     *
     * @param search string to be searched in articles.
     * @param page object to get paginated Article list.
     * @param approved approval status.
     * @return Article Object matching the search parameter.
     */
    @Query("{ '$or': [ {'title': { $regex: ?0, $options: 'i' } }"
            + ", {'description': { $regex: ?0, $options: 'i' } } ] , 'approved': ?1 }")
    Page<KBArticle> findByTitleAndDescriptionAndApproved(String search, Boolean approved, Pageable page);

}
