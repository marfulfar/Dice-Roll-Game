package com.fargas.marcal.S5T2.services;

import com.fargas.marcal.S5T2.dtos.GameDTO;
import com.fargas.marcal.S5T2.dtos.PlayerDTO;
import com.fargas.marcal.S5T2.dtos.RankingDTO;
import com.fargas.marcal.S5T2.entities.Game;
import com.fargas.marcal.S5T2.entities.Player;
import com.fargas.marcal.S5T2.exceptions.NotFoundException;
import com.fargas.marcal.S5T2.repositories.MongoPlayerRepo;
import com.mongodb.MongoWriteException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PlayerServiceImpl implements IPlayerService{

    private final MongoPlayerRepo mongoPlayerRepo;
    private final ModelMapper mapper;


    public PlayerServiceImpl(MongoPlayerRepo mongoPlayerRepo, ModelMapper mapper) {
        this.mongoPlayerRepo = mongoPlayerRepo;
        this.mapper = mapper;
    }

    //TODO handle exception in add player

    public PlayerDTO addPlayerDTO(PlayerDTO playerDTO) {
        String name;
        LocalDateTime timeStamp = LocalDateTime.now();

        if (playerDTO == null || playerDTO.getName() == null){
            name="anonymous";
        }else{
            name= playerDTO.getName();
            List<String> players = mongoPlayerRepo.findAll().stream().map(Player::getName).toList();
                if (players.contains(name)) {
                throw new NotFoundException("","");
                }
        }

        return entityToPlayerDTO(mongoPlayerRepo.save(new Player(name,timeStamp.toString())));
    }

    public PlayerDTO updatePlayerName(String id, String name) {

        Player oldPlayer = mongoPlayerRepo.findById(id).orElseThrow(()->new NotFoundException("PLAYER_NOT_FOUND","Player not found"));

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

        myPlayer = mongoPlayerRepo.findById(id).orElseThrow(()->new NotFoundException("PLAYER_NOT_FOUND","Player not found"));

        dice1 = (byte) rand.nextInt(1,7);
        dice2 = (byte) rand.nextInt(1,7);

        if ((dice1 + dice2)==7){ victory = true; }

        Game newGame = new Game(myPlayer.getId(),dice1,dice2,victory,timeStamp.toString());
        myPlayer.getUserGames().add(newGame);
        mongoPlayerRepo.save(myPlayer);
        return entityToGameDTO(newGame);
    }


    public List<GameDTO> listAllGamesPlayer(String id) {
        return mongoPlayerRepo
                .findById(id)
                .orElseThrow(()->new NotFoundException("PLAYER_NOT_FOUND","Player not found"))
                .getUserGames()
                .stream()
                .map(this::entityToGameDTO)
                .toList();
    }


    public List<RankingDTO> getAllPlayersRanked() {
        return calculateRankingAllPlayers();
    }


    public void deleteAllGamesPlayer(String id){

        Player myPlayer = mongoPlayerRepo.findById(id).orElseThrow(()->new NotFoundException("PLAYER_NOT_FOUND","Player not found"));
        myPlayer.getUserGames().clear();

        try{
            mongoPlayerRepo.save(myPlayer); //Save method in mongo updates teh existing records if ID are equal

        }catch(DataAccessException | MongoWriteException exception) {
            throw new DataAccessResourceFailureException("Deletion not complete", exception);
        }
    }



///////////////////////////////////////////////////


    private List<RankingDTO> calculateRankingAllPlayers() {
        List<Player> players;

        try {
            players = mongoPlayerRepo.findAll();

        } catch (NotFoundException nfe) {
            throw new NotFoundException("USERS_NOT_FOUND", "Users not found");
        }

        List<RankingDTO> ranking = new ArrayList<>();

        players.forEach(player -> {
            long victories = player.getUserGames().stream().filter(Game::isVictory).count();
            float victoriesPerc = ((float) victories / player.getUserGames().size()) * 100;
            ranking.add(new RankingDTO(player.getName(), victoriesPerc));
        });

        return ranking;
    }


    private PlayerDTO entityToPlayerDTO(Player player){
        return mapper.map(player , PlayerDTO.class);
    }


    private GameDTO entityToGameDTO(Game game){return mapper.map(game, GameDTO.class);}




    /*











     */

}//closes class
