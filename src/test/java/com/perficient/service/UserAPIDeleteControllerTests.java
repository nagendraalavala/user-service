package com.perficient.service;

import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

@SuppressWarnings({
        "PMD.JUnitTestsShouldIncludeAssert",  // MockMVC has embedded asserts in its andExpect method verifiers.
        "PMD.SignatureDeclareThrowsException", // This part of MockMVC design, cannot change in my code.
        "PMD.TooManyStaticImports" // Not too many if you use Spring REST docs and MockMVC
})
public class UserAPIDeleteControllerTests extends AbstractUserAPIControllerTests {

    @Test
    public void userDeleteReturnsNoContent() throws Exception {
        doReturn(true).when(userService).delete(TestUtils.DEFAULT_USERNAME);

        mockMvc.perform(RestDocumentationRequestBuilders.delete(USER_URI_TEMPLATE, TestUtils.DEFAULT_USERNAME)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("users-delete", pathParameters(
                        parameterWithName("username").description("Username of user to delete.")
                )));

    }

    @Test
    public void userDeleteReturnsNotFound() throws Exception {
        doReturn(false).when(userService).delete(TestUtils.DEFAULT_USERNAME);

        mockMvc.perform(delete(DEFAULT_USER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}