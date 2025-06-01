package com.example.demo;

import com.example.demo.entity.Player;
import com.example.demo.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor
public class PlayerCsvLoader implements CommandLineRunner {

    private final PlayerRepository playerRepository;

    @Override
    public void run(String... args) throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.csv");
        if (inputStream == null) {
            System.err.println("❌ database.csv 파일을 찾을 수 없습니다.");
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine(); // 첫 줄은 헤더

        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

            Player player = new Player();
            player.setPlayer(tokens[0]);
            player.setTeam(tokens[1]);
            player.setPosition(tokens[4]);
            player.setMinutes(parseInt(tokens[6]));
            player.setGoals(parseInt(tokens[7]));
            player.setAssists(parseInt(tokens[8]));
            player.setTouches(parseInt(tokens[15]));
            player.setCarries(parseInt(tokens[27]));
            player.setDribbles(parseInt(tokens[16]));
            player.setPassesCompleted(parseInt(tokens[23]));
            player.setProgressiveCarries(parseInt(tokens[28]));
            player.setDribbleAttempts(parseInt(tokens[29]));
            player.setSuccessfulDribbles(parseInt(tokens[30]));
            player.setXg(parseDouble(tokens[19]));
            player.setXag(parseDouble(tokens[21]));

            playerRepository.save(player);
        }

        System.out.println("✅ 선수 데이터 초기화 완료");
    }

    private int parseInt(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private double parseDouble(String s) {
        try {
            return Double.parseDouble(s.trim().replace(",", "."));
        } catch (Exception e) {
            return 0.0;
        }
    }
}
