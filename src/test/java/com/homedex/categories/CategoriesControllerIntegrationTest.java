package com.homedex.categories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homedex.categories.models.Category;
import com.homedex.categories.models.CategoryRequest;
import com.homedex.categories.models.Item;
import com.homedex.categories.models.ItemRequest;
import com.homedex.households.models.Household;
import com.homedex.households.models.HouseholdRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CategoriesControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private String householdId;


    @BeforeEach
    void setUp() throws Exception {
        householdId = createHouseholdHelper().id().toString();
    }

    @Test
    void createCategory() throws Exception {
        createCategoryHelper();
    }

    @Test
    void getCategoriesForHousehold() throws Exception {
        createCategoryHelper();
        mockMvc.perform(get("/categories").param("household-id", householdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isNotEmpty())
                .andExpect(jsonPath("$.[0].id").isNotEmpty())
                .andExpect(jsonPath("$.[0].name").value("Fridge"))
                .andExpect(jsonPath("$.[0].householdId").value(householdId));
    }

    @Test
    void getCategory() throws Exception {
        Category category = createCategoryHelper();
        mockMvc.perform(get("/categories/{category-id}", category.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(category.id().toString()))
                .andExpect(jsonPath("$.name").value("Fridge"))
                .andExpect(jsonPath("$.householdId").value(householdId));
    }

    @Test
    void deleteCategory() throws Exception {
        Category category = createCategoryHelper();
        mockMvc.perform(delete("/categories/{category-id}", category.id()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/categories").param("household-id", householdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").isEmpty());
    }

    @Test
    void patchCategory() throws Exception {
        Category category = createCategoryHelper();
        CategoryRequest patchRequest = new CategoryRequest("Garage");
        mockMvc.perform(
                patch("/categories/{category-id}", category.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(patchRequest))
        ).andExpect(status().isNoContent());

        mockMvc.perform(get("/categories/{category-id}", category.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(category.id().toString()))
                .andExpect(jsonPath("$.name").value("Garage"))
                .andExpect(jsonPath("$.householdId").value(householdId));
    }

    // Items


    @Test
    void createItem() throws Exception {
        createItemHelper();
    }

    private Item createItemHelper() throws Exception {
        LocalDateTime expiration = LocalDateTime.MAX;
        ItemRequest item = new ItemRequest("Nectarines", "quantity", "Sprouts", null, expiration);
        String categoryId = createCategoryHelper().id().toString();
        MvcResult mvcResult = mockMvc.perform(post("/categories/{category-id}/items", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(item))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Nectarines"))
                .andExpect(jsonPath("$.measurement").value("quantity"))
                .andExpect(jsonPath("$.brand").value("Sprouts"))
                .andExpect(jsonPath("$.expiration").value(expiration.toString()))
                .andReturn();

        return mapper.readValue(mvcResult.getResponse().getContentAsString(), Item.class);
    }
    private Category createCategoryHelper() throws Exception {
        CategoryRequest category = new CategoryRequest("Fridge");
        MvcResult mvcResult = mockMvc.perform(post("/categories")
                        .param("household-id", householdId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(category))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Fridge"))
                .andExpect(jsonPath("$.householdId").value(householdId))
                .andReturn();

        return mapper.readValue(mvcResult.getResponse().getContentAsString(), Category.class);
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