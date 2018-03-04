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
    public final void getAllActiveUsersByRoles() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");

        List<User> admins = new ArrayList<User>();
        admins.add(user1);

        User user2 = testDataRepository.getUsers()
                .get("user-2");

        List<User> managers = new ArrayList<User>();
        managers.add(user2);

        List<User> adminsAndManagers = new ArrayList<User>();
        adminsAndManagers.addAll(admins);
        adminsAndManagers.addAll(managers);

        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        roles.add("manager");

        when(userRepository.findByUserRole("admin", true)).thenReturn(admins);
        when(userRepository.findByUserRole("manager", true)).thenReturn(managers);

        // Act
        List<User> user = userService.getAllActiveUsersByRoles(roles);
        // Assert
        assertTrue(adminsAndManagers.containsAll(user));
        verify(userRepository, times(1)).findByUserRole("admin", true);
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

    @Test(expected = RuntimeException.class)
    public final void updateNonActiveUser() {

        // Arrange
        User user2 = testDataRepository.getUsers()
                .get("user-2");
        user2.setActive(false);
        given(userRepository.findOne(user2.getId())).willReturn(user2);
        user2.setFirstName("test");
        when(userService.updateUser(user2, user2.getId())).thenThrow(new RuntimeException("user is not active"));

        // Act
        User user = userService.updateUser(user2, user2.getId());

        // Assert
        verify(userRepository, times(1)).save(user2);
    }

    @Test(expected = RuntimeException.class)
    public final void updateNonExistantUser() {

        // Arrange
        User user3 = testDataRepository.getUsers()
                .get("user-3");

        given(userRepository.findOne(user3.getId())).willReturn(null);
        user3.setFirstName("test");
        when(userService.updateUser(user3, user3.getId())).thenThrow(new RuntimeException("user does not exist"));

        // Act
        User user = userService.updateUser(user3, user3.getId());

        // Assert
        verify(userRepository, times(1)).save(user3);
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
    
    @Test()
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

    @Test(expected = RuntimeException.class)
    public final void activateActiveUser() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        user1.setActive(true);

        when(userRepository.findOne(user1.getId()))
        .thenReturn(user1);
        when(userService.changeUserStatus(user1.getId(), true))
        .thenThrow(new RuntimeException("Operation not permitted"));
        userService.changeUserStatus(user1.getId(), true);

        // Assert
        assertEquals(user1.isActive(), true);

        verify(userService, times(1)).changeUserStatus(user1.getId(), true);
    }

    @Test(expected = RuntimeException.class)
    public final void activateNonExistingUser() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");

        when(userRepository.findOne(user1.getId()))
        .thenReturn(null);
        when(userService.changeUserStatus(user1.getId(), true))
        .thenThrow(new RuntimeException("user with the id does not exist"));
        userService.changeUserStatus(user1.getId(), true);

        // Assert
        verify(userService, times(1)).changeUserStatus(user1.getId(), true);
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
