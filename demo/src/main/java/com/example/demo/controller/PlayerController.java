package com.example.demo.controller;


import com.example.demo.entity.Player;
import com.example.demo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/")
    public String selectPosition(Model model) {
        model.addAttribute("positions", playerService.getPositions());
        return "select-position";
    }

    @GetMapping("/players")
    public String showPlayers(@RequestParam String position, Model model) {
        model.addAttribute("players", playerService.getPlayersByPosition(position));
        return "select-player";
    }

    @GetMapping("/players/{id}")
    public String showPlayerStats(@PathVariable Long id, Model model) {
        Player player = playerService.getPlayerById(id).orElseThrow();
        model.addAttribute("player", player);
        model.addAttribute("percentiles", playerService.getPercentileStats(player));
        return "player-stats";
    }


}
