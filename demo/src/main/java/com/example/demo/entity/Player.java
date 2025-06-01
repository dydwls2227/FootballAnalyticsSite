package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "players")
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String player;
    private String team;
    private String position;
    private int minutes;
    private int goals;
    private int assists;
    private int touches;
    private int carries;
    private int dribbles;
    private int passesCompleted;
    private int progressiveCarries;
    private int dribbleAttempts;
    private int successfulDribbles;

    @Column(name = "expected_goals")
    private double xg;

    @Column(name = "expected_assists")
    private double xag;

    // getter/setter 생략
}