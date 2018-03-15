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
 * ArticleRepository interface, declares the methods exposed by the repository.
 * that supports pagination and sorting. Following default methods
 * are provided by PagingAndSortingRepository and can be used as is without requiring
 * any implementation. findAll(Sort), findAll(Pageable)
 */
public interface ArticleRepository extends PagingAndSortingRepository<Article, String> {
    /**
     * Implements a custom method that returns a pageable Article.
     * based on creator of the article, with search on type,status
     * and search key.
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
     * based on either of the filter (creator/approver) of the article ,
     * with search on type,status and search key.
     * @param filter , the filter to be applied
     * @param userId id to be searched in createdBy user.
     * @param types article type to be searched.
     * @param status , to get article based on article published or unpublished.
     * @param search , key to search article.  
     * @param page object to get paginated Article list.
     * @return Article Object matching the search parameter.
     */
    @Query("{'$and':[ {?0: ?1}, { 'articleType._id': { $in: ?2  } }, {approved: { $in: ?3  }}, "
            + "{ 'title' : {$regex:?4, $options: 'i' } }] }")
    Page<Article> findArticlesByFilter(String filter , ObjectId userId, List<ObjectId> types,
            List<Boolean> status, String search , Pageable page);

    /**
     * Implements a custom method that returns a pageable Article. 
     * based on both the possible filters (creator/approver) of
     * article , with search on type,status and search key.
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
     * based on both the possible filters (creator/approver) of
     * article , with search on type,status and search key.
     * @param userId id to be searched in createdBy user.
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
    Page<Article> findAllArticles(ObjectId userId, List<ObjectId> type, List<Boolean> status, String search,
            Pageable page);


    /**
     * Implements a custom method that returns a pageable Article.
     * based on search elements , which searches on title, description, and approved status of article.
     * @param search string to be searched in articles.
     * @param page object to get paginated Article list.
     * @param approved approval status.
     * @return Article Object matching the search parameter.
     */
    @Query("{ '$or': [ {'title': { $regex: ?0, $options: 'i' } }"
            + ", {'description': { $regex: ?0, $options: 'i' } } ] , 'approved': ?1 }")
    Page<KBArticle> findByTitleAndDescriptionAndApproved(String search, Boolean approved, Pageable page);

}
