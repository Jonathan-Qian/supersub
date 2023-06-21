package com.example.supersub.models;

import com.example.supersub.models.position.PositionGroup;

import java.util.UUID;

/*
 * This is a very simple class. It stores information about a player.
 */
public class Player {
    private String firstName, lastName;
    private int jerseyNumber, goals, assists, yellowCards, redCards;
    private PositionGroup positions;
    // The player id has not yet been used but will be used later when goal-assists pairs are added (goal-assists pairs allow a goal and assist to be correlated so users can see who assisted a goal and vice versa).
    private final String PLAYER_ID;
    //TODO: add notes and birthday

    // All PositionGroup(s) used to store a player's positions should have the name "Player" and symbol "PL" so a player PositionGroup can easily be distinguished from other PositionGroup(s).
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
    }
    // Constructor used for creating new Player(s) when adding players to a team
    public Player(String firstName, String lastName, int jerseyNumber, PositionGroup positions) {
        this(firstName, lastName, jerseyNumber, positions, 0, 0, 0, 0, "player_" + UUID.randomUUID().toString());
        // Since this is used for adding new Player(s), it needs to be validated.
        validate();
    }
    // Constructor used in Team.open() which needs to set the Player's position after calling the constructor.
    public Player(String firstName, String lastName, int jerseyNumber, int goals, int assists, int yellowCards, int redCards, String id) {
        this(firstName, lastName, jerseyNumber, new PositionGroup(PLAYER_POSITION_GROUP_NAME, PLAYER_POSITION_GROUP_SYMBOL), goals, assists, yellowCards, redCards, id);
    }

    // Getters and setters.
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

    // This simply ensures the player's first and last name are capitalized and removes positions that are redundant (see the comments for removeRedundant() in PositionGroup).
    public void validate() {
        firstName = capitalizeName(firstName);
        lastName = capitalizeName(lastName);
        positions.removeRedundant();
    }

    private String capitalizeName(String name) {
        // Checking if the name is not too short ensures index out of range errors do not arise.
        if (name.length() > 1) {
            // This check is not necessary, but I thought it may be better to do it to avoid doing unnecessary work if the name is already capitalized.
            if (Character.isLowerCase(name.charAt(0))) {
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
            }
        }

        return name;
    }
}
