package com.example.demo.repository;

import com.example.demo.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT DISTINCT p.position FROM Player p")
    List<String> findDistinctPositions();

    List<Player> findByPositionContaining(String position);
}