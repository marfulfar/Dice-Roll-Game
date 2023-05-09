package com.fargas.marcal.S5T2.controllers;


import com.fargas.marcal.S5T2.services.AuthService;
import com.fargas.marcal.S5T2.auth.AuthenticationRequest;
import com.fargas.marcal.S5T2.auth.AuthenticationResponse;
import com.fargas.marcal.S5T2.auth.RegisterRequest;
import com.fargas.marcal.S5T2.dtos.GameDTO;
import com.fargas.marcal.S5T2.dtos.PlayerDTO;
import com.fargas.marcal.S5T2.dtos.RankingDTO;
import com.fargas.marcal.S5T2.entities.User;
import com.fargas.marcal.S5T2.exceptions.NotFoundException;
import com.fargas.marcal.S5T2.repositories.MongoTokenRepo;
import com.fargas.marcal.S5T2.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlayerController {

    @Autowired
    private final MongoTokenRepo mongoTokenRepo;

    private final PlayerService playerService;
    private final AuthService authService;
    private static final HttpStatus OK = HttpStatus.OK;
    private static final HttpStatus ERROR = HttpStatus.INTERNAL_SERVER_ERROR;


    /*
    The @ExceptionHandler annotation is used to handle exceptions that occur during the execution of a Spring MVC controller.
    When an exception is thrown from a controller method, Spring looks for a method in the same controller class that is annotated with @ExceptionHandler
    and has a matching exception type.

    Here are the steps that Spring follows when using @ExceptionHandler:
    1. An exception is thrown during the execution of a controller method.
    2. Spring looks for a method in the same controller class that is annotated with @ExceptionHandler.
    3. If a matching @ExceptionHandler method is found, Spring invokes it with the thrown exception as a parameter.
    4. The @ExceptionHandler method handles the exception and returns a response to the client.
     */

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> throwMyNotFoundException(NotFoundException nfe) {

        return new ResponseEntity<>(nfe.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> throwDataAccessException(DataAccessException dae) {

        return new ResponseEntity<>(dae.getMessage(), HttpStatus.I_AM_A_TEAPOT);
    }




    @PostMapping("/players")
    public ResponseEntity<PlayerDTO> addPlayer(@RequestBody(required = false) PlayerDTO playerDTO ){

        PlayerDTO myPlayerDTO = playerService.addPlayerDTO(playerDTO);

        return (myPlayerDTO!=null) ? new ResponseEntity<>(myPlayerDTO,OK) : new ResponseEntity<>(null, ERROR);
    }


    @PutMapping("/players/{id}")
    public ResponseEntity<PlayerDTO> updatePlayerName(@PathVariable("id") String id, @RequestBody PlayerDTO playerDTO){

        PlayerDTO myPlayerDTO = playerService.updatePlayerName(id, playerDTO.getName());

        return (myPlayerDTO!=null) ? new ResponseEntity<>(myPlayerDTO,OK) : new ResponseEntity<>(null, ERROR);
    }


    @PostMapping("/players/{id}/games")
    public ResponseEntity<GameDTO> newGame(@PathVariable("id") String id){

        GameDTO myGameDTO = playerService.newGame(id);

        return (myGameDTO!=null) ? new ResponseEntity<>(myGameDTO,OK) : new ResponseEntity<>(null, ERROR);
    }


    @GetMapping("/players/{id}/games")
    public ResponseEntity<List<GameDTO>> listAllGamesPlayer(@PathVariable("id") String id){

        List<GameDTO> myListGamesDTO = playerService.listAllGamesPlayer(id);

        return (myListGamesDTO!=null) ? new ResponseEntity<>(myListGamesDTO,OK) : new ResponseEntity<>(null, ERROR);
    }


    @GetMapping("/players")
    public ResponseEntity<List<RankingDTO>> getAllPlayersRank(){

        List<RankingDTO> myRankingPlayerDTO = playerService.getAllPlayersRanked();

        return (myRankingPlayerDTO!=null) ? new ResponseEntity<>(myRankingPlayerDTO,OK) : new ResponseEntity<>(null, ERROR);

    }


    @DeleteMapping("/players/{id}/games")
    public ResponseEntity<Boolean> deleteAllGamesPlayer(@PathVariable("id") String id){

        playerService.deleteAllGamesPlayer(id);

        return new ResponseEntity<>(true,OK);
    }


    @GetMapping("/players/ranking/winner")
    public ResponseEntity<RankingDTO> getTopPlayer(){

        RankingDTO myTopRank = playerService.getAllPlayersRanked()
                .stream()
                .sorted((p1,p2)-> (int) (p2.getVictoriesPerc()-p1.getVictoriesPerc()))
                .limit(1)
                .findFirst()
                .orElseThrow(()->new NotFoundException("PLAYER_NOT_FOUND","Player not found"));

        return (myTopRank!=null) ? new ResponseEntity<>(myTopRank,OK) : new ResponseEntity<>(null, ERROR);
    }


    @GetMapping("/players/ranking/loser")
    public ResponseEntity<RankingDTO> getBottomPlayer(){

        RankingDTO myBottomRank = playerService.getAllPlayersRanked()
                .stream()
                .sorted((p1,p2)-> (int) (p1.getVictoriesPerc()-p2.getVictoriesPerc()))
                .limit(1)
                .findFirst()
                .orElseThrow(()->new NotFoundException("PLAYER_NOT_FOUND","Player not found"));

        return (myBottomRank!=null) ? new ResponseEntity<>(myBottomRank,OK) : new ResponseEntity<>(null, ERROR);
    }


    @GetMapping("/players/ranking")
    public ResponseEntity<RankingDTO> getAverageRank(){
        long playerNum;
        List<Float> myPercentageRank;
        float sumPercentage;
        float averagePerc;

        myPercentageRank= playerService.getAllPlayersRanked()
                .stream()
                .map(RankingDTO::getVictoriesPerc)
                .toList();

        playerNum = myPercentageRank.size();

        sumPercentage = myPercentageRank.stream().reduce(0f, Float::sum);

        averagePerc = sumPercentage/playerNum;

        return new ResponseEntity<>(new RankingDTO("Average all players",averagePerc),OK);
    }


}//closes class
