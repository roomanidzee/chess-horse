package com.romanidze.chesshorse.services;

import com.romanidze.chesshorse.application.ChessHorseApplication;
import com.romanidze.chesshorse.dto.PositionDTO;
import com.romanidze.chesshorse.services.interfaces.PositionService;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 12.06.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ChessHorseApplication.class})
public class PositionServiceTest {

    @Autowired
    private PositionService positionService;

    @Test
    public void testPossiblePosition(){

        int height = 7;
        int width = 8;

        PositionDTO positionDTO = PositionDTO.builder()
                                             .column(4)
                                             .row(3)
                                             .build();

        Assert.assertTrue(this.positionService.isPossible(height, width, positionDTO));

    }

    @Test
    public void testCorrectRetrieve(){

        List<String> testInput = Arrays.asList("A6", "F8", "G3");

        PositionDTO position1 = PositionDTO.builder()
                                           .column(0)
                                           .row(6)
                                           .build();

        PositionDTO position2 = PositionDTO.builder()
                                           .column(5)
                                           .row(8)
                                           .build();

        PositionDTO position3 = PositionDTO.builder()
                                           .column(6)
                                           .row(3)
                                           .build();

        List<PositionDTO> testOutput = Arrays.asList(position1, position2, position3);

        int limit = 3;

        IntStream.range(0, limit)
                 .forEachOrdered(
                         i -> Assert.assertEquals(testOutput.get(i), this.positionService.retrieve(testInput.get(i))));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadRetrieve(){
        String testInput = "66";
        PositionDTO testOutput = this.positionService.retrieve(testInput);
    }

}
