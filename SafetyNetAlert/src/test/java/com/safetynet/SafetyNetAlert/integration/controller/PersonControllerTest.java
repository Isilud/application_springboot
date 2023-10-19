package com.safetynet.SafetyNetAlert.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

        @Autowired
        private MockMvc mockMvc;

        public String defaultObject() throws JsonProcessingException {
                Map<String, String> requestMap = new HashMap<>();
                requestMap.put("firstName", "Jolan");
                requestMap.put("lastName", "Houot");
                requestMap.put("address", "address");
                requestMap.put("city", "city");
                requestMap.put("zip", "00000");
                requestMap.put("phone", "123456789");
                requestMap.put("email", "jolan@gmail.com");
                return new ObjectMapper().writeValueAsString(requestMap);
        }

        @AfterEach
        public void clear() throws JsonProcessingException, Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/person")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testGetPerson() throws Exception {
                mockMvc.perform(get("/person"))
                                .andExpect(status().isOk());
        }

        @Test
        public void testPostPerson() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/person")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        @Test
        public void testPutPerson() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/person")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isCreated());

                Map<String, String> updatedMap = new HashMap<>();
                updatedMap.put("firstName", "Jolan");
                updatedMap.put("lastName", "Houot");
                updatedMap.put("address", "address");
                updatedMap.put("city", "anotherCity");
                ;
                String updatedRequest = new ObjectMapper().writeValueAsString(updatedMap);
                mockMvc.perform(
                                MockMvcRequestBuilders.put("/person")
                                                .content(updatedRequest)
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testDeletePerson() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/person")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON));
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/person")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNoContent());
        }
}
