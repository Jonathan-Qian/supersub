package com.example.supersub.models;

import java.util.ArrayList;

public class Team {
    private String name;
    private String season;
    private String description;
    private int color;
    private ArrayList<Player> players;
    private static Team currentTeam;

    public Team(TeamFacade teamFacade) {
        this.name = teamFacade.getName();
        this.season = teamFacade.getSeason();
        this.description = teamFacade.getDescription();
        this.color = teamFacade.getColor();
        open(teamFacade);
    }
    public Team(String name, String season, String description, int color, ArrayList<Player> players) {
        this.name = name;
        this.season = season;
        this.description = description;
        this.color = color;
        this.players = players;
    }
    public Team(String name, String season, String description, int color) {
        this(name, season, description, color, new ArrayList<Player>());
    }

    public static Team getCurrentTeam() {
        return currentTeam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    private void open(TeamFacade teamFacade) {
        teamFacade.getDoor();
    }
}
