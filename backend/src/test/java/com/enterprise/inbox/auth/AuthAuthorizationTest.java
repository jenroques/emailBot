package com.enterprise.inbox.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("localdev")
class AuthAuthorizationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void loginAndMeReturnValidTokenAndPrincipal() throws Exception {
        String token = loginAndGetToken("analyst", "analyst123");

        mockMvc.perform(get("/api/auth/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("analyst"))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_ANALYST"));
    }

    @Test
    void analystCannotAccessAdminEndpoint() throws Exception {
        String analystToken = loginAndGetToken("analyst", "analyst123");

        mockMvc.perform(get("/api/audit/admin/events")
                        .header("Authorization", "Bearer " + analystToken))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanAccessAdminEndpoint() throws Exception {
        String adminToken = loginAndGetToken("admin", "admin123");

        mockMvc.perform(get("/api/audit/admin/events")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }

    @Test
    void protectedApiRequiresAuthentication() throws Exception {
        mockMvc.perform(post("/api/ingestion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"messageId\":\"m-1\",\"sender\":\"a@b.com\",\"subject\":\"s\",\"body\":\"b\"}"))
                .andExpect(status().isUnauthorized());
    }

    private String loginAndGetToken(String username, String password) throws Exception {
        String loginBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode node = objectMapper.readTree(loginResult.getResponse().getContentAsString());
        return node.get("accessToken").asText();
    }
}
