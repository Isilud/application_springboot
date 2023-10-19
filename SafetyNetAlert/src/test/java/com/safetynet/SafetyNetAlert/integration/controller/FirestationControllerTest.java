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
public class FirestationControllerTest {

        @Autowired
        private MockMvc mockMvc;

        public String defaultObject() throws JsonProcessingException {
                Map<String, String> requestMap = new HashMap<>();
                requestMap.put("address", "anAddress");
                requestMap.put("station", "1");
                return new ObjectMapper().writeValueAsString(requestMap);
        }

        @AfterEach
        public void clear() throws JsonProcessingException, Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/firestation")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testGetFirestation() throws Exception {
                mockMvc.perform(get("/firestation"))
                                .andExpect(status().isOk());
        }

        @Test
        public void testPostFirestation() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/firestation")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        @Test
        public void testPutFirestation() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/firestation")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isCreated());

                Map<String, String> updatedMap = new HashMap<>();
                updatedMap.put("address", "anAddress");
                updatedMap.put("station", "2");
                String updatedRequest = new ObjectMapper().writeValueAsString(updatedMap);
                mockMvc.perform(
                                MockMvcRequestBuilders.put("/firestation")
                                                .content(updatedRequest)
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testDeleteFirestation() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/firestation")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON));
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/firestation")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNoContent());
        }

        @Test
        public void testDeleteFirestationWithAddress() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/firestation")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON));
                Map<String, String> updatedMap = new HashMap<>();
                updatedMap.put("address", "anAddress");
                String updatedRequest = new ObjectMapper().writeValueAsString(updatedMap);
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/firestation")
                                                .content(updatedRequest)
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNoContent());
        }

        @Test
        public void testDeleteFirestationWithStation() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/firestation")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON));
                Map<String, String> updatedMap = new HashMap<>();
                updatedMap.put("station", "1");
                String updatedRequest = new ObjectMapper().writeValueAsString(updatedMap);
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/firestation")
                                                .content(updatedRequest)
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNoContent());
        }
}
