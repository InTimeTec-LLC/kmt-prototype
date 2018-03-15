package com.itt.test.kmt.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.test.context.junit4.SpringRunner;

import com.itt.kmt.models.Role;
import com.itt.kmt.models.User;
import com.itt.kmt.repositories.RoleRepository;
import com.itt.kmt.repositories.UserRepository;
import com.itt.kmt.response.models.ResponseMsg;
import com.itt.kmt.services.MailService;
import com.itt.kmt.services.UserService;
import com.itt.test_category.ServicesTests;
import com.itt.test_data.RoleTestDataRepository;
import com.itt.test_data.TestDataRepository;
import com.itt.utility.Constants;
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
        String password = user.getPassword();
        
        when(userRepository.save(user)).thenReturn(user);
        when(mailService.sendUserCreatedMail(user.getId(), password,
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
        when(userRepository.findByEmailContainingIgnoreCase(user.getEmail())).thenReturn(user);

        // Act
        User userRecieved = userService.getUserByEmail(user.getEmail());
        // Assert
        assertEquals(userRecieved.getEmail(), user.getEmail());
        assertEquals(userRecieved.getFirstName(), user.getFirstName());
        assertEquals(userRecieved.getLastName(), user.getLastName());
        assertEquals(userRecieved.getUserRole(), user.getUserRole());
        verify(userRepository, times(1)).findByEmailContainingIgnoreCase(user.getEmail());
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
        when(userService.getUserByID(user.getId()))
        .thenThrow(new RuntimeException(Constants.USER_DOES_NOT_EXIST_ERROR_MSG));

        // Act
        userService.getUserByID(user.getId());

        // Assert
        verify(userRepository, times(1)).findOne(user.getId());
    }

    @Test
    public final void filterUsersWithoutFiltersAndSearch() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        User user2 = testDataRepository.getUsers()
                .get("user-2");
        List<User> usersList = new ArrayList<User>();
        usersList.add(user2);
        PageRequest pageReq = new PageRequest(0, 1);

        Page<User> pages = new PageImpl<User>(usersList);
        when(userRepository.findAll(user1.getEmail(), pageReq)).thenReturn(pages);
        //Act
        Page<User> pageOfUsers = userService.filterUsersByStatusAndRole(null, null, null, user1.getEmail(), pageReq);

        // Assert
        assertTrue(pages.getContent().containsAll(pageOfUsers.getContent()));
        verify(userRepository, times(1)).findAll(user1.getEmail(), pageReq);
    }

    @Test
    public final void filterUsersWithRoleAndEmptySearch() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        User user2 = testDataRepository.getUsers()
                .get("user-2");
        List<User> usersList = new ArrayList<User>();
        usersList.add(user2);
        PageRequest pageReq = new PageRequest(0, 1);

        Page<User> pages = new PageImpl<User>(usersList);
        when(userRepository.findByUserRole(user2.getUserRole(), user1.getEmail(), pageReq)).thenReturn(pages);
        //Act
        Page<User> pageOfUsers = userService.filterUsersByStatusAndRole(null, user2.getUserRole(), null, 
                 user1.getEmail(), pageReq);

        // Assert
        assertTrue(pages.getContent().containsAll(pageOfUsers.getContent()));
        verify(userRepository, times(1)).findByUserRole(user2.getUserRole(), user1.getEmail(), pageReq);
    }
    

    @Test
    public final void filterUsersWithRoleAndStatusAndEmptySearch() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        User user2 = testDataRepository.getUsers()
                .get("user-2");
        List<User> usersList = new ArrayList<User>();
        usersList.add(user2);
        PageRequest pageReq = new PageRequest(0, 1);

        Page<User> pages = new PageImpl<User>(usersList);
        when(userRepository.findByUserRoleAndActive(user2.getUserRole(), true, user1.getEmail(), pageReq))
          .thenReturn(pages);
        //Act
        Page<User> pageOfUsers = userService.filterUsersByStatusAndRole(null, user2.getUserRole(), true, 
                   user1.getEmail(), pageReq);

        // Assert
        assertTrue(pages.getContent().containsAll(pageOfUsers.getContent()));
        verify(userRepository, times(1)).findByUserRoleAndActive(user2.getUserRole(), true, user1.getEmail(), pageReq);
    }

    @Test
    public final void filterUsersWithStatusAndEmptySearch() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        User user2 = testDataRepository.getUsers()
                .get("user-2");
        User user3 = testDataRepository.getUsers()
                .get("user-3");
        List<User> usersList = new ArrayList<User>();
        usersList.add(user2);
        usersList.add(user3);
        PageRequest pageReq = new PageRequest(0, 1);

        Page<User> pages = new PageImpl<User>(usersList);
        when(userRepository.findByActive(true, user1.getEmail(), pageReq)).thenReturn(pages);
        //Act
        Page<User> pageOfUsers = userService.filterUsersByStatusAndRole(null, null, true, 
                   user1.getEmail(), pageReq);

        // Assert
        assertTrue(pages.getContent().containsAll(pageOfUsers.getContent()));
        verify(userRepository, times(1)).findByActive(true, user1.getEmail(), pageReq);
    }

    @Test
    public final void filterUsersWithFiltersAndSearch() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        User user2 = testDataRepository.getUsers()
                .get("user-2");

        List<User> usersList = new ArrayList<User>();
        usersList.add(user2);

        PageRequest pageReq = new PageRequest(0, 1);

        Page<User> pages = new PageImpl<User>(usersList);

        when(userRepository.findByFirstNameOrLastNameOrEmailAndActiveAndUserRole(user2.getFirstName(),
                user1.getEmail(), 
                true, "manager", pageReq)).thenReturn(pages);
        //Act
        Page<User> pageOfUsers = userService.filterUsersByStatusAndRole(user2.getFirstName(), 
                   "manager", true, 
                   user1.getEmail(), pageReq);

        // Assert
        assertTrue(pages.getContent().containsAll(pageOfUsers.getContent()));
        verify(userRepository, times(1)).findByFirstNameOrLastNameOrEmailAndActiveAndUserRole(user2.getFirstName(), 
                user1.getEmail(), 
                true, "manager", pageReq);
    }

    @Test
    public final void filterUsersWithStatusAndSearch() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        User user2 = testDataRepository.getUsers()
                .get("user-2");

        List<User> usersList = new ArrayList<User>();
        usersList.add(user2);

        PageRequest pageReq = new PageRequest(0, 1);

        Page<User> pages = new PageImpl<User>(usersList);

        when(userRepository.findByFirstNameOrLastNameOrEmailAndActive(user2.getFirstName(), user1.getEmail(), 
             true, pageReq)).thenReturn(pages);
        //Act
        Page<User> pageOfUsers = userService.filterUsersByStatusAndRole(user2.getFirstName(), 
                   null, true, 
                   user1.getEmail(), pageReq);

        // Assert
        assertTrue(pages.getContent().containsAll(pageOfUsers.getContent()));
        verify(userRepository, times(1)).findByFirstNameOrLastNameOrEmailAndActive(user2.getFirstName(), 
                 user1.getEmail(), true, pageReq);
    }

    @Test
    public final void filterUsersWithRoleAndSearch() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        User user2 = testDataRepository.getUsers()
                .get("user-2");

        List<User> usersList = new ArrayList<User>();
        usersList.add(user2);

        PageRequest pageReq = new PageRequest(0, 1);

        Page<User> pages = new PageImpl<User>(usersList);

        when(userRepository.findByFirstNameOrLastNameOrEmailAndUserRole(user2.getFirstName(), user1.getEmail(), 
             "manager", pageReq)).thenReturn(pages);
        //Act
        Page<User> pageOfUsers = userService.filterUsersByStatusAndRole(user2.getFirstName(), 
                   "manager", null, 
                   user1.getEmail(), pageReq);

        // Assert
        assertTrue(pages.getContent().containsAll(pageOfUsers.getContent()));
        verify(userRepository, times(1)).findByFirstNameOrLastNameOrEmailAndUserRole(user2.getFirstName(), 
                 user1.getEmail(), 
                "manager", pageReq);
    }

    @Test
    public final void filterUsersWithSearchOnly() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        User user2 = testDataRepository.getUsers()
                .get("user-2");

        List<User> usersList = new ArrayList<User>();
        usersList.add(user2);

        PageRequest pageReq = new PageRequest(0, 1);

        Page<User> pages = new PageImpl<User>(usersList);

        when(userRepository.findByFirstNameOrLastNameOrEmail(user2.getFirstName(), user1.getEmail(), 
             pageReq)).thenReturn(pages);
        //Act
        Page<User> pageOfUsers = userService.filterUsersByStatusAndRole(user2.getFirstName(), 
                   null, null, 
                   user1.getEmail(), pageReq);

        // Assert
        assertTrue(pages.getContent().containsAll(pageOfUsers.getContent()));
        verify(userRepository, times(1)).findByFirstNameOrLastNameOrEmail(user2.getFirstName(), user1.getEmail(), 
                pageReq);
    }

    @Test
    public final void getAllUsers() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        User user2 = testDataRepository.getUsers()
                .get("user-2");
        User user3 = testDataRepository.getUsers()
                .get("user-3");
        List<User> users = new ArrayList<User>();
        users.add(user2);
        users.add(user3);
        Page<User> usersPage = new PageImpl<User>(users);
        PageRequest pageReq = new PageRequest(0, 2);
        when(userRepository.findAll(user1.getEmail(), pageReq)).thenReturn(usersPage);

        // Act
        Page<User> usersPageRecieved = userService.getAllUsers(user1.getEmail(), pageReq);
        // Assert
        assertTrue(usersPage.getContent().containsAll(usersPageRecieved.getContent()));
        verify(userRepository, times(1)).findAll(user1.getEmail(), pageReq);
    }

    @Test
    public final void getAllUsersByRolesAndStatus() {

        // Arrange
        User user1 = testDataRepository.getUsers()
                .get("user-1");
        User user2 = testDataRepository.getUsers()
                .get("user-2");

        List<User> managers = new ArrayList<User>();
        managers.add(user2);
        PageRequest pageRequest = new PageRequest(0, 1);
        Page<User> page = new PageImpl<User>(managers);

        when(userRepository.findByUserRoleAndActive("manager", true, user1.getEmail(), pageRequest)).thenReturn(page);

        // Act
        Page<User> usersPageRecieved = userService.getAllUsersByRolesAndStatus("manager", true, user1.getEmail(), 
               pageRequest);
        // Assert
        assertTrue(page.getContent().containsAll(usersPageRecieved.getContent()));
        verify(userRepository, times(1)).findByUserRoleAndActive("manager", true, user1.getEmail(), pageRequest);
    }

    @Test
    public final void getAllUsersByRoles() {

        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");

        List<User> usersList = new ArrayList<User>();
        usersList.add(user);
        PageRequest pageRequest = new PageRequest(0, 1);
        Page<User> page = new PageImpl<User>(usersList);

        when(userRepository.findByUserRole("admin", user.getEmail(), pageRequest)).thenReturn(page);

        // Act
        Page<User> usersPageRecieved = userService.getAllUsersByRoles("admin", user.getEmail(), pageRequest);
        // Assert
        assertTrue(page.getContent().containsAll(usersPageRecieved.getContent()));
        verify(userRepository, times(1)).findByUserRole("admin", user.getEmail(), pageRequest);
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
        when(userService.updateUser(user, user.getId()))
        .thenThrow(new RuntimeException(Constants.UPDATE_INACTIVE_USER_ERROR_MSG));

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
        when(userService.updateUser(user, user.getId()))
        .thenThrow(new RuntimeException(Constants.USER_DOES_NOT_EXIST_ERROR_MSG));

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
        .thenThrow(new RuntimeException(Constants.CHANGE_USER_STATUS_ERROR_MSG));
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
        .thenThrow(new RuntimeException(Constants.USER_DOES_NOT_EXIST_ERROR_MSG));
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
    public final void processForgotPassowrd() throws Exception {

        // Arrange
        User user = testDataRepository.getUsers()
                                      .get("user-1");

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);

        when(mailService.sendResetPasswordMail(user, "somerandompassword")).thenReturn(
            new AsyncResult<Boolean>(true));

        when(userRepository.findOne(user.getId())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        ResponseMsg responseMsg = userService.processForgotPassowrd(user.getEmail());

        assertTrue(responseMsg.getStatus());
        assertEquals(responseMsg.getMessage(), Constants.PASSWORD_RESET_SUCCESS);
    }

    @Test
    public final void generateRandomPassword() {

        // Arrange
        User user = testDataRepository.getUsers()
                                      .get("user-1");

        String password = userService.generateRandomPassword(user);
        assertNotNull(password);
    }
}
