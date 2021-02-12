package ftn.isa.team12.pharmacy.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import ftn.isa.team12.pharmacy.dto.LoginDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTestsStudent2 {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testLoginPhAdmin() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("marko@gmail.com");
        loginDTO.setPassword("marko");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(contentType).accept(contentType)
                .content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().is(200));

    }

    @Test
    public void getAllDrugs() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("marko@gmail.com");
        loginDTO.setPassword("marko");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(contentType).accept(contentType)
                .content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().is(200));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/drug/all")
                .contentType(contentType).accept(contentType)).andExpect(status().is(200));
    }

    @Test
    public void testGetAllManufacturer() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("marko@gmail.com");
        loginDTO.setPassword("marko");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(contentType).accept(contentType)
                .content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().is(200));


        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/manufacturer/all")
                .contentType(contentType).accept(contentType)).andExpect(status().is(200));

    }

    @Test
    public void testGetEReceptByPatient() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/erecepie/getPatientERecepies/" + "maca@faca.com")
                .contentType(contentType).accept(contentType)).andExpect(status().is(200));

    }

    @Test
    public void getAllergiesByPatient() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/patient/allergies/" + "maca@faca.com")
                .contentType(contentType).accept(contentType)).andExpect(status().is(200));
    }



}