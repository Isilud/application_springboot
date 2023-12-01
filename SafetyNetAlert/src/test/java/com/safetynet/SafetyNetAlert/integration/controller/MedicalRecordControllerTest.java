package com.safetynet.SafetyNetAlert.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class MedicalRecordControllerTest {

        @Autowired
        private MockMvc mockMvc;

        public String defaultObject() throws JsonProcessingException {
                Map<String, Object> requestMap = new HashMap<>();
                requestMap.put("firstName", "Jolan");
                requestMap.put("lastName", "Houot");
                requestMap.put("birthdate", "06/01/1993");
                List<String> medications = new ArrayList<>();
                medications.add("coffee");
                requestMap.put("medications", medications);
                List<String> allergies = new ArrayList<>();
                allergies.add("deadlines");
                requestMap.put("allergies", allergies);
                return new ObjectMapper().writeValueAsString(requestMap);

        }

        @AfterEach
        public void clear() throws JsonProcessingException, Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/medicalRecord")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void testGetMedicalRecord() throws Exception {
                mockMvc.perform(get("/medicalRecord"))
                                .andExpect(status().isOk());
        }

        @Test
        public void testPostMedicalRecord() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/medicalRecord")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        @Test
        public void testPutMedicalRecord() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/medicalRecord")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isCreated());

                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.put("firstName", "Jolan");
                updatedMap.put("lastName", "Houot");
                List<String> medications = new ArrayList<>();
                medications.add("coffee");
                medications.add("tea");
                updatedMap.put("medications", medications);
                ;
                String updatedRequest = new ObjectMapper().writeValueAsString(updatedMap);
                mockMvc.perform(
                                MockMvcRequestBuilders.put("/medicalRecord")
                                                .content(updatedRequest)
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testDeleteMedicalRecord() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/medicalRecord")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON));
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/medicalRecord")
                                                .content(defaultObject())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNoContent());
        }
}
