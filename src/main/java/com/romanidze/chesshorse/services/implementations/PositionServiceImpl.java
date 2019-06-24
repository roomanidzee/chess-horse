package com.romanidze.chesshorse.services.implementations;

import com.romanidze.chesshorse.dto.PositionDTO;
import com.romanidze.chesshorse.services.interfaces.PositionService;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 12.06.2019
 *
 * Сервис для работы с положением коня на шахматной доске
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class PositionServiceImpl implements PositionService {

    private final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String LETTERS_REGEXP = "[A-Z]+";
    private final String POSITION_REGEXP = "^[A-Z]+[0-9]+$";

    /**
     * Метод для преобразования буквы из позиции коня в номер столбца
     * @param letter буква для преобразования
     * @return номер столбца
     */
    private Integer convertLetterToNumber(String letter){

        Matcher letterMatcher = Pattern.compile(this.LETTERS_REGEXP)
                                       .matcher(letter);

        if(!letterMatcher.matches()){
            String errorMessage =
                    String.format("Строка вида %s не представляет собой букву английского алфавита", letter);
            throw new IllegalArgumentException(errorMessage);
        }

        if( !(letter.length() == 1) ){
            String errorMessage = "Входная строка должна состоять из одной буквы";
            throw new IllegalArgumentException(errorMessage);
        }

        return this.LETTERS.indexOf(letter);

    }

    /**
     * Метод для преобразования строкового представления положения в модель
     *
     * @param position строковое представление
     * @return модель с номером колонки и столбца
     */
    @Override
    public PositionDTO retrieve(String position) {

        Matcher positionMatcher = Pattern.compile(this.POSITION_REGEXP)
                                         .matcher(position);

        if(!positionMatcher.matches()){

            String errorMessage = String.format("Строка вида %s не представляет собой положение коня", position);
            throw new IllegalArgumentException(errorMessage);

        }

        Matcher letterMatcher = Pattern.compile(this.LETTERS_REGEXP)
                                       .matcher(position);

        String letter;

        if(letterMatcher.find()){
            letter = letterMatcher.group(0);
        }else{
            String errorMessage = String.format("Буквы в пришедшей позиции %s не найдены", position);
            throw new IllegalArgumentException(errorMessage);
        }

        Integer column = this.convertLetterToNumber(letter);

        String numberString = position.replaceAll(this.LETTERS_REGEXP, "");
        Integer row = Integer.valueOf(numberString);

        return PositionDTO.builder()
                          .column(column)
                          .row(row)
                          .build();

    }

    /**
     * Метод для проверки, верно ли положение фигуры относительно поля
     *
     * @param fieldHeight длина поля
     * @param fieldWidth ширина поля
     * @param position положение фигуры на доске
     * @return true, если возможно, и false, если нет
     */
    @Override
    public boolean isPossible(Integer fieldHeight, Integer fieldWidth, PositionDTO position) {

        return (position.getColumn() < fieldWidth) && (position.getColumn() >= 0) &&
                (position.getRow() < fieldHeight) && (position.getRow() >= 0);

    }
}
