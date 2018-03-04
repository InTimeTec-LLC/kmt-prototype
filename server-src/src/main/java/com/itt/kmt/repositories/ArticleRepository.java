package com.itt.kmt.repositories;

import com.itt.kmt.models.Article;
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
     * @param name key to be searched in name of the Article
     * @param content key to be searched in content of Article
     * @return Article Object matching the search parameter
     */

}
