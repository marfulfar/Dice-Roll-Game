package com.fargas.marcal.S5T2.controllers;


import com.fargas.marcal.S5T2.dtos.GameDTO;
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


    @PostMapping("/players/{id}/games")
    public ResponseEntity<GameDTO> newGame(@PathVariable("id") int id){

        GameDTO myGameDTO = playerService.newGame(id);

        return (myGameDTO!=null) ? new ResponseEntity<>(myGameDTO,OK) : new ResponseEntity<>(null, ERROR);
    }

    @GetMapping("/players/{id}/games")
    public ResponseEntity<List<GameDTO>> listAllGamesPlayer(@PathVariable("id") int id){

        List<GameDTO> myListGamesDTO = playerService.listAllGamesPlayer(id);

        return (myListGamesDTO!=null) ? new ResponseEntity<>(myListGamesDTO,OK) : new ResponseEntity<>(null, ERROR);
    }


    @DeleteMapping("/players/{id}/games")
    public ResponseEntity<Boolean> deleteAllGamesPlayer(@PathVariable("id") int id){

        playerService.deleteAllGamesPlayer(id);

        return new ResponseEntity<>(true,OK);
    }





}//closes class
