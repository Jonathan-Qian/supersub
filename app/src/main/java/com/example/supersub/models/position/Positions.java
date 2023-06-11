package com.example.supersub.models.position;

import android.content.Context;

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

        public boolean isGroup() {
            return false;
        }

        @Override
        public String toString() {
            return "Position with name: " + this.name + "; with symbol: " + this.symbol;
        }
    }

    public static String getPositionName(int index) {
        return positions.get(index).name;
    }

    public static String getPositionSymbol(int index) {
        return positions.get(index).symbol;
    }

    public static boolean isGroup(int index) {
        return positions.get(index).isGroup();
    }

    public static ArrayList<Integer> getGroupContents(int index) {
        ArrayList<Integer> groupContents = new ArrayList<>();

        if (isGroup(index)) {
            groupContents = ((PositionGroup) positions.get(index)).getPositionIds();
        }
        else {
            System.out.println("Position at " + index + " is not a PositionGroup");
        }

        return groupContents;
    }

    public static int indexOf(String name) {
        for (int i = 0; i < positions.size(); i++) {
            if (name.equals(getPositionName(i))) {
                return i;
            }
        }

        return -1;
    }

    protected static String positionToString(int index) {
        return positions.get(index).toString();
    }

    private static ArrayList<Positions.Position> read(Context context) throws XmlPullParserException, IOException {
        ArrayList<Positions.Position> positions = new ArrayList<>();

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(context.getAssets().open("positions.xml"), "UTF-8");
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

    public static int length() {
        return positions.size();
    }

    public static void init(Context context) {
        try {
            positions = read(context);
        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
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
