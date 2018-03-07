package com.itt.test.kmt.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.itt.kmt.models.User;
import com.itt.kmt.services.MailService;
import com.itt.kmt.services.UserService;
import com.itt.test_category.ServicesTests;
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

    @MockBean
    private UserService userService;

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
        // Awaitility.await().atMost(2, TimeUnit.SECONDS);

        // assert
        assertTrue(status);

        verify(userService, times(1)).getUserByID(user.getId());
    }

    @Test
    public final void sendUserActivateMail() throws MailException, InterruptedException, ExecutionException {

        // Arrange
        User user = testDataRepository.getUsers().get("user-2");

        // when()
        when(javaMailSender.createMimeMessage()).thenReturn(new JavaMailSenderImpl().createMimeMessage());
        when(userService.getUserByID(user.getId())).thenReturn(user);

        boolean statusActive = mailService.sendUserActivateMail(user, true).get();
        boolean statusDeactive = mailService.sendUserActivateMail(user, false).get();

        // assert
        assertTrue(statusActive);
        assertTrue(statusDeactive);

    }
}