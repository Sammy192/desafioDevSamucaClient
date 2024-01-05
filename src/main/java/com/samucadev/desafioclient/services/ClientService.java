package com.samucadev.desafioclient.services;

import com.samucadev.desafioclient.dto.ClientDTO;
import com.samucadev.desafioclient.entities.Client;
import com.samucadev.desafioclient.repositories.ClientRepository;
import com.samucadev.desafioclient.services.exceptions.DatabaseException;
import com.samucadev.desafioclient.services.exceptions.DatabaseExceptionCpfValidation;
import com.samucadev.desafioclient.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        Page<Client> result = repository.findAll(pageable);
        return result.map(x -> new ClientDTO(x));
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Client client = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado."));

        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        try {
            Client entity = new Client();
            copyDtoToEntity(dto, entity);

            entity = repository.save(entity);
            return new ClientDTO(entity);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseExceptionCpfValidation("O CPF que deseja inserir já existe na base de dados.");
        }
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);

            entity = repository.saveAndFlush(entity);
            return new ClientDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado.");
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseExceptionCpfValidation("O CPF que deseja atualizar já existe na base de dados.");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado.");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setChildren(dto.getChildren());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
    }
}
