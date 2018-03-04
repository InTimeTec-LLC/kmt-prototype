package com.itt.kmt.services;

import com.itt.kmt.models.Article;
import com.itt.kmt.models.ArticleType;
import com.itt.kmt.models.User;
import com.itt.kmt.models.UserResponse;
import com.itt.kmt.repositories.ArticleRepository;
import com.itt.kmt.repositories.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service class that acts as an intermediary between controller and the
 * database for all basic CRUD operations. The business logic should reside in
 * service class.
 * 
 * @author Ashish Y
 */
@Service
public class ArticleService {

    /**
     * Instance of the basic Repository implementation.
     */
    @Autowired
    private ArticleRepository articleRepository;

    /**
     * Instance of user service.
     */
    @Autowired
    private UserService userService;

    /**
     * Instance of the basic Article Type repository.
     */
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    /**
     * Saves the Article to database.
     * 
     * @param article Article object to be saved
     * @return Article object with userResponse included.
     */
    public Article save(final Article article) {

        if (article.getCreatedBy() != null) {
            User createdByUser = userService.getUserByID(article.getCreatedBy().toString());
            article.setCreatedBy(convertUserIntoUserResponse(createdByUser));
        }
        if (article.getApprover() != null) {
            User approver = userService.getUserByID(article.getApprover().toString());
            article.setApprover(convertUserIntoUserResponse(approver));
        }

        return articleRepository.save(article);
    }

    /**
     * Convert user object into article response user format.
     *
     * @param user User object to be converted
     * @return article user response details.
     */
    private UserResponse convertUserIntoUserResponse(final User user) {
        UserResponse userResponse = new UserResponse(user.getId(),
                user.getFirstName(), user.getLastName(), user.getEmail());
        return userResponse;
    }

    /**
     * Get all the Article Types.
     * @return List of all the Article Type.
     */
    public List<ArticleType> getArticleTypes() {
        return (List<ArticleType>) articleTypeRepository.findAll();
    }
}
