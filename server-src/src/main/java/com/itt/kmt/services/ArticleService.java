package com.itt.kmt.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itt.kmt.jwt.exception.UnauthorizedException;
import com.itt.kmt.models.Approve;
import com.itt.kmt.models.Article;
import com.itt.kmt.models.ArticleType;
import com.itt.kmt.models.Comment;
import com.itt.kmt.models.User;
import com.itt.kmt.models.UserResponse;
import com.itt.kmt.repositories.ArticleRepository;
import com.itt.kmt.repositories.ArticleTypeRepository;
import com.itt.kmt.repositories.CommentRepository;
import com.itt.utility.Constants;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
     * Instance of the basic comment repository.
     */
    @Autowired
    private CommentRepository commentRepository;
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
        if (article.getArticleType() != null) {
            article.setArticleType(getArticleTypeByID(article.getArticleType().toString()));
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

    /**
     * Get Article Type with ID.
     * @param id Article ID.
     * @return Article Type.
     */
    private ArticleType getArticleTypeByID(final String id) {
        return articleTypeRepository.findOne(id);
    }

    /**
     * updates the DBEntity(Article) from the database.
     * 
     * @param id of Article to be updated.
     * @param updatedArticle , Article object that needs to be updated.
     * @return Article
     */
    public Article updateArticle(final String id, final Article updatedArticle) {
        Article article = articleRepository.findOne(id);
        if (article == null) {
            throw new RuntimeException("Article not found");
        }
        return articleRepository.save(updateArticle(article, updatedArticle));
    }
    /**
     * Get all available articles the DBEntity(Article) from the database.
     * 
     * @param page Pageable object.
     * @param token jwt token of current session.
     * @return Page<Article> get list of articles.
     */
    public Page<Article> getAllArticles(final Pageable page, final String token) {

        //Get Logged in user
        User loggedInUser = userService.getLoggedInUser(token);

        // get articles according to permission
        if (loggedInUser.getUserRole().equals(Constants.ROLE_MANAGER)) {
            return articleRepository.findByCreatedByAndAndApprover(new ObjectId(loggedInUser.getId()),
                    new ObjectId(loggedInUser.getId()), page);
        } else if (loggedInUser.getUserRole().equals(Constants.ROLE_USER)) {
            return articleRepository.findByCreatedBy(new ObjectId(loggedInUser.getId()), page);
        }

        return articleRepository.findAll(page);
    }

    /**
     * Gets the Article given the id.
     * 
     * @param id ID of the Article.
     * @return Article object matching the id.
     */
    public Article getArticleById(final String id) {

        Article article = articleRepository.findOne(id);

        if (article == null) {
            throw new RuntimeException("No articles found");
        }
        return article;
    }


    /**
     * Update the Article after validation.
     *
     * @param article object of the Article.
     * @param updatedArticle object of the Article.
     * @return Article object matching the id.
     */
    private Article updateArticle(final Article article, final Article updatedArticle) {

        if (!updatedArticle.getTitle().isEmpty()) {
            article.setTitle(updatedArticle.getTitle());
        }
        if (updatedArticle.getRestricted() != null) {
            article.setRestricted(updatedArticle.getRestricted());
        }
        if (updatedArticle.getNeedsApproval() != null) {
            article.setNeedsApproval(updatedArticle.getNeedsApproval());
        }
        if (!updatedArticle.getDescription().isEmpty()) {
            article.setDescription(updatedArticle.getDescription());
        }
        if (updatedArticle.getNeedsApproval() != null) {
            article.setNeedsApproval(updatedArticle.getNeedsApproval());
        }
        if (updatedArticle.getApprover() != null) {
            article.setApprover(updatedArticle.getApprover());
        }
        article.setApproved(false);

        return article;
    }

    /**
     * Function to delete a article.
     * @param articleID which needs to be deleted.
     * @param token jwt token of logged in user.
     */
    public void delete(final String articleID, final String token) {
        User user = userService.getLoggedInUser(token);
        Article article = articleRepository.findOne(articleID);

        if (article == null) {
            throw new RuntimeException("No articles found");
        }

        if (user.getUserRole().equals(Constants.ROLE_USER) || user.getUserRole().equals(Constants.ROLE_MANAGER)) {
            Map<String, String> createdBy =  new ObjectMapper().convertValue(article.getCreatedBy(), Map.class);
            if (user.getId().equals(createdBy.get("id"))) {
                articleRepository.delete(articleID);
                // TODO : Send mail to user/manager they have deleted their created article.
                return;
            }
            throw new UnauthorizedException();
        }
        articleRepository.delete(articleID);
        // TODO : Send mail to approver and user article has been deleted.
    }


    /**
     * Function to approve or review a article.
     * @param approve object for article approval process.
     * @param articleID id of article.
     * @param token jwt token of user.
     * @return boolean status for approval.
     */
    public boolean articleApproval(final Approve approve, final String articleID, final String token) {

        Article article = articleRepository.findOne(articleID);

        if (article == null) {
            throw new RuntimeException("No articles found");
        }

        User loggedInUser = userService.getLoggedInUser(token);

        if (approve.isApproved()) {

            article.setApproved(true);
            List<Comment> comments = article.getComments();

            if (comments.size() > 0) {
                deleteComments(comments);
                article.setComments(null);
            }
            articleRepository.save(article);
            // TODO : Send mail to user for approval with comment, if there are any
            return true;
        }

        Comment savedComment = saveComment(approve, loggedInUser);

        List<Comment> comments = article.getComments();

        if (comments == null) {
            comments = new ArrayList<>();
        }

        if (savedComment != null) {
            comments.add(savedComment);
        }
        article.setComments(comments);


        articleRepository.save(article);
        // TODO : Send mail to user with review comments.

        return false;
    }

    /**
     * Function to save comment in DB.
     * @param approve object for article approval process.
     * @param user object for comment given by user.
     * @return Comment created object.
     */
    private Comment saveComment(final Approve approve, final User user) {

        Comment comment = null;

        if (!approve.isApproved() && approve.getComment() == null) {
            throw new RuntimeException("Found no comments");
        }
        if (approve.getComment() != null) {
            comment = new Comment();
            comment.setComment(approve.getComment());
            comment.setCommentedBy(convertUserIntoUserResponse(user));
            comment = commentRepository.save(comment);
        }
        return comment;
    }

    /**
     * Function to delete comment in DB.
     * @param comments List of comment article has.
     */
    private void deleteComments(final List<Comment> comments) {
        for (Comment comment: comments) {
            commentRepository.delete(comment.getId());
        }
    }
}
