package com.api.gestor_pedidos_telu.controller;

import com.api.gestor_pedidos_telu.domain.client.Client;
import com.api.gestor_pedidos_telu.dto.ClientDTO;
import com.api.gestor_pedidos_telu.service.ClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.api.gestor_pedidos_telu.infra.security.SecurityConfigurations.SECURITY_NAME;

@RestController()
@RequestMapping("/clients")
@Tag(name = "Client")
@SecurityRequirement(name = SECURITY_NAME)
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> getClients(
            @RequestParam(value = "name", required = false) String name
    ) {
        List<Client> clients = clientService.getClients(name);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable String id) {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody ClientDTO client) {
        Client newClient = clientService.createClient(client);
        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable String id, @Valid @RequestBody ClientDTO client) {
        Client newClient = clientService.updateClient(id, client);
        return ResponseEntity.ok(newClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable String id) {
        clientService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
