package com.casgate.testtask;

import com.casgate.testtask.entity.ClientEntity;
import com.casgate.testtask.entity.RecordEntity;
import com.casgate.testtask.service.ClientService;
import com.casgate.testtask.service.RecordsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private final ClientEntity clientEntity = new ClientEntity(1L, "client1", LocalDateTime.now());

    @BeforeEach
    public void setUp() {
        given(clientService.getClientById(clientEntity.getId())).willReturn(clientEntity);
    }

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
        listOfRecords.add(RecordEntity.builder().id(3L).title("record 3").description("'desc 3")
                .createDate(LocalDateTime.now()).lastRead(LocalDateTime.now().plusSeconds(1)).build());

        given(recordsService.findAll()).willReturn(listOfRecords);

        var response = mockMvc.perform(get("/records", listOfRecords));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfRecords.size())));
    }

    @Test
    public void getClientById() throws Exception {
        var response = mockMvc.perform(get("/clients/{id}", clientEntity.getId()));
        response.andDo(print());
        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("client1")))
                .andDo(print());
    }

    @Test
    public void getClientById_entityNotFound() throws Exception {
        long clientId = 2L;
        given(clientService.getClientById(clientId)).willThrow(EntityNotFoundException.class);
        var response = mockMvc.perform(get("/clients/{id}", clientId));
        response.andDo(print());
        response.andExpect(status().isNotFound());
    }

    @Test
    public void getRecordsByClientId() throws Exception {
        listOfRecords.add(RecordEntity.builder().id(1L).title("record 1").description("'desc 1")
                .createDate(LocalDateTime.now()).lastRead(LocalDateTime.now().plusSeconds(1)).userId(1L).build());
        listOfRecords.add(RecordEntity.builder().id(2L).title("record 2").description("'desc 2")
                .createDate(LocalDateTime.now()).lastRead(LocalDateTime.now().plusSeconds(1)).userId(1L).build());
        listOfRecords.add(RecordEntity.builder().id(3L).title("record 3").description("'desc 3")
                .createDate(LocalDateTime.now()).lastRead(LocalDateTime.now().plusSeconds(1)).userId(1L).build());

        given(recordsService.getRecordsByClientId(clientEntity.getId())).willReturn(listOfRecords);

        var response = mockMvc.perform(get("/clients/{clientId}/records", clientEntity.getId()));
        response.andDo(print());
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfRecords.size())));
    }

    @Test
    public void getRecordsByClientId_entityNotFound() throws Exception {
        long clientId = 2L;
        given(recordsService.getRecordsByClientId(clientId)).willThrow(EntityNotFoundException.class);
        var response = mockMvc.perform(get("/clients/{clientId}/records", clientId));
        response.andExpect(status().isNotFound())
                .andDo(print());
    }
}
