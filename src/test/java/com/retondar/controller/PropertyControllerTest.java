package com.retondar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retondar.VivaspotipposApplicationTests;
import com.retondar.dto.ListPropertiesDto;
import com.retondar.dto.PropertyCreationDto;
import com.retondar.dto.PropertyDto;
import com.retondar.service.PropertyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by thiagoretondar on 25/06/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {VivaspotipposApplicationTests.class})
@WebAppConfiguration
public class PropertyControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @InjectMocks
    private PropertyController propertyController;

    @Mock
    private PropertyService propertyService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(propertyController).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void create() throws Exception {
        PropertyDto dto = createPropertyDto();
        PropertyCreationDto dtoCreation = createPropertyCreationDto();

        when(propertyService.saveProperty(any(PropertyCreationDto.class))).thenReturn(dto);

        mockMvc.perform(post("/properties")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(dtoCreation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("1a2b3c")))
                .andExpect(jsonPath("$.x", is(1)))
                .andExpect(jsonPath("$.y", is(2)))
                .andExpect(jsonPath("$.baths", is(3)))
                .andExpect(jsonPath("$.beds", is(4)))
                .andExpect(jsonPath("$.squareMeters", is(20)))
                .andExpect(jsonPath("$.provinces", is(Arrays.asList("Scavy"))));
    }

    @Test
    public void getById() throws Exception {
        String id = "1a2b3c";
        PropertyDto dto = createPropertyDto();

        when(propertyService.getById(id)).thenReturn(dto);

        mockMvc.perform(get("/properties/{id}", "1a2b3c"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.x", is(1)))
                .andExpect(jsonPath("$.y", is(2)))
                .andExpect(jsonPath("$.baths", is(3)))
                .andExpect(jsonPath("$.beds", is(4)))
                .andExpect(jsonPath("$.squareMeters", is(20)))
                .andExpect(jsonPath("$.provinces", is(Arrays.asList("Scavy"))));
    }

    @Test
    public void getByArea() throws Exception {
        Integer ax = 1, ay = 2, bx = 3, by = 4;
        ListPropertiesDto listPropertiesDto = createListPropertiesDto();

        when(propertyService.getListPropertiesByArea(ax, ay, bx, by)).thenReturn(listPropertiesDto);

        mockMvc.perform(get("/properties")
                .param("ax", ax.toString())
                .param("ay", ay.toString())
                .param("bx", bx.toString())
                .param("by", by.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.foundProperties", is(1)))
                .andExpect(jsonPath("$.properties[0].id", is("1a2b3c")))
                .andExpect(jsonPath("$.properties[0].x", is(1)))
                .andExpect(jsonPath("$.properties[0].y", is(2)))
                .andExpect(jsonPath("$.properties[0].baths", is(3)))
                .andExpect(jsonPath("$.properties[0].beds", is(4)))
                .andExpect(jsonPath("$.properties[0].squareMeters", is(20)))
                .andExpect(jsonPath("$.properties[0].provinces", is(Arrays.asList("Scavy"))));
    }

    private PropertyDto createPropertyDto() {
        PropertyDto dto = new PropertyDto();
        dto.setId("1a2b3c");
        dto.setX(1);
        dto.setY(2);
        dto.setBaths(3);
        dto.setBeds(4);
        dto.setSquareMeters(20);
        dto.setProvinces(Arrays.asList("Scavy"));

        return dto;
    }

    private PropertyCreationDto createPropertyCreationDto() {
        PropertyCreationDto dto = new PropertyCreationDto();
        dto.setPositionX(1);
        dto.setPositionY(2);
        dto.setAmountBaths(3);
        dto.setAmountBeds(4);
        dto.setSquareMeters(20);

        return dto;
    }

    private ListPropertiesDto createListPropertiesDto() {
        return new ListPropertiesDto(1, Arrays.asList(createPropertyDto()));
    }

}