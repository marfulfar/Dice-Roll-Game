package com.fargas.marcal.S5T2.services;


import com.fargas.marcal.S5T2.dtos.PlayerDTO;
import com.fargas.marcal.S5T2.entities.Player;
import com.fargas.marcal.S5T2.repositories.SQLRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final SQLRepo sqlRepo;
    private final ModelMapper mapper;

    public PlayerService(SQLRepo sqlRepo, ModelMapper mapper) {
        this.sqlRepo = sqlRepo;
        this.mapper = mapper;
    }


    public PlayerDTO addPlayerDTO(PlayerDTO playerDTO) {

        sqlRepo.save(DTOtoEntity(playerDTO));

        //TODO review what to return in this method
        return playerDTO;
    }


    public List<PlayerDTO> getAllPlayersDTO() {
        return sqlRepo.findAll().stream().map(p-> new PlayerDTO(p.getName())).collect(Collectors.toList());
    }


    private Player DTOtoEntity(PlayerDTO playerDTO){
        return mapper.map(playerDTO, Player.class);
    }




}//closes class
