package com.safetynet.SafetyNetAlert.integration.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class UrlControllerTest {

        @Autowired
        private MockMvc mockMvc;

        public String defaultFirestation() throws JsonProcessingException {
                Map<String, String> requestMap = new HashMap<>();
                requestMap.put("address", "anAddress");
                requestMap.put("station", "aStationNumber");
                return new ObjectMapper().writeValueAsString(requestMap);
        }

        public String defaultPerson() throws JsonProcessingException {
                Map<String, String> requestMap = new HashMap<>();
                requestMap.put("firstName", "Jolan");
                requestMap.put("lastName", "Houot");
                requestMap.put("address", "anAddress");
                requestMap.put("city", "city");
                requestMap.put("zip", "00000");
                requestMap.put("phone", "123456789");
                requestMap.put("email", "jolan@gmail.com");
                return new ObjectMapper().writeValueAsString(requestMap);
        }

        public String defaultMedicalRecord() throws JsonProcessingException {
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

        @BeforeEach
        void setDatas() throws JsonProcessingException, Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/firestation")
                                                .content(defaultFirestation())
                                                .contentType(MediaType.APPLICATION_JSON));
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/person")
                                                .content(defaultPerson())
                                                .contentType(MediaType.APPLICATION_JSON));
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/medicalRecord")
                                                .content(defaultMedicalRecord())
                                                .contentType(MediaType.APPLICATION_JSON));
        }

        @AfterEach
        void clear() throws JsonProcessingException, Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/firestation")
                                                .content(defaultFirestation())
                                                .contentType(MediaType.APPLICATION_JSON));
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/person")
                                                .content(defaultPerson())
                                                .contentType(MediaType.APPLICATION_JSON));
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/medicalRecord")
                                                .content(defaultMedicalRecord())
                                                .contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        public void getPeopleWithStationCoverage() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/firestation")
                                                .param("stationNumber", "aStationNumber")
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void getChildrenAtAddress() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/childAlert")
                                                .param("address", "anAddress")
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void getPhoneWithStationCoverage() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/phoneAlert")
                                                .param("firestation", "aStationNumber")
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void getFireInformation() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/fire")
                                                .param("address", "anAddress")
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void getFloodCoverage() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/flood")
                                                .param("stations", "aStationNumber")
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void getPersonInfo() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/personInfo")
                                                .param("firstName", "Jolan")
                                                .param("lastName", "Houot")
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }
}