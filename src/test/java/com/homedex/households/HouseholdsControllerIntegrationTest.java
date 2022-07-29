package com.homedex.households;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homedex.households.models.Household;
import com.homedex.households.models.HouseholdRequest;
import com.homedex.users.models.User;
import com.homedex.users.models.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class HouseholdsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private String userId;

    @BeforeEach
    void setUp() throws Exception {
        userId = createUserHelper().getId().toString();
    }

    @Test
    void createHousehold() throws Exception {
        createHouseholdHelper();
    }

    @Test
    void getHouseholds() throws Exception {
        Household household = createHouseholdHelper();
        mockMvc.perform(get("/households"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(household.id().toString()))
                .andExpect(jsonPath("$.[0].name").value("Bear Family"));
    }

    @Test
    void getHousehold() throws Exception {
        Household household = createHouseholdHelper();
        mockMvc.perform(get("/households/{id}", household.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(household.id().toString()))
                .andExpect(jsonPath("$.name").value("Bear Family"));
    }

    @Test
    void deleteHousehold() throws Exception {
        Household household = createHouseholdHelper();
        mockMvc.perform(delete("/households/{id}", household.id()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/households"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isEmpty());
    }

    @Test
    void updateHouseholdName() throws Exception {
        Household household = createHouseholdHelper();
        HouseholdRequest request = new HouseholdRequest("We Bare Bears");
        mockMvc.perform(patch("/households/{id}", household.id()).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/households/{id}", household.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(household.id().toString()))
                .andExpect(jsonPath("$.name").value("We Bare Bears"));
    }

    @Test
    void getHouseholdUserMappings() throws Exception {
        Household household = createHouseholdHelper();
        mockMvc.perform(get("/households/mappings").param("user-id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value(household.id().toString()));
    }

    private Household createHouseholdHelper() throws Exception {
        HouseholdRequest request = new HouseholdRequest("Bear Family");
        MvcResult mvcResult = mockMvc.perform(
                        post("/households")
                                .param("user-id", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Bear Family"))
                .andReturn();

        return mapper.readValue(mvcResult.getResponse().getContentAsString(), Household.class);
    }

    private User createUserHelper() throws Exception {
        UserRequest user = new UserRequest("username", "test@example.com");
        MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andReturn();

        return mapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
    }
}
