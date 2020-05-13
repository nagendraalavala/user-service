package com.perficient.service;


import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.service.dao.UserService;
import com.perficient.service.types.User;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserAPIController.class)
@SuppressWarnings({
        "PMD.AbstractClassWithoutAbstractMethod", // abstract required to recognize this class as a base class
})
public abstract class AbstractUserAPIControllerTests {
    protected static final String USERS_PATH = "/users";
    protected static final String DEFAULT_USER_PATH = "/users/user@example.com";
    protected static final String USER_URI_TEMPLATE = USERS_PATH + "/{username}";

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;

    @Autowired
    private WebApplicationContext context;

    protected static User buildDefaultUser() {
        final User user = new User();
        user.setUsername(TestUtils.DEFAULT_USERNAME);
        user.setPassword(TestUtils.DEFAULT_PASSWORD);
        user.setFirstName(TestUtils.DEFAULT_FIRST_NAME);
        user.setLastName(TestUtils.DEFAULT_LAST_NAME);
        return user;
    }

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

}