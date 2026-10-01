package br.com.suryadental.application.service;

import br.com.suryadental.application.exception.ApiException;
import br.com.suryadental.application.model.Client;
import br.com.suryadental.application.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(UUID id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ApiException("Client not found"));
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client update(UUID id, Client client) {
        Client existingClient = findById(id);

        existingClient.setName(client.getName());
        existingClient.setClientCode(client.getClientCode());

        return clientRepository.save(existingClient);
    }

    public void delete(UUID id) {
        Client client = findById(id);
        clientRepository.delete(client);
    }
}