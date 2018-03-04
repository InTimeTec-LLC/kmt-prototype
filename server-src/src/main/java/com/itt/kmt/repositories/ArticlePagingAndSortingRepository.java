package com.itt.kmt.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.itt.kmt.models.Article;


/**
 * BookPagingAndRepostory interface, declares the methods exposed by the
 * repository. that supports pagination and sorting. Following default methods
 * are provided by PagingAndRepository and can be used as is without requiring
 * any implementation. findAll(Sort), findAll(Pageable)
 */

public interface ArticlePagingAndSortingRepository extends PagingAndSortingRepository<Article, String> {
 
    /**
     * Implements a custom method that returns a pageable Book Spring *
     * automatically formulates appropriate query depending on the name of the
     * method. findByXXX() method would look for a match for XXX property and
     * return the object instance.
     *
     * @param title Title of the Book
     * @param pageable pageable instance
     * @return Page<Book> Page of Book objects as determined by Pageable
     *         parameter
     */
    List<Article> findByApprover(String assigned, Pageable pageable);
    /**
     * Implements a custom method that returns a pageable Book Spring *
     * automatically formulates appropriate query depending on the name of the
     * method. findByXXX() method would look for a match for XXX property and
     * return the object instance.
     *
     * @param title Title of the Book
     * @param pageable pageable instance
     * @return Page<Book> Page of Book objects as determined by Pageable
     *         parameter
     */
    List<Article> findByCreatedBy(String createdBy, Pageable pageable);
    /**
     * Implements a custom method that returns a pageable Book Spring *
     * automatically formulates appropriate query depending on the name of the
     * method. findByXXX() method would look for a match for XXX property and
     * return the object instance.
     *
     * @param title Title of the Book
     * @param pageable pageable instance
     * @return Page<Book> Page of Book objects as determined by Pageable
     *         parameter
     */
    //List<Article> findByApproverAndCreatedBy(String assigned, String createdBy);

}
