package com.itt.test.kmt.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.itt.kmt.models.User;
import com.itt.kmt.repositories.UserRepository;
import com.itt.kmt.services.MailService;

import com.itt.test_category.ServicesTests;
import com.itt.test_data.TestDataRepository;

@Category(ServicesTests.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTests {

    @Autowired
    private MailService mailService;

    @Autowired
    private TestDataRepository testDataRepository;

    @MockBean
    private UserRepository userRepository;

    @Before
    public final void setUp() {

    }

    @Test
    public final void sendMailTest() {

        // Arrange
        User user = testDataRepository.getUsers().get("user-1");
        // when()
        when(userRepository.findOne(user.getId())).thenReturn(user);

        boolean status = mailService.sendUserCreatedMail(user.getId(), "login link");
        //assert
        assertTrue(status);

        verify(userRepository, times(1)).findOne(user.getId());
    }
}