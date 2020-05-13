package com.perficient.service;

import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import com.perficient.service.types.User;

@SuppressWarnings({
        "PMD.JUnitTestsShouldIncludeAssert",  // MockMVC has embedded asserts in its andExpect method verifiers.
        "PMD.SignatureDeclareThrowsException", // This part of MockMVC design, cannot change in my code.
        "PMD.TooManyStaticImports" // Not too many if you use Spring REST docs and MockMVC
})
public class UserAPIReadControllerTests extends AbstractUserAPIControllerTests {

    /*@Test
    public void emptyDatabaseReturnsOk() throws Exception {
        mockMvc.perform(get(USERS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }*/

    @Test
    public void singleUserReturnsOk() throws Exception {

        final User user = buildDefaultUser();

        doReturn(user).when(userService).findByUsername(TestUtils.DEFAULT_USERNAME);

        mockMvc.perform(RestDocumentationRequestBuilders.get(USER_URI_TEMPLATE, TestUtils.DEFAULT_USERNAME)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(user, User.class))
                .andDo(document("users-get-one", pathParameters(
                        parameterWithName("username").description("Username of user to read.")
                )));
    }

    @Test
    public void singleUserDoesNotExistReturnsNotFound() throws Exception {

        mockMvc.perform(get(DEFAULT_USER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /*@Test
    public void readAllUsersReturnsOk() throws Exception {

        final User defaultUser = buildDefaultUser();

        final List<User> users = Arrays.asList(defaultUser);

        doReturn(users).when(userService).getAll();

        mockMvc.perform(get(USERS_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(ResponseBodyMatchers.responseBody().containsListAsJson(users, User.class))
                .andDo(document("users-get-all"));

    }*/

}