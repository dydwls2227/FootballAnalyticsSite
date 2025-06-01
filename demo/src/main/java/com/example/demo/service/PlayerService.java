package com.example.demo.service;

import com.example.demo.entity.Player;
import com.example.demo.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public List<String> getPositions() {
        return playerRepository.findAll().stream()
                .flatMap(p -> Arrays.stream(p.getPosition().split(",")))
                .map(pos -> pos.replaceAll("\"", "").trim()) // 따옴표 제거 + 양쪽 공백 제거
                .filter(pos -> !pos.isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByPosition(String position) {
        return playerRepository.findByPositionContaining(position);
    }

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public Map<String, Double> getPercentileStats(Player target) {
        List<Player> samePositionPlayers = playerRepository.findByPositionContaining(target.getPosition());

        Map<String, Double> result = new LinkedHashMap<>();
        result.put("Goals", percentile(samePositionPlayers, p -> (double) p.getGoals(), target.getGoals()));
        result.put("Assists", percentile(samePositionPlayers, p -> (double) p.getAssists(), target.getAssists()));
        result.put("xG", percentile(samePositionPlayers, Player::getXg, target.getXg()));
        result.put("xAG", percentile(samePositionPlayers, Player::getXag, target.getXag()));
        result.put("Touches", percentile(samePositionPlayers, p -> (double) p.getTouches(), target.getTouches()));
        result.put("Carries", percentile(samePositionPlayers, p -> (double) p.getCarries(), target.getCarries()));
        result.put("Dribbles", percentile(samePositionPlayers, p -> (double) p.getDribbles(), target.getDribbles()));

        return result;
    }

    private double percentile(List<Player> players, Function<Player, Double> getter, double value) {
        long count = players.stream()
                .filter(p -> getter.apply(p) <= value)
                .count();
        return 100.0 * count / players.size();
    }
}
