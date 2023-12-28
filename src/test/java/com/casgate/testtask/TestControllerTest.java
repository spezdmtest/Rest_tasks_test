package com.casgate.testtask;

import com.casgate.testtask.entity.ClientEntity;
import com.casgate.testtask.entity.RecordEntity;
import com.casgate.testtask.service.ClientService;
import com.casgate.testtask.service.RecordsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TestController.class)
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;
    private final List<ClientEntity> listOfClients = new ArrayList<>();

    @MockBean
    private RecordsService recordsService;
    private final List<RecordEntity> listOfRecords = new ArrayList<>();

    @Test
    public void getClientAll() throws Exception {
        listOfClients.add(ClientEntity.builder().id(1L).name("client1").createDate(LocalDateTime.now()).build());
        listOfClients.add(ClientEntity.builder().id(2L).name("client2").createDate(LocalDateTime.now()).build());
        given(clientService.findAll()).willReturn(listOfClients);

        var response = mockMvc.perform(get("/clients", listOfClients));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfClients.size())));
    }


    @Test
    public void getRecordAll() throws Exception {
        listOfRecords.add(RecordEntity.builder().id(1L).title("record 1").description("'desc 1")
                .createDate(LocalDateTime.now()).lastRead(LocalDateTime.now().plusSeconds(1)).build());
        listOfRecords.add(RecordEntity.builder().id(2L).title("record 2").description("'desc 2")
                .createDate(LocalDateTime.now()).lastRead(LocalDateTime.now().plusSeconds(1)).build());
        given(recordsService.findAll()).willReturn(listOfRecords);

        var response = mockMvc.perform(get("/records", listOfRecords));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfRecords.size())));
    }
}
