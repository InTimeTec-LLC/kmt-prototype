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

import com.itt.kmt.models.User;
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
     * .
     * @throws InterruptedException
     * .
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
     *.
     * @throws InterruptedException
     *.
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