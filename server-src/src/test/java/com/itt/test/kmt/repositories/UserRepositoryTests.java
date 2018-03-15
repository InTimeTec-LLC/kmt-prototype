
package com.itt.test.kmt.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.itt.kmt.models.User;
import com.itt.kmt.repositories.UserRepository;
import com.itt.test_category.RepositoryTests;
import com.itt.test_data.TestDataRepository;

/**
 * The Sample tests, Note that the test uses Arrange,Act, Assert pattern to
 * logically separate the parts of the test code. Note that @DataMongoTest will
 * cause the tests to run against an in-memory mongo instance.
 *
 * @author Rakshit Rajeev
 */
@Category(RepositoryTests.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository repository;
    
    @Autowired
    private TestDataRepository testDataRepository;

    @Before
    public final void setUp() {

        repository.deleteAll();
        repository.save(testDataRepository.getUsers().get("user-1"));
        repository.save(testDataRepository.getUsers().get("user-2"));
    }

    @Test
    public final void onSaveSetId() {

        // Act
        User user = repository.save(testDataRepository.getUsers()
                                                       .get("user-3"));

        // Assert
        assertThat(user.getId()).isNotNull();

        // Cleanup
        repository.delete(user);
    }

    @Test
    public final void findByEmail() {

        // Arrange
        User user = testDataRepository.getUsers()
                                      .get("user-1");
        // Act
        User users = repository.findByEmailContainingIgnoreCase(user.getEmail());
        // Assert
        assertThat(users).isNotNull();
        assertThat(users.getEmail()).isEqualTo(user.getEmail());
    }
}
