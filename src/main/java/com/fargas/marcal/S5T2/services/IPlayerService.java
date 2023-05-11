package com.fargas.marcal.S5T2.services;

import com.fargas.marcal.S5T2.dtos.GameDTO;
import com.fargas.marcal.S5T2.dtos.PlayerDTO;
import com.fargas.marcal.S5T2.dtos.RankingDTO;
import java.util.List;

public interface IPlayerService {

    PlayerDTO addPlayerDTO(PlayerDTO playerDTO);

    PlayerDTO updatePlayerName(String id, String name);


    GameDTO newGame(String id);


    List<GameDTO> listAllGamesPlayer(String id);


    List<RankingDTO> getAllPlayersRanked();


    void deleteAllGamesPlayer(String id);

}
