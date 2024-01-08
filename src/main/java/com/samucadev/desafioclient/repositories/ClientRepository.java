package com.samucadev.desafioclient.repositories;

import com.samucadev.desafioclient.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByCpf(String cpf);
}
