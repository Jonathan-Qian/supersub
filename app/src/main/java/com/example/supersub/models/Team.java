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
    private static Team currentTeam;

    private Team(Context context, TeamFacade teamFacade) {
        this.name = teamFacade.getName();
        this.season = teamFacade.getSeason();
        this.description = teamFacade.getDescription();
        this.color = teamFacade.getColor();
        this.players = new ArrayList<>();
        this.filepath = context.getFilesDir() + "/teams/" + teamFacade.getDoor();

        open(context);
    }

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

    public int indexOf(int jerseyNumber) {
        int index = -1;

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getJerseyNumber() == jerseyNumber) {
                return i;
            }
        }

        return index;
    }

    public Player topScorer() {
        if (players.size() != 0) {
            int index = 0;
            int goals = players.get(index).getGoals();

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

    private void open(Context context) {
        File directory = new File(context.getFilesDir() + "/teams/");

        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(filepath);

        if (file.exists()) {
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new FileReader(file));
                int eventType = parser.getEventType();
                Player currentPlayer = null;
                PositionGroup playerGroup = null;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (parser.getName().equals("player")) {
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
                        } else if (parser.getName().equals("group")) {
                            playerGroup = new PositionGroup(Player.PLAYER_POSITION_GROUP_NAME, Player.PLAYER_POSITION_GROUP_SYMBOL);
                        } else if (parser.getName().equals("position")) {
                            playerGroup.add(new Integer(parser.getAttributeValue(null, "id")));
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (parser.getName().equals("player")) {
                            currentPlayer.setPositions(playerGroup);
                            players.add(currentPlayer);
                        }
                    }

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

    public void write(Context context) {
        File file = new File(filepath);

        try {
            XmlSerializer serializer = Xml.newSerializer();
            StringWriter stringWriter = new StringWriter();

            serializer.setOutput(stringWriter);
            serializer.startDocument("UTF-8", true);
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            serializer.startTag("", "team");
            serializer.startTag("", "players");

            Player currentPlayer;
            PositionGroup currentGroup;

            for (int i = 0; i < players.size(); i++) {
                currentPlayer = players.get(i);
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

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(stringWriter.toString());
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
