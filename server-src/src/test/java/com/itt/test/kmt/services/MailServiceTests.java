package com.itt.test.kmt.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

import com.itt.kmt.models.Approve;
import com.itt.kmt.models.Article;
import com.itt.kmt.models.User;
import com.itt.kmt.models.UserResponse;
import com.itt.kmt.services.ArticleService;
import com.itt.kmt.services.MailService;
import com.itt.kmt.services.UserService;
import com.itt.test_category.ServicesTests;
import com.itt.test_data.ArticleTestDataRepository;
import com.itt.test_data.TestDataRepository;
import com.itt.utility.EmailConstants;

@Category(ServicesTests.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTests {

    @Autowired
    private MailService mailService;

    @Autowired
    private TestDataRepository testDataRepository;

    @Autowired
    private ArticleTestDataRepository articleTestDataRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private Article article;

    @MockBean
    private JavaMailSender javaMailSender;

    @Before
    public final void setUp() {

    }

    @Test
    public final void sendMailTest() throws MailException, InterruptedException, ExecutionException {

        // Arrange
        User user = testDataRepository.getUsers().get("user-2");

        // when()
        when(javaMailSender.createMimeMessage()).thenReturn(new JavaMailSenderImpl().createMimeMessage());
        when(userService.getUserByID(user.getId())).thenReturn(user);

        boolean status = mailService.sendUserCreatedMail(user.getId(), EmailConstants.PORTAL_LOGIN_LINK).get();

        // assert
        assertTrue(status);

        verify(userService, times(1)).getUserByID(user.getId());
    }

    @Test
    public final void sendUserActivateMailTest() throws MailException, InterruptedException, ExecutionException {

        // Arrange
        User user = testDataRepository.getUsers().get("user-2");

        // when()
        when(javaMailSender.createMimeMessage()).thenReturn(new JavaMailSenderImpl().createMimeMessage());

        boolean statusActive = mailService.sendUserActivateMail(user, true).get();
        boolean statusDeactive = mailService.sendUserActivateMail(user, false).get();

        // assert
        assertTrue(statusActive);
        assertTrue(statusDeactive);

    }

    @Test
    public final void sendResetPasswordMailTest() throws MailException, InterruptedException, ExecutionException {

        // Arrange
        User user = testDataRepository.getUsers().get("user-3");

        // when()
        when(javaMailSender.createMimeMessage()).thenReturn(new JavaMailSenderImpl().createMimeMessage());

        boolean status = mailService.sendResetPasswordMail(user, "newP@sswo1d").get();

        // assert
        assertTrue(status);
    }

    @Test
    public final void sendDeleteKAMailTest() throws MailException, InterruptedException, ExecutionException {

        // Arrange
        User approver = testDataRepository.getUsers().get("user-3");
        User creator = testDataRepository.getUsers().get("user-2");

        UserResponse userApprover = new UserResponse(approver.getId(), approver.getFirstName(), approver.getLastName(),
                approver.getEmail());
        UserResponse usercreator = new UserResponse(approver.getId(), approver.getFirstName(), approver.getLastName(),
                approver.getEmail());

        // when()
        when(javaMailSender.createMimeMessage()).thenReturn(new JavaMailSenderImpl().createMimeMessage());
        when(article.getApprover()).thenReturn(userApprover);
        when(article.getCreatedBy()).thenReturn(usercreator);

//        Article articles = articleTestDataRepository.getArticles().get("article-2");

        boolean asAdmin = mailService.sendDeleteKAMail(article, true).get();
        boolean asUser = mailService.sendDeleteKAMail(article, false).get();

        // assert
        assertTrue(asAdmin);
        assertTrue(asUser);

    }

    @Test
    public final void sendKAapproveAndPublishMailTest() throws MailException, InterruptedException, ExecutionException {

        // Arrange
        User creator = testDataRepository.getUsers().get("user-3");

        UserResponse usercreator = new UserResponse(creator.getId(), creator.getFirstName(), creator.getLastName(),
                creator.getEmail());

        Approve approve = new Approve();
        approve.setComment("test comments");

        // when()
        when(javaMailSender.createMimeMessage()).thenReturn(new JavaMailSenderImpl().createMimeMessage());
        when(article.getCreatedBy()).thenReturn(usercreator);
        boolean status = mailService.sendKAapproveAndPublishMail(article, approve).get();

        // assert
        assertTrue(status);
    }

    @Test
    public final void sendKAReviewdMailTest() throws MailException, InterruptedException, ExecutionException {

        // Arrange
        User creator = testDataRepository.getUsers().get("user-3");

        UserResponse usercreator = new UserResponse(creator.getId(), creator.getFirstName(), creator.getLastName(),
                creator.getEmail());

        Approve approve = new Approve();
        approve.setComment("test comments");

        // when()
        when(javaMailSender.createMimeMessage()).thenReturn(new JavaMailSenderImpl().createMimeMessage());
        when(article.getCreatedBy()).thenReturn(usercreator);

        boolean status = mailService.sendKAReviewdMail(article, approve).get();

        // assert
        assertTrue(status);
    }

    @Test
    public final void sendCreateArticleMailTest() throws MailException, InterruptedException, ExecutionException {

        // Arrange
        User user = testDataRepository.getUsers().get("user-3");
        UserResponse usercreator = new UserResponse(user.getId(), user.getFirstName(), user.getLastName(),
                user.getEmail());

        // when()
        when(javaMailSender.createMimeMessage()).thenReturn(new JavaMailSenderImpl().createMimeMessage());

        boolean status = mailService.sendCreateArticleMail(usercreator, article).get();

        // assert
        assertTrue(status);
    }
    
    @Test
    public final void updateUserToChangeReviewerTest() throws MailException, InterruptedException, ExecutionException {
        
        // Arrange
        Article article = articleTestDataRepository.getArticles().get("article-7");
        List<Article> articles = Arrays.asList(article);
        
        // when()
        when(mailService.sendNotificationMail(articles.get(0), true).get()).thenReturn(true);

        Boolean status = mailService.updateUserToChangeReviewer(articles).get();
        // assert
        assertTrue(status);
    }


}