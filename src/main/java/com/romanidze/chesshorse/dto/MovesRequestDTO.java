package com.romanidze.chesshorse.dto;

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
 * Модель для обработки входных данных алгоритма
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
public class MovesRequestDTO {

    /**
     * Ширина шахматной доски
     */
    private Integer width;

    /**
     * Длина шахматной доски
     */
    private Integer height;

    /**
     * Начальное положение фигуры
     */
    private PositionDTO start;

    /**
     * Конечное положение фигуры
     */
    private PositionDTO end;

}
