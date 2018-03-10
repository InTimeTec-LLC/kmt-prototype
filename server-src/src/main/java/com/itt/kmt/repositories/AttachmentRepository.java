
package com.itt.kmt.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.itt.kmt.models.Attachment;

/**
 * UserRepostory interface, declares the methods exposed by the repository.
 * Following default methods are provided by CrudRepository and can be used as
 * is without requiring any implementation. save, findOne, exists, findAll,
 * count, delete and deleteAll. Please refer to the javadocs for more details.
 */
public interface AttachmentRepository extends CrudRepository<Attachment, String> {

    /**
     * Find by article id.
     *
     * @param articleId the article id
     * @return the list
     */
    List<Attachment> findByArticleId(String articleId);
}
