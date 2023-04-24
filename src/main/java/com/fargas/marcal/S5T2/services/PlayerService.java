package com.fargas.marcal.S5T2.services;


import com.fargas.marcal.S5T2.dtos.GameDTO;
import com.fargas.marcal.S5T2.dtos.PlayerDTO;
import com.fargas.marcal.S5T2.entities.Game;
import com.fargas.marcal.S5T2.entities.Player;
import com.fargas.marcal.S5T2.repositories.SQLGameRepo;
import com.fargas.marcal.S5T2.repositories.SQLPlayerRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final SQLPlayerRepo sqlPlayerRepo;
    private final SQLGameRepo sqlGameRepo;
    private final ModelMapper mapper;

    public PlayerService(SQLPlayerRepo sqlPlayerRepo, SQLGameRepo sqlGameRepo, ModelMapper mapper) {
        this.sqlPlayerRepo = sqlPlayerRepo;
        this.sqlGameRepo = sqlGameRepo;
        this.mapper = mapper;
    }


    public PlayerDTO addPlayerDTO(PlayerDTO playerDTO) {

        sqlPlayerRepo.save(DTOPlayertoEntity(playerDTO));

        //TODO review what to return in this method
        return playerDTO;
    }


    public List<PlayerDTO> getAllPlayersDTO() {
        return sqlPlayerRepo.findAll().stream().map(p-> new PlayerDTO(p.getName())).collect(Collectors.toList());
    }


    public GameDTO newGame(int id) {
        Player myPlayer;
        byte dice1, dice2;
        boolean victory = false;
        Random rand = new Random();

        //TODO custom throw
        myPlayer = sqlPlayerRepo.findById(id).orElseThrow();
        dice1 = (byte) rand.nextInt(0,7);
        dice2 = (byte) rand.nextInt(0,7);

        if ((dice1 + dice2)==7){ victory = true; }

        Game newGame = new Game(myPlayer.getId(),dice1,dice2,victory);

        return entityToGameDTO(sqlGameRepo.save(newGame));
    }


    public List<GameDTO> listAllGamesPlayer(int id) {
        return sqlGameRepo.findAll().stream().filter(g->g.getUserID() == id).map(this::entityToGameDTO).toList();
    }


    public void deleteAllGamesPlayer(int id) {
        List<Game> listOfGamesPlayer = sqlGameRepo.findAll().stream().filter(g->g.getUserID() == id).toList();
        sqlGameRepo.deleteAllInBatch(listOfGamesPlayer);
    }





    private Player DTOPlayertoEntity(PlayerDTO playerDTO){
        return mapper.map(playerDTO, Player.class);
    }

    private Game DTOGametoEntity(GameDTO gameDTO){
        return mapper.map(gameDTO, Game.class);
    }

    private GameDTO entityToGameDTO(Game game){return mapper.map(game, GameDTO.class);}


}//closes class
