package com.rating.controller;

import com.rating.service.RatingControlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class RatingControlLevelControllerIT {

    public static final String RCL_BOOK_V_1_READ_ELIGIBILITY = "/rcl/book/v1/read/eligibility";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingControlService ratingControlService;

    @Test
    public void shouldReturnNotFound_whenCustomerControlLevelAndBookId_notProvided() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(RCL_BOOK_V_1_READ_ELIGIBILITY)
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequest_whenInvalidCustomerControlLevelAndBookId_isProvided() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(RCL_BOOK_V_1_READ_ELIGIBILITY + "/CONTROLXXXX*/ID**")
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldTrue_whenBookServiceControlLevelIsEqualTo_CustomerRatingControlLevel_ForRequestedBookId()
            throws Exception {
        // need service implementation here because to get 200 status we need to make a call to the service
        // @MockBean annotation. It allow to mock a class or an interface and to record and verify behaviors on it.
        given(ratingControlService.canReadBook(anyString(), anyString())).willReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get(RCL_BOOK_V_1_READ_ELIGIBILITY + "/12/B1234")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }


}