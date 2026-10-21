package br.com.suryadental.application.service;

import br.com.suryadental.application.dto.request.ClientRequest;
import br.com.suryadental.application.dto.response.ClientResponse;
import br.com.suryadental.application.exception.ClientNotFoundException;
import br.com.suryadental.application.model.Client;
import br.com.suryadental.application.repository.ClientRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {

    private static final Logger logger =
            LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientResponse> findAll() {
        logger.debug("Searching for all clients");

        List<ClientResponse> clients = clientRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();

        logger.info("Successfully retrieved {} clients", clients.size());

        return clients;
    }

    public ClientResponse findById(UUID id) {
        logger.debug("Searching for client with ID: {}", id);

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Client not found with ID: {}", id);
                    return new ClientNotFoundException("Client not found");
                });

        logger.info("Client found with ID: {}", id);

        return toResponse(client);
    }

    public ClientResponse create(ClientRequest request) {
        logger.debug("Creating client with code: {}", request.getClientCode());

        Client client = new Client();

        client.setName(request.getName());
        client.setClientCode(request.getClientCode());

        Client savedClient = clientRepository.save(client);

        logger.info("Client created successfully with ID: {}", savedClient.getId());

        return toResponse(savedClient);
    }

    public ClientResponse update(UUID id, ClientRequest request) {
        logger.debug("Updating client with ID: {}", id);

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cannot update client: ID {} not found", id);
                    return new ClientNotFoundException("Client not found");
                });

        client.setName(request.getName());
        client.setClientCode(request.getClientCode());

        Client updatedClient = clientRepository.save(client);

        logger.info("Client updated successfully with ID: {}", id);

        return toResponse(updatedClient);
    }

    public void delete(UUID id) {
        logger.debug("Deleting client with ID: {}", id);

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cannot delete client: ID {} not found", id);
                    return new ClientNotFoundException("Client not found");
                });

        clientRepository.delete(client);

        logger.info("Client deleted successfully with ID: {}", id);
    }

    private ClientResponse toResponse(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getClientCode()
        );
    }
}