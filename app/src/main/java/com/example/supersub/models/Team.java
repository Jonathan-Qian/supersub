package com.example.supersub.models;

import android.content.Context;
import android.util.Xml;

import com.example.supersub.models.position.PositionGroup;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class Team {
    private String name, season, description, filepath;
    private int color;
    private ArrayList<Player> players;
    // currentTeam is the single instance of Team that should exist at any point (see comments at the constructor for more info).
    private static Team currentTeam;

    // The constructor is private because this class follows the singleton pattern where currentTeam is the only instance since there should only be one Team loaded at any moment.
    private Team(Context context, TeamFacade teamFacade) {
        this.name = teamFacade.getName();
        this.season = teamFacade.getSeason(); // The team season might be something like "Summer 2022."
        this.description = teamFacade.getDescription(); // The team description might be something like "U17 Boys."
        this.color = teamFacade.getColor();
        this.players = new ArrayList<>(); // players is initialized with an empty list for now but will be set later in the constructor.
        this.filepath = context.getFilesDir() + "/teams/" + teamFacade.getDoor(); // The TeamFacade that is passed in provides a "door" which is really a filepath to this team's xml file (see comments in the TeamFacade class where the instance variables are declared to find more info about the "door").

        // Opening this Team sets the players.
        open(context);
    }

    // Getters and setters
    public static Team getCurrentTeam() {
        return currentTeam;
    }

    public static void setCurrentTeam(Context context, TeamFacade teamFacade) {
        currentTeam = new Team(context, teamFacade);
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

    // Return the index of a player based on the jerseyNumber.
    public int indexOfPlayer(int jerseyNumber) {
        int index = -1; // If a Player with jerseyNumber does not exist, return -1.

        // Linear search of players for a Player with jerseyNumber, then return index of that Player.
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getJerseyNumber() == jerseyNumber) {
                return i;
            }
        }

        return index;
    }

    // Return player based on id (will be used for goal-assist pairs).
    public Player findPlayerById(String id) {
        // Linear search of players for a Player with matching id.
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPLAYER_ID().equals(id)) {
                return players.get(i);
            }
        }

        return null;
    }

    // Find and return the Player in this team with the most goals.
    public Player topScorer() {
        // Making sure the team is not empty before accessing Player at 0. If the it is empty, return null.
        if (players.size() != 0) {
            int index = 0;
            int goals = players.get(index).getGoals();

            // Go through every player, checking if each has more goals that those before it.
            for (int i = index; i < players.size(); i++) {
                if (players.get(i).getGoals() > goals) {
                    index = i;
                    goals = players.get(i).getGoals();
                }
            }

            return players.get(index);
        }
        else {
            return null;
        }
    }

    // Same as topScorer() but with assists.
    public Player topAssister() {
        if (players.size() != 0) {
            int index = 0;
            int assists = players.get(index).getAssists();

            for (int i = index; i < players.size(); i++) {
                if (players.get(i).getAssists() > assists) {
                    index = i;
                    assists = players.get(i).getAssists();
                }
            }

            return players.get(index);
        }
        else {
            return null;
        }
    }

    // Read this team's xml file and set the players.
    private void open(Context context) {
        File directory = new File(context.getFilesDir() + "/teams/");

        // If the teams directory in internal storage does not exist, create it.
        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(filepath);

        // If this team's xml file exists, use XMLPullParser to read it. Otherwise, make a new file for this team.
        if (file.exists()) {
            try {
                // Create and set up a XMLPullParser by getting the XMLPullParserFactory and creating a XMLPullParser.
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new FileReader(file));
                int eventType = parser.getEventType();

                Player currentPlayer = null;
                ArrayList<Integer> playerPositions = null;

                while (eventType != XmlPullParser.END_DOCUMENT) { // Continue until the file ends.
                    if (eventType == XmlPullParser.START_TAG) {
                        if (parser.getName().equals("player")) {
                            // Create a Player using the attributes in the "player" start tag.
                            currentPlayer = new Player(
                                    parser.getAttributeValue(null, "firstName"),
                                    parser.getAttributeValue(null, "lastName"),
                                    Integer.parseInt(parser.getAttributeValue(null, "jerseyNumber")),
                                    Integer.parseInt(parser.getAttributeValue(null, "goals")),
                                    Integer.parseInt(parser.getAttributeValue(null, "assists")),
                                    Integer.parseInt(parser.getAttributeValue(null, "yellowCards")),
                                    Integer.parseInt(parser.getAttributeValue(null, "redCards")),
                                    parser.getAttributeValue(null, "id")
                            );
                        }
                        if (parser.getName().equals("group")) {
                            // Initializing or resetting playerPositions.
                            playerPositions = new ArrayList<>();
                        }
                        else if (parser.getName().equals("position")) {
                            // Add a position in the file to the list of ids that will be used to set the current player's positions.
                            playerPositions.add(new Integer(parser.getAttributeValue(null, "id")));
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        // Set the current player's positions and then add it to this team's players.
                        if (parser.getName().equals("player")) {
                            currentPlayer.getPositions().setPositionIds(playerPositions);
                            players.add(currentPlayer);
                        }
                    }

                    // Move to the next line (similar to bufferedReader.readLine() for BufferedReader).
                    eventType = parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Write this team's info to its xml file using XMLSerializer.
    public void write(Context context) {
        File file = new File(filepath);

        try {
            // Set up and create the XMLSerializer.
            XmlSerializer serializer = Xml.newSerializer();
            // Put the XMLSerializer's output in a StringWriter.
            StringWriter stringWriter = new StringWriter();
            serializer.setOutput(stringWriter);
            serializer.startDocument("UTF-8", true);
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            // Create the root tag, then the players tag.
            serializer.startTag("", "team");
            serializer.startTag("", "players");

            Player currentPlayer;
            PositionGroup currentGroup;

            // Iterate through every Player in players and write it.
            for (int i = 0; i < players.size(); i++) {
                currentPlayer = players.get(i);

                // Write the player and its attributes.
                serializer.startTag("", "player");
                serializer.attribute("", "id", currentPlayer.getPLAYER_ID());
                serializer.attribute("", "firstName", currentPlayer.getFirstName());
                serializer.attribute("", "lastName", currentPlayer.getLastName());
                serializer.attribute("", "jerseyNumber", Integer.toString(currentPlayer.getJerseyNumber()));
                serializer.attribute("", "goals", Integer.toString(currentPlayer.getGoals()));
                serializer.attribute("", "assists", Integer.toString(currentPlayer.getAssists()));
                serializer.attribute("", "yellowCards", Integer.toString(currentPlayer.getYellowCards()));
                serializer.attribute("", "redCards", Integer.toString(currentPlayer.getRedCards()));

                currentGroup = currentPlayer.getPositions();
                serializer.startTag("", "group");

                // Iterate through the Player's positions and write them within "group" tags.
                for (int j = 0; j < currentGroup.length(); j++) {
                    serializer.startTag("", "position");
                    serializer.attribute("", "id", Integer.toString(currentGroup.getPositionId(j)));
                    serializer.endTag("", "position");
                }

                serializer.endTag("", "group");
                serializer.endTag("", "player");
            }

            serializer.endTag("", "players");
            serializer.endTag("", "team");
            serializer.endDocument();

            // Write what was outputted to the StringWriter to this team's xml file.
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(stringWriter.toString());
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
