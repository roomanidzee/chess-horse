package com.romanidze.chesshorse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

/**
 * 12.06.2019
 *
 * Модель для обработки выходных данных алгоритма
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class MovesCalculationDTO {

    /**
     * Начальное положение коня
     */
    @JsonProperty("start_position")
    private PositionDTO startPosition;

    /**
     * Конечное положение коня
     */
    @JsonProperty("end_position")
    private PositionDTO endPosition;

    /**
     * Количество ходов от начального до конечного положения
     */
    @JsonProperty("moves_count")
    private Integer movesCount;

}
