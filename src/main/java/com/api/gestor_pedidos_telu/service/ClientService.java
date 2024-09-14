package com.api.gestor_pedidos_telu.service;

import com.api.gestor_pedidos_telu.domain.client.Client;
import com.api.gestor_pedidos_telu.dto.ClientDTO;
import com.api.gestor_pedidos_telu.infra.Exception.NotFoundException;
import com.api.gestor_pedidos_telu.repository.ClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getClients(String name) {

        if (name != null) {
            return clientRepository.findByNameContainingIgnoreCase(name)
                    .orElse(Collections.emptyList());
        }

        return clientRepository.findAll();
    }

    public Client getClientById(String id) {
        return findClientByIdOrThrow(id);
    }

    public Client createClient(ClientDTO data) {
        Client newClient = new Client(data);

        validateClientEmailUniqueness(data.email(), null);

        return clientRepository.save(newClient);
    }

    public Client updateClient(String id, ClientDTO data) {
        Client client = findClientByIdOrThrow(id);

        validateClientEmailUniqueness(data.email(), id);

        BeanUtils.copyProperties(data, client);

        return clientRepository.save(client);
    }

    public void deleteCategory(String id) {
        findClientByIdOrThrow(id);
        clientRepository.deleteById(id);
    }

    private void validateClientEmailUniqueness(String email, String clientId) {
        Optional<Client> existingClient = clientRepository.findByEmail(email);

        if (existingClient.isPresent() && !existingClient.get().getId().equals(clientId)) {
            throw new IllegalArgumentException("Cliente já registrado");
        }
    }

    private Client findClientByIdOrThrow(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
    }
}
