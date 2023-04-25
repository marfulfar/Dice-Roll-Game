package com.fargas.marcal.S5T2.controllers;


import com.fargas.marcal.S5T2.dtos.GameDTO;
import com.fargas.marcal.S5T2.dtos.PlayerDTO;
import com.fargas.marcal.S5T2.dtos.RankingDTO;
import com.fargas.marcal.S5T2.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerController {

    private final PlayerService playerService;
    private static final HttpStatus OK = HttpStatus.OK;
    private static final HttpStatus ERROR = HttpStatus.INTERNAL_SERVER_ERROR;


    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    @PostMapping("/players")
    public ResponseEntity<PlayerDTO> addPlayer(@RequestBody PlayerDTO playerDTO){

        PlayerDTO myPlayerDTO = playerService.addPlayerDTO(playerDTO);

        return (myPlayerDTO!=null) ? new ResponseEntity<>(myPlayerDTO,OK) : new ResponseEntity<>(null, ERROR);
    }


    @PutMapping("/players")
    public ResponseEntity<PlayerDTO> updatePlayerName(@RequestBody PlayerDTO playerDTO){

        return null;
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

        //TODO check custom throw
        RankingDTO myTopRank = playerService.getAllPlayersRanked()
                .stream()
                .sorted((p1,p2)-> (int) (p2.getVictoriesPerc()-p1.getVictoriesPerc()))
                .limit(1)
                .findFirst()
                .orElseThrow();

        return new ResponseEntity<>(myTopRank,OK);
    }


    @GetMapping("/players/ranking/loser")
    public ResponseEntity<RankingDTO> getBottomPlayer(){

        //TODO check custom throw
        RankingDTO myBottomRank = playerService.getAllPlayersRanked()
                .stream()
                .sorted((p1,p2)-> (int) (p1.getVictoriesPerc()-p2.getVictoriesPerc()))
                .limit(1)
                .findFirst()
                .orElseThrow();

        return new ResponseEntity<>(myBottomRank,OK);
    }


    @GetMapping("/players/ranking")
    public ResponseEntity<RankingDTO> getAverageRank(){
        long playerNum;
        List<Float> myPercentageRank;
        float sumPercentage = 0;
        float averagePerc;

        myPercentageRank= playerService.getAllPlayersRanked()
                .stream()
                .map(RankingDTO::getVictoriesPerc)
                .toList();

        playerNum = myPercentageRank.size();

        for (Float percentage:myPercentageRank) {
            sumPercentage += percentage;
        }

        averagePerc = sumPercentage/playerNum;

        return new ResponseEntity<>(new RankingDTO("Average all players",averagePerc),OK);
    }


}//closes class
