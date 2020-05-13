package com.perficient.service;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.perficient.service.types.User;

@SuppressWarnings({
        "PMD.JUnitTestsShouldIncludeAssert",  // MockMVC has embedded asserts in its andExpect method verifiers.
        "PMD.SignatureDeclareThrowsException", // This part of MockMVC design, cannot change in my code.
        "PMD.TooManyStaticImports" // Not too many if you use Spring REST docs and MockMVC
})
public class UserAPICreateControllerTests extends AbstractUserAPIControllerTests {

    // Add username and password length verification tests

    @Test
    public void noUserAPIReturnsBadRequest() throws Exception {
        mockMvc.perform(post(USERS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    /*@Test
    public void emptyUserAPIReturnsBadRequest() throws Exception {
        final User user = new User();
        mockMvc.perform(post(USERS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                //.andDo(print())
                .andExpect(ResponseBodyMatchers.responseBody()
                        .containsError("username", "must not be empty"))
                .andExpect(ResponseBodyMatchers.responseBody()
                        .containsError("password", "must not be empty"));
    }*/

    @Test
    public void validUserReturnsCreated() throws Exception {

        final User user = buildDefaultUser();

        mockMvc.perform(post(USERS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost:8080/users/user@example.com"))
                .andDo(document("users-created"));
    }

/*    @Test
    public void userWithNoRolesInSystemReturnsBadRequest() throws Exception {

        final User userAPI = buildDefaultUser();

        mockMvc.perform(post(USERS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userAPI)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Role named ROLE_VIEWER does not exist."));
    }

    @Test
    public void duplicateUserReturnsBadRequest() throws Exception {
    	
        final User user = buildDefaultUser();

        mockMvc.perform(post(USERS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username user@example.com already exists"));
    }*/

}