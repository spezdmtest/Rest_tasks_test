package com.casgate.testtask.service;

import com.casgate.testtask.entity.ClientEntity;
import com.casgate.testtask.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

public class ClientServiceTest {
    private ClientRepository clientRepository;
    private ClientService clientService;

    List<ClientEntity> clientEntity = List.of(new ClientEntity(1L, "client1", LocalDateTime.now()));

    @BeforeEach
    public void SetUpBeforeEach() {
        clientRepository = Mockito.mock(ClientRepository.class);
        clientService = new ClientService(clientRepository);
    }

    @Test
    public void findAll() {

        Mockito.when(clientRepository.findAll()).thenReturn(clientEntity);

        List<ClientEntity> clients = clientService.findAll();

        Assertions.assertNotNull(clients);
        Assertions.assertEquals(clientEntity, clients);
    }


}


