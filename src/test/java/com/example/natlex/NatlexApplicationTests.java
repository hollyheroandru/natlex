package com.example.natlex;

import com.example.natlex.dtos.requests.user.CreateUserRequest;
import com.example.natlex.models.GeologicalClass;
import com.example.natlex.models.Section;
import com.example.natlex.models.User;
import com.example.natlex.repositories.SectionRepository;
import com.example.natlex.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class NatlexApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUserValid() throws Exception {
        CreateUserRequest request = new CreateUserRequest("test", "password");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/natlex/api/v1/user")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        userRepository.delete(userRepository.findByUsername("test").orElseThrow());
    }

    @Test
    public void testCreateUserNotValid() throws Exception {
        CreateUserRequest request = new CreateUserRequest("te", "password");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/natlex/api/v1/user")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void testFindSectionsByGeoCode() throws Exception {
        Section section = new Section();
        section.setName("Test section");
        GeologicalClass geologicalClass = new GeologicalClass();
        geologicalClass.setName("Test geo name");
        geologicalClass.setCode("Test geo code");
        geologicalClass.setSection(section);
        List<GeologicalClass> geologicalClasses = new ArrayList<>();
        geologicalClasses.add(geologicalClass);
        section.setGeologicalClasses(geologicalClasses);
        sectionRepository.save(section);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/natlex/api/v1/sections/by-code?code=Test geo code")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").doesNotExist());

        sectionRepository.delete(section);
    }


    @Test
    void contextLoads() {
    }

}
