package com.safetynet.SafetyNetAlert.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getPeopleWithStationCoverage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/firestation")
                        .param("stationNumber", "1")
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
                        .param("firestation", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getFireInformation() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/fire")
                        .param("address", "951 LoneTree Rd")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}