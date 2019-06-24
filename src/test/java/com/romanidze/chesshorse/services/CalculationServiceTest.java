package com.romanidze.chesshorse.services;

import com.romanidze.chesshorse.application.ChessHorseApplication;
import com.romanidze.chesshorse.dto.MovesCalculationDTO;
import com.romanidze.chesshorse.dto.MovesRequestDTO;
import com.romanidze.chesshorse.dto.PositionDTO;
import com.romanidze.chesshorse.services.interfaces.CalculationService;
import com.romanidze.chesshorse.services.interfaces.PositionService;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 12.06.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ChessHorseApplication.class})
public class CalculationServiceTest {

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private PositionService positionService;

    @Test
    public void testGoodInput(){

        PositionDTO start = this.positionService.retrieve("B1");
        PositionDTO end = this.positionService.retrieve("A3");

        MovesRequestDTO testInput = MovesRequestDTO.builder()
                                                   .width(10)
                                                   .height(14)
                                                   .start(start)
                                                   .end(end)
                                                   .build();

        MovesCalculationDTO testOutput = MovesCalculationDTO.builder()
                                                            .startPosition(start)
                                                            .endPosition(end)
                                                            .movesCount(1)
                                                            .build();

        Assert.assertEquals(testOutput, this.calculationService.getMovesCount(testInput));

    }

    @Test
    public void testBadInput(){

        PositionDTO start = this.positionService.retrieve("A3");
        PositionDTO end = this.positionService.retrieve("A3");

        MovesRequestDTO testInput = MovesRequestDTO.builder()
                                                   .width(10)
                                                   .height(14)
                                                   .start(start)
                                                   .end(end)
                                                   .build();

        MovesCalculationDTO testOutput = MovesCalculationDTO.builder()
                                                            .startPosition(start)
                                                            .endPosition(end)
                                                            .movesCount(-1)
                                                            .build();

        Assert.assertEquals(testOutput, this.calculationService.getMovesCount(testInput));

    }

}
