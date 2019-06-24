package com.romanidze.chesshorse.services.interfaces;

import com.romanidze.chesshorse.dto.PositionDTO;

/**
 * 12.06.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface PositionService {

    PositionDTO retrieve(String position);
    boolean isPossible(Integer fieldHeight, Integer fieldWidth, PositionDTO position);

}
