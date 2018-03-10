package com.itt.test.kmt.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.test.context.junit4.SpringRunner;

import com.itt.kmt.models.Role;
import com.itt.kmt.models.User;
import com.itt.kmt.repositories.RoleRepository;
import com.itt.kmt.repositories.UserRepository;
import com.itt.kmt.services.MailService;
import com.itt.kmt.services.UserService;
import com.itt.test_category.ServicesTests;
import com.itt.test_data.RoleTestDataRepository;
import com.itt.test_data.TestDataRepository;
import com.itt.utility.EmailConstants;

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

    @MockBean
    private MailService mailService;

    @Before
    public final void setUp() {

    }

    @Test
    public final void save() throws MailException, InterruptedException, ExecutionException {

        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");

        when(userRepository.save(user)).thenReturn(user);
        when(mailService.sendUserCreatedMail(user.getId(),
                EmailConstants.PARAM_PORTAL_LOGIN_LINK)).thenReturn(new AsyncResult<Boolean>(true));

        when(userRepository.save(user)).thenReturn(user);

        // Act
        User createdUser = userService.save(user);

        // Assert
        assertEquals(createdUser.getEmail(), user.getEmail());
        assertEquals(createdUser.getFirstName(), user.getFirstName());
        assertEquals(createdUser.getLastName(), user.getLastName());
        assertEquals(createdUser.getUserRole(), user.getUserRole());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public final void getUserByEmail() {
        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        // Act
        User userRecieved = userService.getUserByEmail(user.getEmail());
        // Assert
        assertEquals(userRecieved.getEmail(), user.getEmail());
        assertEquals(userRecieved.getFirstName(), user.getFirstName());
        assertEquals(userRecieved.getLastName(), user.getLastName());
        assertEquals(userRecieved.getUserRole(), user.getUserRole());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    public final void getUserByID() {

        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");
        when(userRepository.findOne(user.getId())).thenReturn(user);

        // Act
        User userRecieved = userService.getUserByID(user.getId());

        // Assert
        assertEquals(userRecieved.getId(), user.getId());
        assertEquals(userRecieved.getEmail(), user.getEmail());
        assertEquals(userRecieved.getFirstName(), user.getFirstName());
        assertEquals(userRecieved.getLastName(), user.getLastName());
        assertEquals(userRecieved.getUserRole(), user.getUserRole());
        verify(userRepository, times(1)).findOne(user.getId());
    }

    @Test(expected = RuntimeException.class)
    public final void getNonExistingUser() {

        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");
        when(userRepository.findOne(user.getId())).thenReturn(null);
        when(userService.getUserByID(user.getId())).thenThrow(new RuntimeException("user with the id does not exist"));

        // Act
        userService.getUserByID(user.getId());

        // Assert
        verify(userRepository, times(1)).findOne(user.getId());
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
        List<User> usersListRecieved = userService.getAllUsers();
        // Assert
        assertTrue(users.containsAll(usersListRecieved));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public final void getAllUsersByRoles() {

        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");

        List<User> usersList = new ArrayList<User>();
        usersList.add(user);

        when(userRepository.findByUserRole("admin")).thenReturn(usersList);
        List<String> roles = new ArrayList<String>();
        roles.add("admin");

        // Act
        List<User> usersListRecieved = userService.getAllUsersByRoles(roles);
        // Assert
        assertTrue(usersList.containsAll(usersListRecieved));
        verify(userRepository, times(1)).findByUserRole("admin");
    }

    @Test
    public final void searchUsersByUserAttribute() {

        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");

        List<User> usersList = new ArrayList<User>();
        usersList.add(user);

        when(userRepository.findByFirstNameOrLastNameOrEmail(user.getFirstName())).thenReturn(usersList);

        // Act
        List<User> usersListRecieved = userService.searchUsersByUserAttribute(user.getFirstName());
        // Assert
        assertTrue(usersList.containsAll(usersListRecieved));
        verify(userRepository, times(1)).findByFirstNameOrLastNameOrEmail(user.getFirstName());
    }

    @Test
    public final void getAllUsersByRolesAndStatus() {

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

        when(userRepository.findByUserRoleAndActive("admin", true)).thenReturn(admins);
        when(userRepository.findByUserRoleAndActive("manager", true)).thenReturn(managers);

        // Act
        List<User> usersListRecieved = userService.getAllUsersByRolesAndStatus(roles, true);
        // Assert
        assertTrue(adminsAndManagers.containsAll(usersListRecieved));
        verify(userRepository, times(1)).findByUserRoleAndActive("admin", true);
    }

    @Test
    public final void updateUser() throws MailException, InterruptedException {

        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");
        user.setFirstName("test");
        when(userRepository.findOne(user.getId())).thenReturn(user);
        when(mailService.sendResetPasswordMail(user, user.getPassword())).thenReturn(new AsyncResult<Boolean>(true));

        when(userRepository.save(user)).thenReturn(user);

        // Act
        User updatedUser = userService.updateUser(user, user.getId());

        // Assert
        assertEquals(updatedUser.getId(), user.getId());
        assertEquals(updatedUser.getEmail(), user.getEmail());
        assertEquals(updatedUser.getFirstName(), user.getFirstName());
        assertEquals(updatedUser.getLastName(), user.getLastName());
        assertEquals(updatedUser.getUserRole(), user.getUserRole());
        verify(userRepository, times(1)).save(user);
    }

    @Test(expected = RuntimeException.class)
    public final void updateNonActiveUser() {

        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-2");
        user.setActive(false);
        when(userRepository.findOne(user.getId())).thenReturn(user);
        user.setFirstName("test");
        when(userService.updateUser(user, user.getId())).thenThrow(new RuntimeException("user is not active"));

        // Act
        userService.updateUser(user, user.getId());

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test(expected = RuntimeException.class)
    public final void updateNonExistantUser() {

        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-3");

        when(userRepository.findOne(user.getId())).thenReturn(null);
        user.setFirstName("test");
        when(userService.updateUser(user, user.getId())).thenThrow(new RuntimeException("user does not exist"));

        // Act
        userService.updateUser(user, user.getId());

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public final void changeUserStatus() throws MailException, InterruptedException {

        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");
        user.setActive(false);
        when(userRepository.findOne(user.getId())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User updatedUser = userService.changeUserStatus(user.getId(), true);

        // Assert
        assertEquals(updatedUser.isActive(), true);

        verify(userRepository, times(1)).save(user);
    }

    @Test()
    public final void changeUserStatusToDeactivate() throws MailException, InterruptedException {

        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-3");
        user.setActive(true);

        when(userRepository.findOne(user.getId())).thenReturn(user);
        when(mailService.sendUserActivateMail(user, false)).thenReturn(new AsyncResult<Boolean>(true)); 
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User updatedUser = userService.changeUserStatus(user.getId(), false);

        // Assert
        assertEquals(updatedUser.isActive(), false);

        verify(userRepository, times(1)).save(user);
    }

    @Test(expected = RuntimeException.class)
    public final void activateActiveUser() throws MailException, InterruptedException {

        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");
        user.setActive(true);

        when(userRepository.findOne(user.getId()))
        .thenReturn(user);
        when(userService.changeUserStatus(user.getId(), true))
        .thenThrow(new RuntimeException("Operation not permitted"));
        when(mailService.sendUserActivateMail(user, true)).thenReturn(new AsyncResult<Boolean>(true)); 

        userService.changeUserStatus(user.getId(), true);

        // Assert
        assertEquals(user.isActive(), true);

        verify(userService, times(1)).changeUserStatus(user.getId(), true);
    }

    @Test(expected = RuntimeException.class)
    public final void activateNonExistingUser() {

        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");

        when(userRepository.findOne(user.getId()))
        .thenReturn(null);
        when(userService.changeUserStatus(user.getId(), true))
        .thenThrow(new RuntimeException("user with the id does not exist"));
        userService.changeUserStatus(user.getId(), true);

        // Assert
        verify(userService, times(1)).changeUserStatus(user.getId(), true);
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

    @Test
    public final void filterUsersByStatusAndRole() {

        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");

        List<User> usersList = new ArrayList<User>();
        usersList.add(user);

        when(userRepository.findByFirstNameOrLastNameOrEmail("ashish"))
        .thenReturn(usersList);


        // Act
        List<User> usersListRecieved = userService.filterUsersByStatusAndRole("ashish", user.getUserRole(), 
                user.isActive());
        // Assert
        assertTrue(usersList.containsAll(usersListRecieved));
        verify(userRepository, times(1)).findByFirstNameOrLastNameOrEmail("ashish");
    }
}
