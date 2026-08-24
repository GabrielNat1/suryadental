package br.com.suryadental.application.repository;

import br.com.suryadental.application.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends MongoRepository<Client, UUID> {
}
