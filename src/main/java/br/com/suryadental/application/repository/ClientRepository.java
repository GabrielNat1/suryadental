package br.com.suryadental.application.repository;

import br.com.suryadental.application.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByClientCode(String clientCode);
}
