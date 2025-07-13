package com.example.jsondataset.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DatasetControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void insertAndSortRecord() throws Exception {
        // Insert 3 employee records
        String[] jsonRecords = {
                "{\"id\":2,\"name\":\"Alice\",\"age\":25,\"department\":\"Engineering\"}",
                "{\"id\":3,\"name\":\"Bob\",\"age\":35,\"department\":\"Engineering\"}",
                "{\"id\":4,\"name\":\"Charlie\",\"age\":28,\"department\":\"Engineering\"}"
        };

        for (String record : jsonRecords) {
            mockMvc.perform(post("/api/dataset/employees/record")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(record))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.dataset").value("employees"));
        }

        // Sort by "age" in descending order, numeric = true, limit = 2
        mockMvc.perform(get("/api/dataset/employees/query")
                        .param("sortBy", "age")
                        .param("order", "desc")
                        .param("isNumeric", "true")
                        .param("limit", "2")
                        .param("offset", "0")) // Important: include offset now!
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }
}
