package com.samucadev.desafioclient.controllers;

import com.samucadev.desafioclient.dto.ClientDTO;
import com.samucadev.desafioclient.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ClientDTO> findAll(Pageable pageable) {
        Page<ClientDTO> dto = service.findAll(pageable);
        return dto;
    }
}
