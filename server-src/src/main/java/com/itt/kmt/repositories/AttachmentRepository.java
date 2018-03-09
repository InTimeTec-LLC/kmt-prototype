package com.itt.kmt.repositories;

import com.itt.kmt.models.Attachment;
import com.itt.kmt.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
/**
 * UserRepostory interface, declares the methods exposed by the repository.
 * Following default methods are provided by CrudRepository and can be used as
 * is without requiring any implementation. save, findOne, exists, findAll,
 * count, delete and deleteAll. Please refer to the javadocs for more details.
 */
public interface AttachmentRepository extends CrudRepository<Attachment, String> {

    List<Attachment> findByArticleId(String articleId);
}
