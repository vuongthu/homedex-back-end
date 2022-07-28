package com.homedex.households;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homedex.households.models.Household;
import com.homedex.households.models.HouseholdRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class HouseholdsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

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

    private Household createHouseholdHelper() throws Exception {
        HouseholdRequest household = new HouseholdRequest("Bear Family");
        MvcResult mvcResult = mockMvc.perform(post("/households").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(household)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Bear Family"))
                .andReturn();

        return mapper.readValue(mvcResult.getResponse().getContentAsString(), Household.class);
    }
}
