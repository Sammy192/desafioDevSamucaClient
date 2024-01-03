package com.samucadev.desafioclient.repositories;

import com.samucadev.desafioclient.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
