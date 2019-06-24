package com.romanidze.chesshorse.services.implementations;

import com.romanidze.chesshorse.dto.MovesCalculationDTO;
import com.romanidze.chesshorse.dto.MovesRequestDTO;
import com.romanidze.chesshorse.dto.PositionDTO;
import com.romanidze.chesshorse.services.interfaces.CalculationService;
import com.romanidze.chesshorse.services.interfaces.PositionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * 12.06.2019
 *
 * Сервис для реализации алгоритма шахматного коня
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class CalculationServiceImpl implements CalculationService {

    private final PositionService positionService;

    // константы для ограничения диапазона ходов коня
    private final Integer HORSE_START_STEP = -2;
    private final Integer HORSE_END_STEP = 2;

    @Autowired
    public CalculationServiceImpl(PositionService positionService) {
        this.positionService = positionService;
    }

    /**
     * Метод для подсчёта минимального количества ходов коня
     *
     * @param movesRequestDTO входные данные алгоритма
     * @return информация о начальном положении, конечном положении, и количестве ходов
     */
    @Override
    public MovesCalculationDTO getMovesCount(MovesRequestDTO movesRequestDTO) {

        Integer height = movesRequestDTO.getHeight();
        Integer width = movesRequestDTO.getWidth();

        PositionDTO start = movesRequestDTO.getStart();
        PositionDTO end = movesRequestDTO.getEnd();

        if(!this.positionService.isPossible(height, width, start)){

            String errorMessage = "Стартовая позиция {} не вписывается в рамки поля";
            throw new IllegalArgumentException(MessageFormat.format(errorMessage, start.toString()));

        }

        if(!this.positionService.isPossible(height, width, end)){

            String errorMessage = "Конечная позиция {} не вписывается в рамки поля";
            throw new IllegalArgumentException(MessageFormat.format(errorMessage, end.toString()));

        }

        int[][] chessField = new int[width][height];

        Arrays.stream(chessField)
              .forEach(arr -> Arrays.fill(arr, -1));

        Deque<PositionDTO> horseStepQueue = new ArrayDeque<>();
        horseStepQueue.addLast(start);
        chessField[start.getColumn()][start.getRow()] = 0;

        while(!horseStepQueue.isEmpty()){

            PositionDTO position = horseStepQueue.removeFirst();

            if(position.equals(end)){
                break;
            }

            Integer column = position.getColumn();
            Integer row = position.getRow();

            for(int i = HORSE_START_STEP; i <= HORSE_END_STEP; i++){

                if(i == 0){
                    continue;
                }

                for(int j = HORSE_START_STEP; j <= HORSE_END_STEP; j++){

                    if(j == 0 || Math.abs(j) == Math.abs(i)){
                        continue;
                    }

                    Integer nextColumn = column + i;
                    Integer nextRow = row + j;

                    PositionDTO tempPosition = PositionDTO.builder()
                                                          .column(nextColumn)
                                                          .row(nextRow)
                                                          .build();

                    if(!this.positionService.isPossible(height, width, tempPosition)){
                        continue;
                    }

                    if(chessField[nextColumn][nextRow] == -1){

                        chessField[nextColumn][nextRow] = chessField[column][row] + 1;
                        horseStepQueue.addLast(tempPosition);

                    }

                }

            }

        }

        Integer result = chessField[end.getColumn()][end.getRow()];

        if(result == 0){
            result = -1;
        }

        return MovesCalculationDTO.builder()
                                  .startPosition(start)
                                  .endPosition(end)
                                  .movesCount(result)
                                  .build();

    }
}
