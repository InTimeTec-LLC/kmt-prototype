package com.itt.test.kmt.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.itt.kmt.models.Role;
import com.itt.kmt.repositories.RoleRepository;
import com.itt.test_data.RoleTestDataRepository;
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
import com.itt.kmt.services.UserService;
import com.itt.test_category.ServicesTests;
import com.itt.test_data.TestDataRepository;

@Category(ServicesTests.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Autowired
    private TestDataRepository testDataRepository;

    @Autowired
    private RoleTestDataRepository roleTestDataRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Before
    public final void setUp() {

    }

    @Test
    public final void save() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        when(userRepository.save(user1)).thenReturn(user1);

        // Act
        User user = userService.save(user1);

        // Assert
        assertEquals(user.getEmail(), user1.getEmail());
        assertEquals(user.getFirstName(), user1.getFirstName());
        assertEquals(user.getLastName(), user1.getLastName());
        assertEquals(user.getUserRole(), user1.getUserRole());
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    public final void getUserByEmail() {
        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        when(userRepository.findByEmail(user1.getEmail())).thenReturn(user1);

        // Act
        User user = userService.getUserByEmail(user1.getEmail());
        // Assert
        assertEquals(user.getEmail(), user1.getEmail());
        assertEquals(user.getFirstName(), user1.getFirstName());
        assertEquals(user.getLastName(), user1.getLastName());
        assertEquals(user.getUserRole(), user1.getUserRole());
        verify(userRepository, times(1)).findByEmail(user1.getEmail());
    }

    @Test
    public final void getUserById() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        when(userRepository.findOne(user1.getId())).thenReturn(user1);

        // Act
        User user = userRepository.findOne(user1.getId());

        // Assert
        assertEquals(user.getId(), user1.getId());
        assertEquals(user.getEmail(), user1.getEmail());
        assertEquals(user.getFirstName(), user1.getFirstName());
        assertEquals(user.getLastName(), user1.getLastName());
        assertEquals(user.getUserRole(), user1.getUserRole());
        verify(userRepository, times(1)).findOne(user1.getId());
    }

    @Test
    public final void getAllUsers() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        User user2 = testDataRepository.getUsers()
                .get("user-2");
        List<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> user = userService.getAllUsers();
        // Assert
        assertTrue(users.containsAll(user));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public final void deleteUserById() {
        // Arrange
        User user1 = testDataRepository.getUsers().get("user-1");

        given(userRepository.findOne("1")).willReturn(user1);

        user1.setActive(false);
        // Act
        when(userRepository.save(user1)).thenReturn(user1);
        User user = userRepository.save(user1);

        // Assert
        assertEquals(user.getId(), user1.getId());
        assertEquals(user.isActive(), false);
        verify(userRepository, times(1)).save(user1);

    }

    @Test
    public final void updateUser() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        user1.setFirstName("test");
        given(userRepository.findOne(user1.getId())).willReturn(user1);

        when(userRepository.save(user1)).thenReturn(user1);

        // Act
        User user = userService.updateUser(user1, user1.getId());

        // Assert
        assertEquals(user.getId(), user1.getId());
        assertEquals(user.getEmail(), user1.getEmail());
        assertEquals(user.getFirstName(), user1.getFirstName());
        assertEquals(user.getLastName(), user1.getLastName());
        assertEquals(user.getUserRole(), user1.getUserRole());
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    public final void changeUserStatus() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        user1.setActive(false);
        given(userRepository.findOne(user1.getId())).willReturn(user1);

        when(userRepository.save(user1)).thenReturn(user1);

        // Act
        User user = userService.changeUserStatus(user1.getId(), true);

        // Assert
        assertEquals(user.isActive(), user1.isActive());

        verify(userRepository, times(1)).save(user1);
    }
    
    @Test
    public final void changeUserStatustoDeactivate() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-3");
        user1.setActive(true);
        given(userRepository.findOne(user1.getId())).willReturn(user1);

        when(userRepository.save(user1)).thenReturn(user1);

        // Act
        User user = userService.changeUserStatus(user1.getId(), false);

        // Assert
        assertEquals(user.isActive(), user1.isActive());

        verify(userRepository, times(1)).save(user1);
    }


    @Test
    public final void getAllRoles() {

        // Arrange
        Role role1 = roleTestDataRepository.getRoles()
                .get("role-1");
        Role role2 = roleTestDataRepository.getRoles()
                .get("role-1");
        List<Role> roles = new ArrayList<Role>();
        roles.add(role1);
        roles.add(role1);
        when(roleRepository.findAll()).thenReturn(roles);

        // Act
        List<Role> role = userService.getUserRoles();
        // Assert
        assertTrue(roles.containsAll(role));
        verify(roleRepository, times(1)).findAll();
    }
}
