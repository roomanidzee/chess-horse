package com.romanidze.chesshorse.services.interfaces;

import com.romanidze.chesshorse.dto.MovesCalculationDTO;
import com.romanidze.chesshorse.dto.MovesRequestDTO;

/**
 * 12.06.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface CalculationService {

    MovesCalculationDTO getMovesCount(MovesRequestDTO movesRequestDTO);

}
