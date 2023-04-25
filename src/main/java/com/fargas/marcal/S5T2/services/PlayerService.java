package com.fargas.marcal.S5T2.services;


import com.fargas.marcal.S5T2.dtos.GameDTO;
import com.fargas.marcal.S5T2.dtos.PlayerDTO;
import com.fargas.marcal.S5T2.dtos.RankingDTO;
import com.fargas.marcal.S5T2.entities.Game;
import com.fargas.marcal.S5T2.entities.Player;
import com.fargas.marcal.S5T2.repositories.MongoGameRepo;
import com.fargas.marcal.S5T2.repositories.MongoPlayerRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PlayerService {

    private final MongoPlayerRepo mongoPlayerRepo;
    private final MongoGameRepo mongoGameRepo;
    private final ModelMapper mapper;


    public PlayerService(MongoPlayerRepo mongoPlayerRepo, MongoGameRepo mongoGameRepo, ModelMapper mapper) {
        this.mongoPlayerRepo = mongoPlayerRepo;
        this.mongoGameRepo = mongoGameRepo;
        this.mapper = mapper;
    }


    public PlayerDTO addPlayerDTO(PlayerDTO playerDTO) {

        LocalDateTime timeStamp = LocalDateTime.now();

        return entityToPlayerDTO(mongoPlayerRepo.save(new Player(playerDTO.getName(), timeStamp.toString())));
    }

    public PlayerDTO updatePlayerName(String id, String name) {

        //TODO check throw
        Player oldPlayer = mongoPlayerRepo.findAll().stream().filter(p->p.getId().contentEquals(id)).findFirst().orElseThrow();
        oldPlayer.setName(name);
        mongoPlayerRepo.save(oldPlayer); //Save method in mongo updates teh existing records if ID are equal

        return entityToPlayerDTO(oldPlayer);
    }


    public GameDTO newGame(String id) {
        Player myPlayer;
        byte dice1, dice2;
        boolean victory = false;
        Random rand = new Random();
        LocalDateTime timeStamp = LocalDateTime.now();

        //TODO custom throw
        myPlayer = mongoPlayerRepo.findAll().stream().filter(p->p.getId().contentEquals(id)).findFirst().orElseThrow();
        dice1 = (byte) rand.nextInt(1,7);
        dice2 = (byte) rand.nextInt(1,7);

        if ((dice1 + dice2)==7){ victory = true; }

        Game newGame = new Game(myPlayer.getId(),dice1,dice2,victory,timeStamp.toString());

        return entityToGameDTO(mongoGameRepo.save(newGame));
    }


    public List<GameDTO> listAllGamesPlayer(String id) {
        return mongoGameRepo.findAll().stream().filter(g->g.getUserID().contentEquals(id)).map(this::entityToGameDTO).toList();
    }


    public List<RankingDTO> getAllPlayersRanked() {
        return calculateRankingAllPlayers();
    }


    public void deleteAllGamesPlayer(String id) {
        List<Game> listOfGamesPlayer = mongoGameRepo.findAll().stream().filter(g->g.getUserID().contentEquals(id)).toList();
        mongoGameRepo.deleteAll(listOfGamesPlayer);
    }



///////////////////////////////////////////////////


    private List<RankingDTO> calculateRankingAllPlayers(){

        List<Player> players = mongoPlayerRepo.findAll();
        List<Game> games = mongoGameRepo.findAll();
        List<RankingDTO> ranking = new ArrayList<>();
        int gamesNum=0, victories=0;
        float victoriesPerc;

        for (Player player:players) {
            for (Game game:games) {
                if (game.getUserID().contentEquals(player.getId())){
                    gamesNum++;
                    if (game.isVictory()){
                        victories++;
                    }
                }
            }
            victoriesPerc = Math.round((((float) victories /gamesNum)*100));
            ranking.add(new RankingDTO(player.getName(), victoriesPerc));
        }

        return ranking;
    }


    private Player dtoPlayertoEntity(PlayerDTO playerDTO){
        return mapper.map(playerDTO, Player.class);
    }

    private PlayerDTO entityToPlayerDTO(Player player){
        return mapper.map(player , PlayerDTO.class);
    }

    private Game dtoGametoEntity(GameDTO gameDTO){
        return mapper.map(gameDTO, Game.class);
    }

    private GameDTO entityToGameDTO(Game game){return mapper.map(game, GameDTO.class);}




    /*











     */

}//closes class
