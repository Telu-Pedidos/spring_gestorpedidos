package com.api.gestor_pedidos_telu.service;

import com.api.gestor_pedidos_telu.domain.client.Client;
import com.api.gestor_pedidos_telu.dto.ClientDTO;
import com.api.gestor_pedidos_telu.infra.exception.NotFoundException;
import com.api.gestor_pedidos_telu.repository.ClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.api.gestor_pedidos_telu.utils.Phone.*;


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

        validateClientPhoneUniqueness(data.phone(), null);

        return clientRepository.save(newClient);
    }

    public Client updateClient(String id, ClientDTO data) {
        Client client = findClientByIdOrThrow(id);

        validateClientPhoneUniqueness(data.phone(), id);

        BeanUtils.copyProperties(data, client);

        return clientRepository.save(client);
    }

    public void deleteCategory(String id) {
        findClientByIdOrThrow(id);
        clientRepository.deleteById(id);
    }

    private void validateClientPhoneUniqueness(String phone, String clientId) {
        if (phone == null || phone.trim().isEmpty()) {
            return;
        }

        boolean validPhone = isPhoneValid(phone);

        if (!validPhone) {
            throw new IllegalArgumentException("O telefone deve ser válido, com ou sem DDD, e no formato (XX) XXXXX-XXXX ou similar");
        }

        Optional<Client> existingClient = clientRepository.findByPhone(phone);

        if (existingClient.isPresent() && !existingClient.get().getId().equals(clientId)) {
            throw new IllegalArgumentException("Telefone já registrado");
        }
    }

    private Client findClientByIdOrThrow(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
    }
}
