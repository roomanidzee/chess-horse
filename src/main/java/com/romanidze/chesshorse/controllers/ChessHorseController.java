package com.romanidze.chesshorse.controllers;

import com.romanidze.chesshorse.dto.ErrorResponseDTO;
import com.romanidze.chesshorse.dto.MovesRequestDTO;
import com.romanidze.chesshorse.dto.PositionDTO;
import com.romanidze.chesshorse.services.interfaces.CalculationService;
import com.romanidze.chesshorse.services.interfaces.PositionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 12.06.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@RestController
public class ChessHorseController {

    private final PositionService positionService;
    private final CalculationService calculationService;

    @Autowired
    public ChessHorseController(PositionService positionService,
                                CalculationService calculationService) {
        this.positionService = positionService;
        this.calculationService = calculationService;
    }

    @GetMapping("/hourse/rest/count")
    public ResponseEntity calculateHorseMoves(@RequestParam Map<String,String> params){

        List<String> requiredParams = Arrays.asList("start", "end", "height", "width");

        boolean paramCheck = requiredParams.stream()
                                           .allMatch(params::containsKey);

        if(!paramCheck){

            ErrorResponseDTO errorResponseDTO =
                    ErrorResponseDTO.builder()
                                    .errorTime(LocalDateTime.now(ZoneId.of("Europe/Moscow")))
                                    .errorMessage("В запросе переданы не все необходимые параметры")
                                    .build();

            return ResponseEntity.badRequest().body(errorResponseDTO);

        }

        PositionDTO startPosition = this.positionService.retrieve(params.get("start"));
        PositionDTO endPosition = this.positionService.retrieve(params.get("end"));

        MovesRequestDTO movesRequestDTO = MovesRequestDTO.builder()
                                                         .height(Integer.valueOf(params.get("height")))
                                                         .width(Integer.valueOf(params.get("width")))
                                                         .start(startPosition)
                                                         .end(endPosition)
                                                         .build();

        return ResponseEntity.ok(this.calculationService.getMovesCount(movesRequestDTO));

    }

}
