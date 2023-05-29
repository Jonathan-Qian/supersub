package com.example.supersub.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "players")
public class Player {
    @PrimaryKey(autoGenerate = true)
    public long id;
    private String firstName;
    private String lastName;
    private int jerseyNumber;
    private PositionGroup positions;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;

    @Ignore
    public Player(String firstName, String lastName, int jerseyNumber, int goals, int assists, int yellowCards, int redCards) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jerseyNumber = jerseyNumber;
        this.goals = goals;
        this.assists = assists;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
    }

    @Ignore
    public Player(String firstName, String lastName, int jerseyNumber) {
        this(firstName, lastName, jerseyNumber, 0, 0, 0, 0);
    }

    @Ignore
    public String getFirstName() {
        return firstName;
    }

    @Ignore
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Ignore
    public String getLastName() {
        return lastName;
    }

    @Ignore
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Ignore
    public int getJerseyNumber() {
        return jerseyNumber;
    }

    @Ignore
    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    @Ignore
    public int getGoals() {
        return goals;
    }

    @Ignore
    public void setGoals(int goals) {
        this.goals = goals;
    }

    @Ignore
    public int getAssists() {
        return assists;
    }

    @Ignore
    public void setAssists(int assists) {
        this.assists = assists;
    }

    @Ignore
    public int getYellowCards() {
        return yellowCards;
    }

    @Ignore
    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    @Ignore
    public int getRedCards() {
        return redCards;
    }

    @Ignore
    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }
}
