package com.example.supersub.models;

import com.example.supersub.models.position.PositionGroup;

import java.util.UUID;

public class Player {
    private String firstName, lastName;
    private int jerseyNumber, goals, assists, yellowCards, redCards;
    private PositionGroup positions;
    private final String PLAYER_ID;
    //TODO: add notes and birthday

    public static final String PLAYER_POSITION_GROUP_NAME = "Player";
    public static final String PLAYER_POSITION_GROUP_SYMBOL = "PL";

    public Player(String firstName, String lastName, int jerseyNumber, PositionGroup positions, int goals, int assists, int yellowCards, int redCards, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jerseyNumber = jerseyNumber;
        this.positions = positions;
        this.goals = goals;
        this.assists = assists;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.PLAYER_ID = id;
        validate();
    }
    public Player(String firstName, String lastName, int jerseyNumber, int goals, int assists, int yellowCards, int redCards, String id) {
        this(firstName, lastName, jerseyNumber, new PositionGroup(PLAYER_POSITION_GROUP_NAME, PLAYER_POSITION_GROUP_SYMBOL), goals, assists, yellowCards, redCards, id);
    }
    public Player(String firstName, String lastName, int jerseyNumber, PositionGroup positions) {
        this(firstName, lastName, jerseyNumber);
        this.positions = positions;
        validate();
    }
    public Player(String firstName, String lastName, int jerseyNumber) {
        this(firstName, lastName, jerseyNumber, 0, 0, 0, 0, "player_" + UUID.randomUUID().toString());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public PositionGroup getPositions() {
        return positions;
    }

    public void setPositions(PositionGroup positions) {
        this.positions = positions;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public String getPLAYER_ID() {
        return PLAYER_ID;
    }

    public void validate() {
        firstName = capitalizeName(firstName);
        lastName = capitalizeName(lastName);
        positions.removeRedundant();
    }

    private String capitalizeName(String name) {
        if (Character.isLowerCase(name.charAt(0))) {
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
        }

        return name;
    }
}
