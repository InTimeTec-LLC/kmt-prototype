package com.itt.kmt.services;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itt.kmt.jwt.exception.UnauthorizedException;
import com.itt.kmt.models.Approve;
import com.itt.kmt.models.Article;
import com.itt.kmt.models.ArticleFilter;
import com.itt.kmt.models.ArticleType;
import com.itt.kmt.models.Attachment;
import com.itt.kmt.models.Comment;
import com.itt.kmt.models.KBArticle;
import com.itt.kmt.models.User;
import com.itt.kmt.models.UserResponse;
import com.itt.kmt.repositories.ArticleRepository;
import com.itt.kmt.repositories.ArticleTypeRepository;
import com.itt.kmt.repositories.CommentRepository;
import com.itt.utility.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service class that acts as an intermediary between controller and the
 * database for all basic CRUD operations. The business logic should reside in
 * service class.
 * 
 * @author Ashish Y
 */
@Slf4j
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
     * Instance of user service.
     */
    @Autowired
    private MailService mailService;
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
    
    /** The attachment service. */
    @Autowired
    private AttachmentService attachmentService;

    /**
     * Constant for id.
     */
    private static final String ID = "id";

    /**
     * Constant for article not found comment.
     */
    private static final String ARTICLE_NOT_FOUND = "Article not found";


    /** Description length for showing in knowledge base search. */
    @Value("${description.length}")
    private Integer descriptionLength;


    /**
     * Saves the Article to database.
     * 
     * @param article
     *            Article object to be saved
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
     * @param user
     *            User object to be converted
     * @return article user response details.
     */
    private UserResponse convertUserIntoUserResponse(final User user) {
        UserResponse userResponse = new UserResponse(user.getId(), user.getFirstName(), user.getLastName(),
                user.getEmail());
        return userResponse;
    }

    /**
     * Get all the Article Types.
     * 
     * @return List of all the Article Type.
     */
    public List<ArticleType> getArticleTypes() {
        return (List<ArticleType>) articleTypeRepository.findAll();
    }

    /**
     * Get Article Type with ID.
     * 
     * @param id
     *            Article ID.
     * @return Article Type.
     */
    private ArticleType getArticleTypeByID(final String id) {
        return articleTypeRepository.findOne(id);
    }

    /**
     * updates the DBEntity(Article) from the database.
     * 
     * @param id
     *            of Article to be updated.
     * @param updatedArticle
     *            , Article object that needs to be updated.
     * @return Article
     */
    public Article updateArticle(final String id, final Article updatedArticle) {
        Article article = articleRepository.findOne(id);
        if (article == null) {
            throw new RuntimeException(ARTICLE_NOT_FOUND);
        }
        List<Attachment> attachments = updatedArticle.getAttachments();
        updatedArticle.setAttachments(null);
        Article dbArticle = articleRepository.save(updateArticle(article, updatedArticle));
        
        // linking attached attachments
        
        if (attachments != null && attachments.size() > 0) {
            attachmentService.updateAttachmentWithArticleId(attachments, dbArticle.getId());
        }
        
        return dbArticle;
    }

    /**
     * Get all available articles the DBEntity(Article) from the database.
     * 
     * @param page
     *            Pageable object.
     * @param token
     *            jwt token of current session.
     * @return Page<Article> get list of articles.
     */
    public Page<Article> getAllArticles(final Pageable page, final String token) {

        // Get Logged in user
        User loggedInUser = userService.getLoggedInUser(token);

        // get articles according to permission
        if (loggedInUser.getUserRole().equals(Constants.ROLE_MANAGER)) {
            Page<Article> articles = articleRepository.findByCreatedByAndAndApprover(new ObjectId(loggedInUser.getId()),
                    new ObjectId(loggedInUser.getId()), page);
            for (Article article :articles) {
                List<Attachment> attachments = attachmentService.getArticleAttachments(article.getId());
                article.setAttachments(attachments);
            }
        } else if (loggedInUser.getUserRole().equals(Constants.ROLE_USER)) {
            Page<Article> articles = articleRepository.findByCreatedBy(new ObjectId(loggedInUser.getId()), page);
            for (Article article :articles) {
                List<Attachment> attachments = attachmentService.getArticleAttachments(article.getId());
                article.setAttachments(attachments);
            }
            return articles;
        }

        Page<Article> articles = articleRepository.findAll(page);
        for (Article article :articles) {
            List<Attachment> attachments = attachmentService.getArticleAttachments(article.getId());
            article.setAttachments(attachments);
        }
        return articles;
    }

    /**
     * Gets the Article given the id.
     * 
     * @param id
     *            ID of the Article.
     * @param token
     *            jwt token of the user.
     * @return Article object matching the id.
     */
    public Article getArticleById(final String id, final String token) {

        Article article = articleRepository.findOne(id);

        if (article == null) {
            throw new RuntimeException(ARTICLE_NOT_FOUND);
        }

        String loggedInUserRole = userService.getLoggedInUser(token).getUserRole();

        if (article.getRestricted()) {
            Map<String, String> createdBy = new ObjectMapper().convertValue(article.getCreatedBy(), Map.class);
            String createdByUserRole = userService.getUserByID(createdBy.get(ID)).getUserRole();
            switch (loggedInUserRole) {
                case Constants.ROLE_USER:
                    if (createdByUserRole.equals(Constants.ROLE_MANAGER)
                            || createdByUserRole.equals(Constants.ROLE_ADMIN)) {
                        throw new RuntimeException(Constants.UNAUTHORIZED_ACCESS_MSG);
                    }
        
        List<Attachment> attachments = attachmentService.getArticleAttachments(article.getId());
        article.setAttachments(attachments);
        
                    return article;
                case Constants.ROLE_MANAGER:
                    if (createdByUserRole.equals(Constants.ROLE_ADMIN)) {
                        throw new RuntimeException(Constants.UNAUTHORIZED_ACCESS_MSG);
                    }
                    return article;
                case Constants.ROLE_ADMIN:
                    return article;
                default : throw new RuntimeException(Constants.UNAUTHORIZED_ACCESS_MSG);
            }
        }

        return article;
    }

    /**
     * Update the Article after validation.
     *
     * @param article
     *            object of the Article.
     * @param updatedArticle
     *            object of the Article.
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
            User approver = userService.getUserByID(updatedArticle.getApprover().toString());
            article.setApprover(convertUserIntoUserResponse(approver));
        }
        if (updatedArticle.getLastModifiedBy() != null) {
            User lastModifedBy = userService.getUserByID(updatedArticle.getLastModifiedBy().toString());
            article.setLastModifiedBy(convertUserIntoUserResponse(lastModifedBy));
        }
        article.setApproved(false);

        return article;
    }

    /**
     * Function to delete a article.
     * 
     * @param articleID
     *            which needs to be deleted.
     * @param token
     *            jwt token of logged in user.
     */
    public void delete(final String articleID, final String token) {
        User user = userService.getLoggedInUser(token);
        Article article = articleRepository.findOne(articleID);

        if (article == null) {
            throw new RuntimeException(ARTICLE_NOT_FOUND);
        }

        if (user.getUserRole().equals(Constants.ROLE_USER) || user.getUserRole().equals(Constants.ROLE_MANAGER)) {
            Map<String, String> createdBy = new ObjectMapper().convertValue(article.getCreatedBy(), Map.class);
            if (user.getId().equals(createdBy.get(ID))) {
                articleRepository.delete(articleID);
                try {
                    mailService.sendDeleteKAMail(article, false);
                } catch (MailException | InterruptedException e) {
                    log.error(e.getMessage());
                }
                
                // Deleting attached attachments
                attachmentService.deleteAttachmentWithArticleId(articleID);
                return;
            }
            throw new UnauthorizedException();
        }

        articleRepository.delete(articleID);
        
        // Deleting attached attachments
        attachmentService.deleteAttachmentWithArticleId(articleID);

        try {
            mailService.sendDeleteKAMail(article, true);
        } catch (MailException | InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Function to approve or review a article.
     * 
     * @param approve
     *            object for article approval process.
     * @param articleID
     *            id of article.
     * @param token
     *            jwt token of user.
     * @return boolean status for approval.
     */
    public boolean articleApproval(final Approve approve, final String articleID, final String token) {

        Article article = articleRepository.findOne(articleID);

        if (article == null) {
            throw new RuntimeException(ARTICLE_NOT_FOUND);
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
            try {
                mailService.sendKAapproveAndPublishMail(article, approve);
            } catch (MailException | InterruptedException e) {
                log.error(e.getMessage());
            }
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
        try {
            mailService.sendKAReviewdMail(article, approve);
        } catch (MailException | InterruptedException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    /**
     * Function to save comment in DB.
     * 
     * @param approve
     *            object for article approval process.
     * @param user
     *            object for comment given by user.
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
     * 
     * @param comments
     *            List of comment article has.
     */
    private void deleteComments(final List<Comment> comments) {
        for (Comment comment : comments) {
            commentRepository.delete(comment.getId());
        }
    }

    /**
     * Function to get articles based on search and filters.
     * @param filter , the key to be filter the article.
     * @param type , the type of the article to be fetched.
     * @param status , the status of the article to be fetched.
     * @param search , the key to be searched in title of the article.
     * @param page Pageable object.
     * @param token jwt token of current session.
     * @return Page<Article> get list of articles.
     */
    public Page<Article> getAllWithFiltersAndSearch(final ArticleFilter filter, final String type, final String status,
            final String search, final Pageable page, final String token) {
        String filterBy = getFilter(filter);
        User loggedInUser = userService.getLoggedInUser(token);
        List<ObjectId> articleTypes = getArticleTypeObject(type); 
        List<Boolean> allStatus = getApprovalStatus(status);
        return getAllWithFiltersAndSearch(loggedInUser.getUserRole(), new ObjectId(loggedInUser.getId()), 
                filterBy, articleTypes, allStatus, search, page);
    }    


    /**
     * private method to get articles based on search and filters.
     * @param userRole , go get articles based on Role of the logged in user.
     * @param userId , the id of the user.
     * @param filter , the key to be filtered.
     * @param type , the list of articletype .
     * @param status , the list of valid status.
     * @param search , the key to be searched in title of the article.
     * @param page Pageable object.
     * @return Page<Article> get list of articles.
     */
    private Page<Article> getAllWithFiltersAndSearch(final String userRole, final ObjectId userId, final String filter,
            final List<ObjectId> type, final List<Boolean> status, final String search, final Pageable page) {
        switch (userRole) {
        case Constants.ROLE_USER: return articleRepository.findArticlesByCreatedBy(userId, type, status, search, page);
        case Constants.ROLE_MANAGER:
            if (StringUtils.isBlank(filter)) {
                return articleRepository.findAllArticles(userId, type, status, search, page);
            } else {
                return articleRepository.findArticlesByFilter(filter, userId, type, status, search, page);
            }
        case Constants.ROLE_ADMIN: 
            if (StringUtils.isBlank(filter)) {
                return articleRepository.findAllArticles(type, status, search, page);
            } else {
                return articleRepository.findArticlesByFilter(filter, userId, type, status, search, page);
            }
        default : throw new RuntimeException(Constants.UNAUTHORIZED_ACCESS_MSG);
        }

    }

    /**
     * Function to get articlestype objects.
     * @param type , the article type. 
     * @return List<ObjectId> get list of articleTypes.
     */
    private List<ObjectId> getArticleTypeObject(final String type) {
        if (StringUtils.isNotBlank(type)) {
         return Arrays.asList(new ObjectId(type));    
        } 
        List<ObjectId> articleTypeObject = new ArrayList<>();
        getArticleTypes().forEach(articleType-> articleTypeObject.add(new ObjectId(articleType.getId())));
        return articleTypeObject;
    }
    
    /**
     * Function to get filter string.
     * @param filter , get valid filter value.
     * @return String get filter value.
     */
    private String getFilter(final ArticleFilter filter) {
        if (filter == null) {
            return null;
        }
        return (filter.toString() + "._id");
    }

    /**
     * Function to get approval status.
     * @param status , get valid status value.
     * @return List<Boolean> get list of approval status.
     */
    private List<Boolean> getApprovalStatus(final String status) {
        if (StringUtils.isBlank(status)) { 
            return Arrays.asList(true, false);
        } else {
            return Arrays.asList(Boolean.parseBoolean(status));
        }
    }


    /**
     * Function to search article in knowledge base.
     * @param search string to search in article list.
     * @param page Pageable object.
     * @return Page<KBArticle> get list of approved article.
     */
    public Page<KBArticle> getKBArticlesWithSearch(final String search, final Pageable page) {

        if (StringUtils.isBlank(search)) {
            return null;
        }
        Page<KBArticle> articlePage = articleRepository.findByTitleAndDescriptionAndApproved(search, true, page);
        List<KBArticle> articles = articlePage.getContent();

        for (KBArticle kbArticle:articles) {
            String description = (kbArticle.getDescription()).replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
            kbArticle.setDescription(description.substring(0, Math.min(description.length(), descriptionLength)));
        }
        return articlePage;
    }
}
