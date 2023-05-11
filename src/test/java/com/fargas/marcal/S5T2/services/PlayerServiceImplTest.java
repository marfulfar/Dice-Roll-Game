package com.fargas.marcal.S5T2.services;

import com.fargas.marcal.S5T2.dtos.GameDTO;
import com.fargas.marcal.S5T2.dtos.PlayerDTO;
import com.fargas.marcal.S5T2.dtos.RankingDTO;
import com.fargas.marcal.S5T2.entities.Game;
import com.fargas.marcal.S5T2.entities.Player;
import com.fargas.marcal.S5T2.exceptions.NotFoundException;
import com.fargas.marcal.S5T2.repositories.MongoPlayerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


class PlayerServiceImplTest {

    @Mock
    MongoPlayerRepo mongoPlayerRepo;

    @InjectMocks
    PlayerServiceImpl playerService;

    @Mock
    private ModelMapper mapper;

    Player testPlayerFull;
    PlayerDTO testPlayerDTO;
    List<Player> playersList;
    Game gameTest;
    GameDTO gameTestDTO;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        testPlayerFull = new Player("1","testSubject","today",new ArrayList<>());
        gameTest = new Game("11","1",(byte)3,(byte)4,true,"yesterday");
        testPlayerFull.getUserGames().add(gameTest);

        playersList = List.of(testPlayerFull);

        testPlayerDTO = new PlayerDTO("testSubjectDTO");

        gameTestDTO = new GameDTO("1",(byte)3,(byte)4,true,"yesterday");


    }

    @Test
    void addPlayerDTO() {
        //when
        when(mongoPlayerRepo.findAll()).thenReturn(playersList);
        when(mapper.map(testPlayerFull, PlayerDTO.class)).thenReturn(testPlayerDTO);

        playerService.addPlayerDTO(testPlayerDTO);

        verify(mongoPlayerRepo).save(any());
        assertEquals("testSubjectDTO",testPlayerDTO.getName());
        assertDoesNotThrow(()-> new NotFoundException("",""));

    }

    @Test
    void updatePlayerName() {
        //when
        when(mongoPlayerRepo.findById(anyString())).thenReturn(Optional.of(testPlayerFull));

        playerService.updatePlayerName(anyString(),"pepe");

        verify(mongoPlayerRepo).save(any());
        assertEquals("pepe",testPlayerFull.getName());

    }

    @Test
    void newGame() {
        //when
        when(mongoPlayerRepo.findById(anyString())).thenReturn(Optional.of(testPlayerFull));
        when(mongoPlayerRepo.save(testPlayerFull)).thenReturn(testPlayerFull);

        playerService.newGame("1");

        assertEquals(2,testPlayerFull.getUserGames().size());
        assertEquals("1", testPlayerFull.getUserGames().get(1).getUserID());


    }

    @Test
    void listAllGamesPlayer() {
        //when
        when(mongoPlayerRepo.findById(anyString())).thenReturn(Optional.ofNullable(testPlayerFull));

        List<GameDTO> gamesDTO = playerService.listAllGamesPlayer("");

        assertNotNull(gamesDTO);
        assertEquals(gamesDTO.size(),1);

    }

    @Test
    void getAllPlayersRanked() {
        //when
        when(mongoPlayerRepo.findAll()).thenReturn(playersList);

        List<RankingDTO> ranking = playerService.getAllPlayersRanked();

        assertDoesNotThrow(()-> new NotFoundException("",""));
        assertNotNull(ranking);
        assertEquals(1,ranking.size());

    }

    @Test
    void deleteAllGamesPlayer() {
        //when
        when(mongoPlayerRepo.findById("1")).thenReturn(Optional.of(testPlayerFull));

        playerService.deleteAllGamesPlayer("1");

        assertDoesNotThrow(()-> new DataAccessResourceFailureException(""));
        assertEquals(0,testPlayerFull.getUserGames().size());

    }
}