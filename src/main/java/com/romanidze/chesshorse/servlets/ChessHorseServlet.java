package com.romanidze.chesshorse.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.romanidze.chesshorse.dto.ErrorResponseDTO;
import com.romanidze.chesshorse.dto.MovesCalculationDTO;
import com.romanidze.chesshorse.dto.MovesRequestDTO;
import com.romanidze.chesshorse.dto.PositionDTO;
import com.romanidze.chesshorse.services.interfaces.CalculationService;
import com.romanidze.chesshorse.services.interfaces.PositionService;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * 12.06.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@WebServlet(urlPatterns = "/hourse/servlet/count", loadOnStartup = 1)
public class ChessHorseServlet extends HttpServlet {

    private final ObjectMapper objectMapper;
    private final PositionService positionService;
    private final CalculationService calculationService;

    @Autowired
    public ChessHorseServlet(ObjectMapper objectMapper,
                             PositionService positionService,
                             CalculationService calculationService) {
        this.objectMapper = objectMapper;
        this.positionService = positionService;
        this.calculationService = calculationService;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{

        PrintWriter pw = resp.getWriter();

        try{

            String height = Objects.requireNonNull(req.getParameter("height"));
            String width = Objects.requireNonNull(req.getParameter("width"));
            String start = Objects.requireNonNull(req.getParameter("start"));
            String end = Objects.requireNonNull(req.getParameter("end"));

            PositionDTO startPosition = this.positionService.retrieve(start);
            PositionDTO endPosition = this.positionService.retrieve(end);

            MovesRequestDTO movesRequestDTO = MovesRequestDTO.builder()
                                                             .height(Integer.valueOf(height))
                                                             .width(Integer.valueOf(width))
                                                             .start(startPosition)
                                                             .end(endPosition)
                                                             .build();

            MovesCalculationDTO movesCalculationDTO = this.calculationService.getMovesCount(movesRequestDTO);

            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Content-Type", "application/json");

            pw.println(this.objectMapper.writeValueAsString(movesCalculationDTO));

        }catch(Exception e){
            e.printStackTrace();

            ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                                                                .errorTime(LocalDateTime.now(ZoneId.of("Europe/Moscow")))
                                                                .errorMessage(e.getMessage())
                                                                .build();

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Content-Type", "application/json");

            pw.println(this.objectMapper.writeValueAsString(errorResponseDTO));

        }

        pw.flush();

    }

}
