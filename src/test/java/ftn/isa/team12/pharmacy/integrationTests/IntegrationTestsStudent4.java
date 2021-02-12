package ftn.isa.team12.pharmacy.integrationTests;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

import java.io.IOException;
import java.nio.charset.Charset;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTestsStudent4 {


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

    protected <T> T     mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }

    @Test
    public void testLoginSystemAdministrator() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("petar.petrovic@gmail.com");
        loginDTO.setPassword("Per@1234");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(contentType).accept(contentType)
                .content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().is(200));

    }

    @Test
    public void testGetLoyaltyProgram() throws Exception {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("petar.petrovic@gmail.com");
        loginDTO.setPassword("Per@1234");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(contentType).accept(contentType)
                .content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().is(200));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/loyaltyProgram/get")
        .contentType(contentType).accept(contentType)).andExpect(status().is(200));
    }

    @Test
    public void testGetAllCountry() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("petar.petrovic@gmail.com");
        loginDTO.setPassword("Per@1234");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(contentType).accept(contentType)
                .content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().is(200));


        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/country/all")
                .contentType(contentType).accept(contentType)).andExpect(status().is(200));

    }

    @Test
    public void testGetCityByCountry() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/city/byCountry")
                .contentType(contentType).accept(contentType).param("name", "Srbija")).andExpect(status().is(200));

    }

    @Test
    public void testGetMedicalStuffMarksByPatietn() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/medicalStuffMarks//medicalStuffToMark/" + "maca@faca.com")
                .contentType(contentType).accept(contentType).param("name", "Srbija")).andExpect(status().is(200));


    }







}
