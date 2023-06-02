package com.example.supersub.models;

import android.content.Context;
import android.content.res.AssetManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;

public class Positions {
    private static ArrayList<Position> positions = new ArrayList<>();
    private static boolean initialized = false;

    protected static class Position {
        private String name;
        private String symbol;

        public Position(String name, String symbol) {
            this.name = name;
            this.symbol = symbol;
        }

        public String getName() {
            return name;
        }

        public String getSymbol() {
            return symbol;
        }

        @Override
        public String toString() {
            return "Position with name: " + this.name + "; with symbol: " + this.symbol;
        }
    }

    protected static String getPositionName(int id) {
        return positions.get(id).name;
    }

    protected static String getPositionSymbol(int id) {
        return positions.get(id).symbol;
    }

    protected static String positionToString(int index) {
        return positions.get(index).toString();
    }

    private static ArrayList<Positions.Position> read(Context context) throws XmlPullParserException, IOException {
        ArrayList<Positions.Position> positions = new ArrayList<>();

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        AssetManager assetManager = context.getAssets();
        parser.setInput(assetManager.open("positions.xml"), "UTF-8");
        int eventType = parser.getEventType();
        PositionGroup currentGroup = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (parser.getName().equals("group")) {
                    currentGroup = new PositionGroup(parser.getAttributeValue(null, "name"),
                            parser.getAttributeValue(null, "symbol"));
                }
                else if (parser.getName().equals("position")) {
                    positions.add(new Position(parser.getAttributeValue(null, "name"),
                            parser.getAttributeValue(null, "symbol")));

                    if (currentGroup != null && parser.getDepth() > 2) {
                        currentGroup.add(positions.size() - 1);
                    }
                }
            }
            else if (eventType == XmlPullParser.END_TAG) {
                if (parser.getName().equals("group")) {
                    positions.add(currentGroup);
                }
            }

            eventType = parser.next();
        }

        return positions;
    }

    public static void init(Context context) {
        try {
            positions = read(context);
        }
        catch (XmlPullParserException e) {
            System.err.println(e);
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    public static String positionsToString() {
        StringBuilder builder = new StringBuilder("Available positions and position groups include: ");

        for (int i = 0; i < positions.size(); i++) {
           builder.append(positionToString(i));
           builder.append(", ");
        }

        return builder.toString();
    }
}
