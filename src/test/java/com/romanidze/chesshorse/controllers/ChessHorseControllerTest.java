package com.romanidze.chesshorse.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.romanidze.chesshorse.application.ChessHorseApplication;
import com.romanidze.chesshorse.dto.MovesCalculationDTO;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;

/**
 * 12.06.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ChessHorseApplication.class})
public class ChessHorseControllerTest {

    private final String REQUEST_URI = "/hourse/rest/count";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGoodInput(){

        String testURI = "%s?width=%d&height=%d&start=%s&end=%s";
        URI finalURI = URI.create(String.format(testURI, REQUEST_URI, 14, 10, "A3", "B1"));

        ResponseEntity<String> response = this.restTemplate.getForEntity(finalURI, String.class);

        MovesCalculationDTO testOutput = null;

        try {
           testOutput = this.objectMapper.readValue(response.getBody(), MovesCalculationDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(Integer.valueOf(1), testOutput.getMovesCount());

    }

    @Test
    public void testBadInput(){

        String testURI = "%s?&height=%d&start=%s&end=%s";
        URI finalURI = URI.create(String.format(testURI, REQUEST_URI, 10, "A3", "B1"));

        ResponseEntity<String> response = this.restTemplate.getForEntity(finalURI, String.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

}
