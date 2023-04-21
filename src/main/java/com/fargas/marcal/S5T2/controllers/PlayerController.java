package com.fargas.marcal.S5T2.controllers;


import com.fargas.marcal.S5T2.dtos.PlayerDTO;
import com.fargas.marcal.S5T2.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerController {

    private final PlayerService playerService;

    private ResponseEntity<PlayerDTO> myResponseDto;
    private ResponseEntity<List<PlayerDTO>> myResponseListDto;
    private static final HttpStatus OK = HttpStatus.OK;
    private static final HttpStatus ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }



    @PostMapping("/players")
    public ResponseEntity<PlayerDTO> addPlayerDTO(@RequestBody PlayerDTO playerDTO){

        PlayerDTO myPlayerDTO = playerService.addPlayerDTO(playerDTO);

        return (myPlayerDTO!=null) ? new ResponseEntity<>(myPlayerDTO,OK) : new ResponseEntity<>(null, ERROR);
    }


    @GetMapping("/players")
    public ResponseEntity<List<PlayerDTO>> getAllPlayersDTO(){

        List<PlayerDTO> myListPlayerDTO = playerService.getAllPlayersDTO();

        return (myListPlayerDTO!=null) ? new ResponseEntity<>(myListPlayerDTO,OK) : new ResponseEntity<>(null, ERROR);

    }



}//closes class
