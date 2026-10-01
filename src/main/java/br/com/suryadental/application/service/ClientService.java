package br.com.suryadental.application.service;

import br.com.suryadental.application.dto.request.ClientRequest;
import br.com.suryadental.application.dto.response.ClientResponse;
import br.com.suryadental.application.exception.ClientNotFoundException;
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

    public List<ClientResponse> findAll() {
        return clientRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ClientResponse findById(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));

        return toResponse(client);
    }

    public ClientResponse create(ClientRequest request) {
        Client client = new Client();

        client.setName(request.getName());
        client.setClientCode(request.getClientCode());

        return toResponse(clientRepository.save(client));
    }

    public ClientResponse update(UUID id, ClientRequest request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));

        client.setName(request.getName());
        client.setClientCode(request.getClientCode());

        return toResponse(clientRepository.save(client));
    }

    public void delete(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));

        clientRepository.delete(client);
    }

    private ClientResponse toResponse(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getClientCode()
        );
    }
}