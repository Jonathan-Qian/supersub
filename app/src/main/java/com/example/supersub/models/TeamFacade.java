package com.example.supersub.models;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;

/*
* This class contains the info necessary to preview a team. It is necessary for the team list page. Only after selecting a team in the list will a real Team be loaded.
* That is why it is called TeamFacade. It contains the "front" or "facade" of a team but unlike a Team object, does not contain the "interior" (players).
* TeamFacade also exists because the Team class only allows one instance of Team to exist so this allows the team list page to have the information from multiple teams all displayed in one page.
*/
public class TeamFacade {
    /*
    * Like the Team class, TeamFacade also contains name, season, etc. since they are necessary for previewing. However, TeamFacade(s) also includes a "door", a filepath to the file that contains the rest of the information for a team.
    * "door" was chosen to be the variable name to go with the facade metaphor since the door filepath acts as an entrance to the "interior" of the team.
    */
    private String name, season, description;
    int color;
    private final String door;

    public TeamFacade(String name, String season, String description, int color, String door) {
        this.name = name;
        this.season = season;
        this.description = description;
        this.color = color;
        this.door = door;
    }
    public TeamFacade(String name, String season, String description, int color) {
        this(name, season, description, color, "team_" + UUID.randomUUID().toString() + ".xml");
    }

    // Getters. Setters aren't needed because other classes shouldn't change this TeamFacade's values.
    public String getName() {
        return name;
    }

    public String getSeason() {
        return season;
    }

    public String getDescription() {
        return description;
    }

    public int getColor() {
        return color;
    }

    public String getDoor() {
        return door;
    }

    // Read the teamfacades.csv file and return the programmatic equivalent of the contents as a list.
    public static ArrayList<TeamFacade> readAll(Context context) {
        ArrayList<TeamFacade> teamFacades = new ArrayList<>();
        File file = new File(context.getFilesDir() + "/teamfacades.csv");

        // Read the file only if it exists.
        if (file.exists()) {
            String line;
            String[] row;

            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));

                // Read TeamFacade(s) into the list until the file ends.
                while ((line = reader.readLine()) != null) {
                    row = line.split(",");
                    teamFacades.add(new TeamFacade(row[0], row[1], row[2], Integer.parseInt(row[3]), row[4]));
                }

                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return teamFacades;
    }

    // Write a TeamFacade to the teamfacades.csv file.
    public void write(Context context) {
        File file = new File(context.getFilesDir() + "/teamfacades.csv");
        // Since many concatenations will need to be made to create a String form of this TeamFacade's contents, StringBuilder is essential.
        StringBuilder builder = new StringBuilder();

        // Create a String from this TeamFacade's values in the proper format for a csv.
        builder.append(this.name);
        builder.append(",");
        builder.append(this.season);
        builder.append(",");
        builder.append(this.description);
        builder.append(",");
        builder.append(this.color);
        builder.append(",");
        builder.append(this.door);

        // Write the String to the csv file.
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file, true));

            writer.println(builder.toString());
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
