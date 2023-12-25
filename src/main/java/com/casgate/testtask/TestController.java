package com.casgate.testtask;

import com.casgate.testtask.entity.ClientEntity;
import com.casgate.testtask.entity.RecordEntity;
import com.casgate.testtask.service.ClientService;
import com.casgate.testtask.service.RecordsService;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@AllArgsConstructor
public class TestController {
    private RecordsService recordsService;
    private ClientService clientService;

    @GetMapping("/clients")
    public List<ClientEntity> getClients() {
        return clientService.findAll();
    }

    @GetMapping("/records")
    public List<RecordEntity> getRecords() {
        return recordsService.findAll();
    }

    /**
     * 1. Method must return client object by id or 404 http status code
     * 2. Unit tests
     */
    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientEntity> getClientById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(clientService.getClientById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Method must return client records by client id
     * 1. All returned records should be updated with lastRead as current timestamp
     * 2. LastRead in response should be old value before update.
     * 3. Records must be sorted by lastRead DESC (null last read must be sorted by createDate)
     * 4. Unit tests
     */
    @GetMapping("/clients/{clientId}/records")
    public ResponseEntity<List<RecordEntity>> getRecordsByClientId(@PathVariable long clientId) {
        try {
            return ResponseEntity.ok(recordsService.getRecordsByClientId(clientId));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 1. Method must delete all records by clientId with lastRead not null and lastRead < current_timestamp - 5 minutes
     * 2. Number of deleted records should be returned in the response
     * 3. Unit tests
     */
    @DeleteMapping("/clients/{clientId}/records")
    public long deleteReadRecords(@PathVariable long clientId) {
        return 0;
    }
}
