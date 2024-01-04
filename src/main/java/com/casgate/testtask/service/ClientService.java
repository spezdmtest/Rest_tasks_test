package com.casgate.testtask.service;

import com.casgate.testtask.entity.ClientEntity;
import com.casgate.testtask.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {
    private ClientRepository clientRepository;

    public List<ClientEntity> findAll() {
        return clientRepository.findAll();
    }

    public ClientEntity getClientById(long id) {
        return clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
