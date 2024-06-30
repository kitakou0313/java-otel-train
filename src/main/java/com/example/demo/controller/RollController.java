package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.Dice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class RollController {
    private static final Logger logger = LoggerFactory.getLogger(RollController.class);

    @GetMapping("/rolldice")
    public List<Integer> index(
        @RequestParam("player") Optional<String> player, 
        @RequestParam("rolls") Optional<Integer> rolls) {
        
        if (!rolls.isPresent()){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Missng param"
            );
        }

        List<Integer> result = new Dice(1, 6).rollTheDice(rolls.get());
        
        if (player.isPresent()) {
            logger.info("{} is rolling the dice: {}", player.get(), result);
          } else {
            logger.info("Anonymous player is rolling the dice: {}", result);
          }
          return result;
    }
}
