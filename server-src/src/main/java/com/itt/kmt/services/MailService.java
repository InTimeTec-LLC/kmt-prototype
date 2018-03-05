package com.itt.kmt.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.itt.kmt.models.User;
import com.itt.kmt.repositories.UserRepository;
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
    private UserRepository userRepository;
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
     * @return boolean
     */
    public boolean sendUserCreatedMail(final String userID, final String loginLink) {

        Map<String, String> model = new HashMap<String, String>();
        User user = userRepository.findOne(userID);

        model.put(EmailConstants.PARAM_USER_FIRST_NAME, user.getFirstName());
        model.put(EmailConstants.PARAM_USER_MAIL_ID, user.getEmail());
        model.put(EmailConstants.PARAM_USER_MAIL_PASSWORD, user.getPassword());
        model.put(EmailConstants.PARAM_PORTAL_LOGIN_LINK, loginLink);
        model.put(EmailConstants.PARAM_EMAIL_SUBJECT, EmailConstants.SUBJECT_CREATE_USER_MAIL);

        return sendMail(EmailConstants.CREATE_USER_MAIL_TMPLT, model);
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
    public boolean sendMail(final String templateName, final Map<String, String> model) {

        boolean status = true;

        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {

            freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");

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