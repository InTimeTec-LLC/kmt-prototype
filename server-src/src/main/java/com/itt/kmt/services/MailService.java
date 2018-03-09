package com.itt.kmt.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.itt.kmt.models.Approve;
import com.itt.kmt.models.Article;
import com.itt.kmt.models.User;
import com.itt.kmt.models.UserResponse;
import com.itt.utility.EmailConstants;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Service class that contoller will communicate and perform operations.
 *
 * @author Sachin Singh
 */
@Service
public class MailService {
    /**
     * Instance of the java mail sender .
     */
    @Autowired
    private JavaMailSender sender;
    /**
     * Instance of the user repository.
     */
    @Autowired
    private UserService userService;
    /**
     * Instance of free marker configuration.
     */
    @Autowired
    private Configuration freemarkerConfig;

    /**
     * This method will call when new user created.
     * 
     * @param userID
     *            to findout the user details.
     * @param loginLink
     *            to show in mail.
     * @throws MailException
     *             .
     * @throws InterruptedException
     *             .
     * @return boolean
     **/
    @Async
    public Future<Boolean> sendUserCreatedMail(final String userID, final String loginLink)
            throws MailException, InterruptedException {

        Map<String, String> model = new HashMap<String, String>();
        User user = userService.getUserByID(userID);

        model.put(EmailConstants.PARAM_USER_FIRST_NAME, user.getFirstName());
        model.put(EmailConstants.PARAM_USER_MAIL_ID, user.getEmail());
        model.put(EmailConstants.PARAM_USER_MAIL_PASSWORD, user.getPassword());
        model.put(EmailConstants.PARAM_PORTAL_LOGIN_LINK, loginLink);
        model.put(EmailConstants.PARAM_EMAIL_SUBJECT, EmailConstants.SUBJECT_CREATE_USER_MAIL);
        return new AsyncResult<Boolean>(sendMail(EmailConstants.CREATE_USER_MAIL_TMPLT, model));
    }

    /**
     * This method will call when admin activate a deactivated user.
     * 
     * @param user
     *            to findout the user details.
     * @param isActive
     *            check active and deactive of user
     * 
     * @throws MailException
     *             .
     * @throws InterruptedException
     *             .
     * @return boolean
     */
    @Async
    public Future<Boolean> sendUserActivateMail(final User user, final boolean isActive)
            throws MailException, InterruptedException {

        Map<String, String> model = new HashMap<String, String>();

        model.put(EmailConstants.PARAM_USER_FIRST_NAME, user.getFirstName());
        model.put(EmailConstants.PARAM_USER_MAIL_ID, user.getEmail());
        if (isActive) {
            model.put(EmailConstants.PARAM_EMAIL_SUBJECT, EmailConstants.SUBJECT_ACCOUNT_ACTIVATE_MAIL);
            return new AsyncResult<Boolean>(sendMail(EmailConstants.ACTIVATE_USER_MAIL_TMPLT, model));
        } else {
            model.put(EmailConstants.PARAM_EMAIL_SUBJECT, EmailConstants.SUBJECT_ACCOUNT_DEACTIVATE_MAIL);
            return new AsyncResult<Boolean>(sendMail(EmailConstants.DEACTIVATE_USER_MAIL_TMPLT, model));
        }

    }

    /**
     * This method will call when new Article will be created.
     * 
     * @param user
     *            to findout the user details.
     * @throws MailException
     *             .
     * @throws InterruptedException
     *             .
     * @return boolean
     **/
    @Async
    public Future<Boolean> sendCreateArticleMail(final User user) throws MailException, InterruptedException {

        Map<String, String> model = new HashMap<String, String>();

        model.put(EmailConstants.PARAM_USER_FIRST_NAME, user.getFirstName());
        model.put(EmailConstants.PARAM_USER_MAIL_ID, user.getEmail());
        model.put(EmailConstants.PARAM_EMAIL_SUBJECT, EmailConstants.SUBJECT_ARTICLE_CREATE_MAIL);

        return new AsyncResult<Boolean>(sendMail(EmailConstants.CREATE_KA_MAIL_TMPLT, model));
    }

    /**
     * This method will call when Admin reset the password of users .
     * 
     * @param user
     *            to findout the user details.
     * @param newPassword
     *            to show in mail.
     * @throws MailException
     *             .
     * @throws InterruptedException
     *             .
     * @return boolean
     **/
    @Async
    public Future<Boolean> sendResetPasswordMail(final User user, final String newPassword)
            throws MailException, InterruptedException {

        Map<String, String> model = new HashMap<String, String>();

        model.put(EmailConstants.PARAM_USER_FIRST_NAME, user.getFirstName());
        model.put(EmailConstants.PARAM_USER_MAIL_ID, user.getEmail());
        model.put(EmailConstants.PARAM_USER_MAIL_PASSWORD, newPassword);
        model.put(EmailConstants.PARAM_EMAIL_SUBJECT, EmailConstants.SUBJECT_ADMIN_RESET_PASS_MAIL);

        return new AsyncResult<Boolean>(sendMail(EmailConstants.EDIT_USER_MAIL_TMPLT, model));
    }

    /**
     * This method will call when KA will be deleted.
     * 
     * @param article
     *            to findout the article details.
     * @param isAdmin
     *            to check the user role.
     * @throws MailException
     *             .
     * @throws InterruptedException
     *             .
     * @return boolean
     **/
    @Async
    public Future<Boolean> sendDeleteKAMail(final Article article, final boolean isAdmin)
            throws MailException, InterruptedException {

        Map<String, String> model = new HashMap<String, String>();

        String tmplt = EmailConstants.DELETE_KA_MAIL_TMPLT;
        String subject = EmailConstants.SUBJECT_ARTICLE_DELETED_MAIL;

        if (isAdmin) {

            tmplt = EmailConstants.DELETE_KA_BYADMIN_MAIL_TMPLT;
            subject = EmailConstants.SUBJECT_ARTICLE_DELETEDBY_ADMIN_MAIL;

            UserResponse approver = (UserResponse) article.getApprover();

            model.put(EmailConstants.PARAM_USER_FIRST_NAME, approver.getFirstName());
            model.put(EmailConstants.PARAM_USER_MAIL_ID, approver.getEmail());
            model.put(EmailConstants.PARAM_EMAIL_SUBJECT, subject);
            sendMail(tmplt, model);
            model.clear();
        }

        UserResponse createdBy = (UserResponse) article.getCreatedBy();

        model.put(EmailConstants.PARAM_USER_FIRST_NAME, createdBy.getFirstName());
        model.put(EmailConstants.PARAM_USER_MAIL_ID, createdBy.getEmail());
        model.put(EmailConstants.PARAM_EMAIL_SUBJECT, subject);

        return new AsyncResult<Boolean>(sendMail(tmplt, model));
    }

    /**
     * This method will call when ka will be approved and publish .
     * 
     * @param article
     *            to findout the article details.
     *
     * @throws MailException
     *             .
     * @throws InterruptedException
     *             .
     * @return boolean
     **/
    @Async
    public Future<Boolean> sendKAapproveAndPublishMail(final Article article)
            throws MailException, InterruptedException {

        Map<String, String> model = new HashMap<String, String>();

        UserResponse userResponse = (UserResponse) article.getCreatedBy();

        model.put(EmailConstants.PARAM_USER_FIRST_NAME, userResponse.getFirstName());
        model.put(EmailConstants.PARAM_USER_MAIL_ID, userResponse.getEmail());
        model.put(EmailConstants.PARAM_EMAIL_SUBJECT, EmailConstants.SUBJECT_ARTICLE_APPROVED_AND_PUBLISHED_MAIL);

        return new AsyncResult<Boolean>(sendMail(EmailConstants.APPROVED_AND_PUBLISH_KA_MAIL_TMPLT, model));
    }

    /**
     * This method will call when KA is reviewed by reviewer .
     * 
     * @param article
     *            to findout the article details.
     * @param approve.
     * @throws MailException
     *             .
     * @throws InterruptedException
     *             .
     * @return boolean
     **/
    @Async
    public Future<Boolean> sendKAReviewdMail(final Article article, final Approve approve)
            throws MailException, InterruptedException {

        Map<String, String> model = new HashMap<String, String>();

        UserResponse userResponse = (UserResponse) article.getCreatedBy();

        model.put(EmailConstants.PARAM_USER_FIRST_NAME, userResponse.getFirstName());
        model.put(EmailConstants.PARAM_USER_MAIL_ID, userResponse.getEmail());
        model.put(EmailConstants.PARAM_EMAIL_SUBJECT, EmailConstants.SUBJECT_ARTICLE_REVIEWED_MAIL);
        model.put(EmailConstants.PARAM_COMMENTS, approve.getComment());

        return new AsyncResult<Boolean>(sendMail(EmailConstants.REVIEWED_KA_MAIL_TMPLT, model));
    }

    /**
     * This method is responsible for sending mail.
     *
     * @param templateName
     *            to choose the relevent templates.
     * @param model
     *            model have the relevent parameters that is required for
     *            templates.
     *
     * @return boolean
     */
    private boolean sendMail(final String templateName, final Map<String, String> model) {

        boolean status = true;

        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {

            freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

            Template template = freemarkerConfig.getTemplate(templateName);

            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setTo(model.get(EmailConstants.PARAM_USER_MAIL_ID).toString());
            helper.setText(text, true);
            helper.setSubject(model.get(EmailConstants.PARAM_EMAIL_SUBJECT).toString());

            sender.send(message);

        } catch (MessagingException | IOException | TemplateException e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }
}