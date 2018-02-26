
package com.itt.kmt.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@SpringBootTest
@SuiteClasses({
    ArticleControllerTest.class
})
public class RESTControllerTestSuite {
}
